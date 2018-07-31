package com.example.android.newsapp;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    // static resources
    public static String APP_NAME;

    public static void initResources(Context context) {
        APP_NAME = context.getResources().getString(R.string.unknown);
    }
}
