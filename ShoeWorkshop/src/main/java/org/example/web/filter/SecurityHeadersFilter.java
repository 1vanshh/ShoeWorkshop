package org.example.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/*")
public class SecurityHeadersFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse response = (HttpServletResponse) res;

        // Заголовки безопасности
        response.setHeader("X-Frame-Options", "DENY"); // запрет на вставку в iframe
        response.setHeader("X-XSS-Protection", "1; mode=block"); // базовая XSS-защита
        response.setHeader("Referrer-Policy", "no-referrer"); // скрывать источник
        response.setHeader("X-Content-Type-Options", "nosniff"); // защита от MIME-атак

        // Content Security Policy (CSP)
        response.setHeader("Content-Security-Policy",
                "default-src 'self'; " +
                        "script-src 'self'; " +
                        "style-src 'self' 'unsafe-inline'; " +
                        "img-src 'self' data:; " +
                        "object-src 'none';");

        chain.doFilter(req, res);
    }
}
