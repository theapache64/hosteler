package com.theah64.hosteler.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by theapache64 on 17/11/17.
 */

public class DateUtils {

    private static final SimpleDateFormat READABLE_DATE_FORMAT = new SimpleDateFormat("MMM d, EEE, ''yy", Locale.getDefault());

    private static final SimpleDateFormat ddMMyyyy = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

    public static Date parseWithddMMyyyy(final String date) {
        try {
            return ddMMyyyy.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public static String formatWithddMMyyyy(final Date date) {
        return ddMMyyyy.format(date);
    }

    public static String getReadableDateFormat(final Date date) {
        return READABLE_DATE_FORMAT.format(date);
    }
}
