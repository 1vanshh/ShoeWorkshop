package org.example.security;

import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

public class HtmlSanitizerUtil {

    private static final PolicyFactory POLICY = new HtmlPolicyBuilder()
            .allowElements("b", "i", "u", "em", "strong", "p", "ul", "ol", "li", "br")
            .allowElements("a")
            .allowUrlProtocols("http", "https")
            .allowAttributes("href").onElements("a")
            .requireRelNofollowOnLinks()
            .disallowElements("script", "iframe", "object", "embed", "style")
            .toFactory();

    public static String sanitize(String input) {
        if (input == null) return null;
        return POLICY.sanitize(input);
    }
}
