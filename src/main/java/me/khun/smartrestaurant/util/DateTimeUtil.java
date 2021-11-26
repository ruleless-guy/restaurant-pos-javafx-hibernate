package me.khun.smartrestaurant.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeUtil {

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
