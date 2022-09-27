package com.example.waitou.rxjava;

import android.os.Handler;
import android.view.View;

import com.example.waitou.rxjava.base.BaseActivity;

/**
 * auth aboom
 * date 2018/7/31
 */
public class CircleActivity extends BaseActivity {
    Handler handler = new Handler();
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


        SectorProgressView sectorProgressView = findViewById(R.id.download_progress);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sectorProgressView.setPercent(sectorProgressView.getPercent() + 1);

                handler.postDelayed(this,500);
            }
        }, 500);


    }
}
