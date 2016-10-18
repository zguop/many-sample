package com.example.waitou.rxjava.main;

import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontHelper {
    public static final String FONTS_DIR = "fonts/";
    public static final String DEF_FONT  = FONTS_DIR + "fontawesome-webfont.ttf";

    public static void injectFont(View rootView) {
        injectFont(rootView, Typeface.createFromAsset(rootView.getContext()
                .getAssets(), DEF_FONT));
    }

    public static void injectFont(View rootView, Typeface tf) {
        if (rootView instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) rootView;
            int count = group.getChildCount();
            for (int i = 0; i < count; i++) {
                injectFont(group.getChildAt(i), tf);
            }
        } else if (rootView instanceof TextView) {
            ((TextView) rootView).setTypeface(tf);
        }
    }
}
