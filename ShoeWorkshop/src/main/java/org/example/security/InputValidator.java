package org.example.security;

import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern PHONE = Pattern.compile("^\\+?[0-9]{10,15}$");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE.matcher(phone).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }

    public static boolean isSafeString(String text, int maxLength) {
        return text != null && text.length() <= maxLength && !text.contains("<") && !text.contains(">");
    }
}
