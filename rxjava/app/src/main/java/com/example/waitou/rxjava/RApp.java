package com.example.waitou.rxjava;

import android.app.Application;
import android.content.Context;

import com.example.waitou.rxjava.util.Utils;

/**
 * Created by waitou on 16/10/28.
 */

public class RApp extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this;
        Utils.init(this);
    }

    public static Context getApp() {
        return mContext;
    }
}
