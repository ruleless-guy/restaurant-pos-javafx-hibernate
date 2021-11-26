package me.khun.smartrestaurant.util;

public class StringValidator {

    public static void notEmptyOrNotNull(String string, String exceptionMessage) {

        if (null == string || string.trim().isEmpty())
            throw new IllegalArgumentException(exceptionMessage);

    }
}
