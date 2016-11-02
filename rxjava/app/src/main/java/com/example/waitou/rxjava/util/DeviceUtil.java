package com.example.waitou.rxjava.util;

import android.content.Context;
import android.util.DisplayMetrics;

import com.example.waitou.rxjava.RApp;

/**
 * Created by waitou on 16/10/19.
 */

public class DeviceUtil {

    /**
     * 获取屏幕高度
     */
    public static int getDeviceHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getDeviceWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int dip2px(float dipValue) {
        float reSize = RApp.getApp().getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

}
