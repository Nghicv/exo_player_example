package com.project.nghicv.videochathead.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtil {

    public static final String FORMAT_DATE_ADDED = "MMM dd yyyy";

    public static String formatDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE_ADDED);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date * 1000L);
        return dateFormat.format(calendar.getTime());
    }
}
