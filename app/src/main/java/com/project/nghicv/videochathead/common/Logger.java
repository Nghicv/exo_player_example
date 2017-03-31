package com.project.nghicv.videochathead.common;

import android.util.Log;

public final class Logger {

    private static final String DEFAULT_TAG = "TaxiCrewApp";

    public static void verbose(String message) {
        log(Log.VERBOSE, DEFAULT_TAG, message);
    }

    public static void verbose(Object tag, String message) {
        log(Log.VERBOSE, tag, message);
    }

    public static void debug(String message) {
        log(Log.DEBUG, DEFAULT_TAG, message);
    }

    public static void debug(Object tag, String message) {
        log(Log.DEBUG, tag, message);
    }

    public static void info(String message) {
        log(Log.INFO, DEFAULT_TAG, message);
    }

    public static void info(Object tag, String message) {
        log(Log.INFO, tag, message);
    }

    public static void warn(String message) {
        log(Log.WARN, DEFAULT_TAG, message);
    }

    public static void warn(String message, Throwable tr) {
        log(Log.WARN, DEFAULT_TAG, message, tr);
    }

    public static void warn(Object tag, String message) {
        log(Log.WARN, tag, message);
    }

    public static void warn(Object tag, String message, Throwable tr) {
        log(Log.WARN, tag, message, tr);
    }

    public static void error(String message) {
        log(Log.ERROR, DEFAULT_TAG, message);
    }

    public static void error(String tag, Throwable tr) {
        log(Log.ERROR, tag, tr.getMessage());
    }

    public static void error(Object tag, String message) {
        log(Log.ERROR, tag, message);
    }

    public static void error(Object tag, String message, Throwable tr) {
        log(Log.ERROR, tag, message, tr);
    }

    public static void printStackTrace(Throwable e) {
        error(DEFAULT_TAG, e);
    }

    private static void log(int logLevel, Object tag, String message) {
        log(logLevel, tag, message, null);
    }

    private static void log(int logLevel, Object obj, String message, Throwable tr) {

        if (obj == null || message == null) {
            return;
        }

        String tag = obj.getClass().getSimpleName();

        switch (logLevel) {
            case Log.VERBOSE:
                Log.v(tag, message);
                break;
            case Log.DEBUG:
                Log.d(tag, message);
                break;
            case Log.INFO:
                Log.i(tag, message);
                break;
            case Log.WARN:
                if (tr != null) {
                    Log.w(tag, message, tr);
                } else {
                    Log.w(tag, message);
                }
                break;
            case Log.ERROR:
            case Log.ASSERT:
                if (tr != null) {
                    Log.e(tag, message, tr);
                } else {
                    Log.e(tag, message);
                }
                break;
            default:
                break;
        }
    }
}
