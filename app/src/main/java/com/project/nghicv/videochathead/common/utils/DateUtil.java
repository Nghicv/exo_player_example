package com.project.nghicv.videochathead.common.utils;

import android.content.Context;
import com.project.nghicv.videochathead.R;
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

    public static String formatTime(Context context, long time) {
        int timeSecond = (int) (time / 1000);

        if (timeSecond < 60) {
            return context.getString(R.string.format_time_minute_video, 0, timeSecond);
        }

        if (timeSecond > 60 && timeSecond <= 3600) {
            int minute = timeSecond / 60;
            int second = timeSecond % 60;
            return context.getString(R.string.format_time_minute_video, minute, second);
        }

        int hour = timeSecond / 3600;
        int minute = (timeSecond % 3600) / 60;
        int second = (timeSecond % 3600) % 60;
        return context.getString(R.string.format_time_hour_video, hour, minute, second);
    }
}
