package me.khun.smartrestaurant.util;

public class StringUtil {

    public static boolean isNullOrBlank(String s) {
        return null == s || s.isBlank();
    }

    public static boolean isNullOrEmpty(String s) {
        return null == s || s.isEmpty();
    }

    public static String normalizeNotNullTrim(String s) {
        return null == s ? "" : s.trim();
    }
}
