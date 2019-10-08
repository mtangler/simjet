package com.app.mayur.retrofit;

import android.util.Log;

public class LogEx {

    public static void log(String log) {
        Log.e("log", log);
    }

    public static void log(Exception e) {
        Log.e("Exception", e.getMessage());
        e.printStackTrace();
    }
}
