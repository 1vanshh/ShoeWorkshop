package org.example.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    private void ensureCsrf(HttpServletRequest req) {
        HttpSession session = req.getSession(true);
        String token = (String) session.getAttribute("CSRF_TOKEN");
        if (token == null) {
            token = UUID.randomUUID().toString();
            session.setAttribute("CSRF_TOKEN", token);
        }
        req.setAttribute("csrfToken", token);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        ensureCsrf(req);
        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }
}
