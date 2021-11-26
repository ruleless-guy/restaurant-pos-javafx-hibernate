package me.khun.smartrestaurant.util;

import java.text.DecimalFormat;

public class DecimalUtil {

    public static Double round(Double d, int decimalPlaces) {

        if (null == d) return null;

        DecimalFormat df = new DecimalFormat("#."+"#".repeat(Math.max(0, decimalPlaces)));
        return Double.valueOf(df.format(d));
    }
}
