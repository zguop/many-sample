package com.example.waitou.rxjava.util;

import android.content.Context;
import android.util.DisplayMetrics;

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
}
