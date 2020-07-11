package com.example.waitou.rxjava;

import android.view.View;

import com.example.waitou.rxjava.base.BaseActivity;

/**
 * auth aboom
 * date 2018/7/31
 */
public class CircleActivity extends BaseActivity {
    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_circle;
    }

    @Override
    protected void initData() {
        super.initData();

       CircleProgressView circleProgressView = (CircleProgressView) findViewById(R.id.progress);


        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                circleProgressView.start();

                CircleProgressDialog circleProgressDialog = new CircleProgressDialog(CircleActivity.this);
                circleProgressDialog.show();
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleProgressView.stop();
            }
        });



    }
}
