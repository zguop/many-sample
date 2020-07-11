package com.example.waitou.rxjava;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * auth aboom
 * date 2018/7/31
 */
public class CircleProgressDialog extends Dialog {

    public CircleProgressDialog(@NonNull Context context) {
        super(context, R.style.Dialog_Tip);
        CircleProgressView circleProgressView = new CircleProgressView(context);
        setContentView(circleProgressView);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp;
        if (dialogWindow != null) {
            lp = dialogWindow.getAttributes();
            dialogWindow.setGravity(Gravity.CENTER);
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }
    }
}
