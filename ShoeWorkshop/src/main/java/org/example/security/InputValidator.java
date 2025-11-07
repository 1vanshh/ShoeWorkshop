package org.example.security;

import java.util.regex.Pattern;

public class InputValidator {
    private static final Pattern PHONE = Pattern.compile("^\\+?[0-9]{10,15}$");
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public static String normalizePhone(String raw) {
        if (raw == null) return null;
        String s = raw.replaceAll("[^0-9+]", ""); // выкидываем всё, кроме цифр и +
        if (s.startsWith("+7") && s.length() == 12) {
            return s;
        }
        if (s.startsWith("8") && s.length() == 11) {
            return "+7" + s.substring(1);
        }
        if (!s.startsWith("+") && s.length() == 10) {
            return "+7" + s;
        }
        return s;
    }

    public static boolean isValidPhone(String raw) {
        if (raw == null) return false;
        // убираем «визуальный мусор»
        String s = raw.trim()
                .replace(" ", "")
                .replace("-", "")
                .replace("(", "")
                .replace(")", "");
        // допускаем ведущий '+'
        if (s.startsWith("+")) s = s.substring(1);
        // теперь только цифры и длина от 10 до 15
        if (!s.matches("\\d{10,15}")) return false;
        return true;
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL.matcher(email).matches();
    }

    public static boolean isSafeString(String text, int maxLength) {
        return text != null && text.length() <= maxLength && !text.contains("<") && !text.contains(">");
    }
}
