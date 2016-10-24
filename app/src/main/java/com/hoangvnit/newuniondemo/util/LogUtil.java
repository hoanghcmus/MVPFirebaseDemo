package com.hoangvnit.newuniondemo.util;

import android.util.Log;

import com.hoangvnit.newuniondemo.BuildConfig;

/**
 * Created by hoang on 10/18/16.
 */

public class LogUtil {

    private static final String TAG = "NewUnionDemo";

    public static void v(String message) {
        if (BuildConfig.DEBUG) {
            Log.v(TAG, message);
            Log.v(TAG, "--------------------------------------------------------------------------");
        }
    }

    public static void i(String message) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, message);
            Log.i(TAG, "--------------------------------------------------------------------------");
        }
    }

    public static void d(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
            Log.d(TAG, "--------------------------------------------------------------------------");
        }
    }

    public static void w(String message) {
        if (BuildConfig.DEBUG) {
            Log.w(TAG, message);
            Log.w(TAG, "--------------------------------------------------------------------------");
        }
    }

    public static void e(String message) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, message);
            Log.e(TAG, "--------------------------------------------------------------------------");
        }
    }

    public static void v(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, message);
            Log.v(tag, "--------------------------------------------------------------------------");
        }
    }

    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, message);
            Log.i(tag, "--------------------------------------------------------------------------");
        }
    }

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
            Log.d(tag, "--------------------------------------------------------------------------");
        }
    }

    public static void w(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, message);
            Log.w(tag, "--------------------------------------------------------------------------");
        }
    }

    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message);
            Log.e(tag, "--------------------------------------------------------------------------");
        }
    }

}
