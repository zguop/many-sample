package com.example.waitou.rxjava.gesture_scroller.gesture;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by waitou on 16/11/28.
 */

public class GestureDetectorActivity extends BaseActivity implements View.OnTouchListener {

    @BindView(R.id.btn)
    Button mButton;
    private GestureDetector      mGestureDetector;
    private ScaleGestureDetector mScaleGestureDetector;

    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_gesture;
    }


    @Override
    protected void initData() {
        mGestureDetector = new GestureDetector(this, new GestureListener());
        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleGestureListener());
        mButton.setOnTouchListener(this);


    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
     //   mScaleGestureDetector.onTouchEvent(event);
        return mScaleGestureDetector.onTouchEvent(event);
    }
}
