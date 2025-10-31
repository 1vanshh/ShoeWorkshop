package org.example.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.UUID;

@WebFilter("/*")
public class CsrfFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        HttpSession session = request.getSession(true);
        String csrfToken = (String) session.getAttribute("csrfToken");

        // если токена нет — создаём новый
        if (csrfToken == null) {
            csrfToken = UUID.randomUUID().toString();
            session.setAttribute("csrfToken", csrfToken);
        }

        // проверяем POST-запросы
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            String requestToken = request.getParameter("csrfToken");
            if (requestToken == null || !requestToken.equals(csrfToken)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF verification failed");
                return;
            }
        }

        // Добавляем токен в атрибут запроса (для JSP)
        request.setAttribute("csrfToken", csrfToken);

        chain.doFilter(req, res);
    }
}
