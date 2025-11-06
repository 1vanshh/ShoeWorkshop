package org.example.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entities.Client;
import org.example.security.InputValidator;
import org.example.service.ClientService;
import org.example.service.ClientServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet("/clients")
public class ClientServlet extends HttpServlet {

    private final ClientService clientService = new ClientServiceImpl();

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
                case "findByEmail" -> findByEmail(req, resp);
                case "search" -> search(req, resp);
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
                case "add" -> addClient(req, resp);
                case "update" -> updateClient(req, resp);
                case "delete" -> deleteClient(req, resp);
                default -> resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action");
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
        }
    }

    // ----------- Методы ------------

    private void listAll(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Client> clients = clientService.getAll();
        req.setAttribute("clients", clients);
        req.getRequestDispatcher("/WEB-INF/views/clients.jsp").forward(req, resp);
    }

    private void getById(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Client client = clientService.getById(id);
        req.setAttribute("client", client);
        req.getRequestDispatcher("/WEB-INF/views/client-detail.jsp").forward(req, resp);
    }

    private void findByEmail(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        Client client = clientService.findByEmail(email);
        req.setAttribute("client", client);
        req.getRequestDispatcher("/WEB-INF/views/client-detail.jsp").forward(req, resp);
    }

    private void search(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String query = req.getParameter("query");
        List<Client> clients = clientService.searchClients(query, 20, 0);
        req.setAttribute("clients", clients);
        req.getRequestDispatcher("/WEB-INF/views/clients.jsp").forward(req, resp);
    }

    private void addClient(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Client client = new Client();
        client.setFullName(req.getParameter("fullName"));
        client.setEmail(req.getParameter("email"));
        client.setPhoneNumber(req.getParameter("phone"));
        client.setAddress(req.getParameter("address"));

        clientService.add(client);
        resp.sendRedirect("clients");
    }

    private void updateClient(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Client client = new Client();
        client.setFullName(req.getParameter("fullName"));
        client.setEmail(req.getParameter("email"));
        client.setPhoneNumber(req.getParameter("phone"));
        client.setAddress(req.getParameter("address"));

        clientService.update(id, client);
        resp.sendRedirect("clients");
    }

    private void deleteClient(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        clientService.delete(id);
        resp.sendRedirect("clients");
    }
}
