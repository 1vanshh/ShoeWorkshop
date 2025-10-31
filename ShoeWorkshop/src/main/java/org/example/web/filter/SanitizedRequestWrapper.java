package org.example.web.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.Map;

/**
 * Обёртка для HttpServletRequest, в которую можно подставить
 * безопасные (очищенные) параметры.
 */
public class SanitizedRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> sanitizedParams;

    public SanitizedRequestWrapper(HttpServletRequest request, Map<String, String[]> sanitizedParams) {
        super(request);
        this.sanitizedParams = sanitizedParams;
    }

    @Override
    public String getParameter(String name) {
        String[] values = sanitizedParams.get(name);
        if (values != null && values.length > 0) {
            return values[0];
        }
        return super.getParameter(name);
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = sanitizedParams.get(name);
        if (values != null) {
            return values;
        }
        return super.getParameterValues(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return sanitizedParams;
    }
}
