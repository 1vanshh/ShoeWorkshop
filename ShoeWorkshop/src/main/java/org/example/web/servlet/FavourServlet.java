package org.example.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.example.entities.Favour;
import org.example.service.FavourService;
import org.example.service.FavourServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/favours")
public class FavourServlet extends HttpServlet {

    private final FavourService favourService = new FavourServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "getById" -> getById(req, resp);
                // если появится search по name — добавим здесь case "search"
                default -> listAll(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String action = req.getParameter("action");
        if (action == null) action = "";

        try {
            switch (action) {
                case "add" -> addFavour(req, resp);
                case "update" -> updateFavour(req, resp);
                case "delete" -> deleteFavour(req, resp);
                default -> resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    // ==== helpers ====

    private void ensureCsrf(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        String token = (String) session.getAttribute("CSRF_TOKEN");
        if (token == null) {
            token = UUID.randomUUID().toString();
            session.setAttribute("CSRF_TOKEN", token);
        }
        req.setAttribute("csrfToken", token);
    }

    private void listAll(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ensureCsrf(req);
        List<Favour> favours = favourService.getAll();
        req.setAttribute("favours", favours);
        req.getRequestDispatcher("/WEB-INF/views/favours.jsp").forward(req, resp);
    }

    private void getById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ensureCsrf(req);
        int id = Integer.parseInt(req.getParameter("id"));
        Favour favour = favourService.getById(id);
        req.setAttribute("favour", favour);
        req.getRequestDispatcher("/WEB-INF/views/favour-detail.jsp").forward(req, resp);
    }

    private void addFavour(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        String name = req.getParameter("favourName");
        String basePriceStr = req.getParameter("basePrice");

        if (name == null || name.isBlank()) {
            req.setAttribute("error", "Название услуги обязательно");
            forwardBack(req, resp);
            return;
        }
        Double price = parsePrice(basePriceStr);
        if (price == null || price <= 0) {
            req.setAttribute("error", "Цена должна быть положительным числом");
            forwardBack(req, resp);
            return;
        }

        Favour f = new Favour();
        f.setFavourName(name.trim());
        f.setBasePrice(price);

        favourService.add(f);
        resp.sendRedirect(req.getContextPath() + "/favours");
    }

    private void updateFavour(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("favourName");
        String basePriceStr = req.getParameter("basePrice");

        if (name == null || name.isBlank()) {
            req.setAttribute("error", "Название услуги обязательно");
            forwardDetail(req, resp, id);
            return;
        }
        Double price = parsePrice(basePriceStr);
        if (price == null || price <= 0) {
            req.setAttribute("error", "Цена должна быть положительным числом");
            forwardDetail(req, resp, id);
            return;
        }

        Favour f = new Favour();
        f.setFavourName(name.trim());
        f.setBasePrice(price);

        favourService.update(id, f);
        resp.sendRedirect(req.getContextPath() + "/favours");
    }

    private void deleteFavour(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        favourService.delete(id);
        resp.sendRedirect(req.getContextPath() + "/favours");
    }

    private Double parsePrice(String s) {
        try {
            if (s == null) return null;
            return Double.valueOf(s.replace(',', '.'));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void forwardBack(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        listAll(req, resp);
    }

    private void forwardDetail(HttpServletRequest req, HttpServletResponse resp, int id)
            throws ServletException, IOException {
        Favour favour = favourService.getById(id);
        req.setAttribute("favour", favour);
        req.getRequestDispatcher("/WEB-INF/views/favour-detail.jsp").forward(req, resp);
    }
}
