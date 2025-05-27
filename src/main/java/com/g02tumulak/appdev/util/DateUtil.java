package com.g02tumulak.appdev.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final int EXPIRY_DAYS = 130;

    public static Date getCurrentDate() {
        return new Date();
    }

    public static Date getExpiryDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(getCurrentDate());
        calendar.add(Calendar.DAY_OF_YEAR, EXPIRY_DAYS);
        return calendar.getTime();
    }

    public static boolean isExpired(Date expiryDate) {
        return expiryDate != null && expiryDate.before(getCurrentDate());
    }
}