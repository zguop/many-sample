package com.example.waitou.rxjava.demos.demo5;

import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;
import com.example.waitou.rxjava.util.DeviceUtil;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by waitou on 16/10/19.
 */

public class AnimationsActivity extends BaseActivity {

    //拉力系数
    private static final int DEFAULT_TENSION  = 40;
    //摩擦力系数
    private static final int DEFAULT_FRICTION = 6;

    @BindView(R.id.anim_layout)
    RelativeLayout mAnimLayout;

    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode initOverridePendingTransitionModel() {
        return TransitionMode.TOP;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_animations;
    }


    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn1)
    public void onBtn1Click() {
        BounceInUpAnimator animator = new BounceInUpAnimator();
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setTarget(mAnimLayout);
        animator.start();
    }

    @OnClick(R.id.btn2)
    public void onBtn2Click() {
        SpringSystem.create()
                .createSpring()
                .setCurrentValue(DeviceUtil.getDeviceHeight(this))
                .setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(DEFAULT_TENSION, DEFAULT_FRICTION))
                .addListener(new SpringListener() {
                    @Override
                    public void onSpringUpdate(Spring spring) {
                        Log.i("aa" , "onSpringUpdate = " + spring.getCurrentValue());
                        //不断的 3
                        mAnimLayout.setTranslationY((float) spring.getCurrentValue());
                    }

                    @Override
                    public void onSpringAtRest(Spring spring) {
                        Log.i("aa" , "onSpringAtRest = " + spring.getCurrentValue());
                        //结束 4
                    }

                    @Override
                    public void onSpringActivate(Spring spring) {
                        Log.i("aa" , "onSpringActivate = " + spring.getCurrentValue());
                        //2
                    }

                    @Override
                    public void onSpringEndStateChange(Spring spring) {
                        Log.i("aa" , "onSpringEndStateChange = " + spring.getCurrentValue());
                        //打印情况   这个方法先调用 1
                    }
                })
                .setEndValue(0);

    }
}
