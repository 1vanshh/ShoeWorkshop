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

        Map<String, String[]> sanitizedParams = new HashMap<>();
        for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
            String key = entry.getKey();
            String[] sanitizedValues = Arrays.stream(entry.getValue())
                    .map(HtmlSanitizerUtil::sanitize)
                    .toArray(String[]::new);
            sanitizedParams.put(key, sanitizedValues);
        }

        chain.doFilter(new SanitizedRequestWrapper(request, sanitizedParams), res);
    }
}
