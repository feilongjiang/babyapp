package com.example.apps.baseProject.baseLib.utils;

import android.util.Log;

/**
 * Created by yc on 16-2-6. 再次封装的LogCat类，实现在release版本不打印logcat
 */
public class ILog {

    private static boolean showLog = true;  //  是否显示，用于release版本不打印log
    private static String tag = "FastAndroidDev";   //  默认的

    public static void i(String tag, Object msg) {

        if (showLog) {
            Log.i(tag, String.valueOf(msg));
        }
    }

    public static void i(Object msg) {

        if (showLog) {
            Log.i(tag, String.valueOf(msg));
        }
    }

    public static void d(String tag, Object msg) {

        if (showLog) {
            Log.d(tag, String.valueOf(msg));
        }
    }

    public static void d(Object msg) {

        if (showLog) {
            Log.d(tag, String.valueOf(msg));
        }
    }

    public static void e(String tag, Object msg) {

        if (showLog) {
            Log.e(tag, String.valueOf(msg));
        }
    }

    public static void e(Object msg) {

        if (showLog) {
            Log.e(tag, String.valueOf(msg));
        }
    }

    public static boolean isShowLog() {
        return showLog;
    }

    public static void setShowLog(boolean showLog) {
        ILog.showLog = showLog;
    }
}
