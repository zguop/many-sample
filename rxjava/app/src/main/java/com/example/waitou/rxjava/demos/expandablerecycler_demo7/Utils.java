package com.example.waitou.rxjava.demos.expandablerecycler_demo7;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.TypedValue;

import com.example.waitou.rxjava.R;


/**
 * Created by stefano on 14/04/2015.
 */
public class Utils {

    public static int getAccentColor(Context context) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int accentColour = a.getColor(0, 0);

        a.recycle();

        return accentColour;
    }

    public static int getSecondaryColor(Context context) {
        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{android.R.attr.textColorSecondary});
        int accentColour = a.getColor(0, 0);

        a.recycle();

        return accentColour;
    }

}
