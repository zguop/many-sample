package com.example.waitou.rxjava;

import android.app.Application;
import android.content.Context;

/**
 * Created by waitou on 16/10/28.
 */

public class RApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
    }

    public static Context getApp() {
        return mContext;
    }
}
