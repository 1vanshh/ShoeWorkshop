package org.example.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class CsrfFilter implements Filter {

    private static final String SESSION_KEY = "CSRF_TOKEN";
    private static final String REQ_ATTR     = "csrfToken";

    // Какие пути не проверяем (статика и т.п.)
    private static final Set<String> STATIC_PREFIXES = Set.of(
            "/css/", "/images/", "/js/", "/favicon", "/webjars/"
    );

    @Override
    public void doFilter(ServletRequest sreq, ServletResponse sres, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest) sreq;
        HttpServletResponse resp = (HttpServletResponse) sres;

        String uri = req.getRequestURI();
        String ctx = req.getContextPath();
        if (ctx != null && !ctx.isEmpty() && uri.startsWith(ctx)) {
            uri = uri.substring(ctx.length());
        }

        // Пропускаем статику без проверок
        for (String p : STATIC_PREFIXES) {
            if (uri.startsWith(p)) {
                chain.doFilter(req, resp);
                return;
            }
        }

        HttpSession session = req.getSession(true);

        // 1) Создать токен в сессии при первом заходе
        String sessionToken = (String) session.getAttribute(SESSION_KEY);
        if (sessionToken == null) {
            sessionToken = UUID.randomUUID().toString();
            session.setAttribute(SESSION_KEY, sessionToken);
        }

        // 2) Прокинуть токен в request, чтоб JSP видели ${csrfToken}
        req.setAttribute(REQ_ATTR, sessionToken);

        String method = req.getMethod();

        // 3) Проверяем только state-changing методы
        if (method.equalsIgnoreCase("POST")
                || method.equalsIgnoreCase("PUT")
                || method.equalsIgnoreCase("PATCH")
                || method.equalsIgnoreCase("DELETE")) {

            // Принимаем несколько вариантов имени
            String tokenParam = req.getParameter("csrfToken");
            if (tokenParam == null || tokenParam.isEmpty()) {
                tokenParam = req.getParameter("_csrf");
            }
            if (tokenParam == null || tokenParam.isEmpty()) {
                tokenParam = req.getHeader("X-CSRF-Token");
            }

            if (tokenParam == null || !tokenParam.equals(sessionToken)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF verification failed");
                return;
            }
        }

        chain.doFilter(req, resp);
    }
}
