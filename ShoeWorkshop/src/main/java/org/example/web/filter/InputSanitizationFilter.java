package org.example.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.example.security.HtmlSanitizerUtil;

import java.io.IOException;
import java.util.*;

public class InputSanitizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;

        // Поля, которые НЕ нужно HTML-санитизировать.
        // Их просто тримим; проверка делается в сервисах через InputValidator.
        Set<String> noHtmlSanitize = Set.of(
                "query", "email", "phone", "id", "clientId", "statusId",
                "receiptId", "serviceId", "quantity", "discount", "limit", "offset", "action", "_csrf"
        );

        Map<String, String[]> sanitizedParams = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String key = entry.getKey();
            String[] values = entry.getValue();

            String[] safe = Arrays.stream(values)
                    .map(v -> v == null ? null : v.trim())
                    .map(v -> {
                        if (noHtmlSanitize.contains(key)) return v;     // не трогаем
                        return HtmlSanitizerUtil.sanitize(v);           // только для "богатых" текстов
                    })
                    .toArray(String[]::new);

            sanitizedParams.put(key, safe);
        }

        chain.doFilter(new SanitizedRequestWrapper(request, sanitizedParams), res);
    }
}
