package com.example.waitou.rxjava.demos.demo5;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;
import com.example.waitou.rxjava.demos.demo2.RoundRectImageView;
import com.example.waitou.rxjava.util.DeviceUtil;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringChain;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

import java.util.List;

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

    @BindView(R.id.img)
    RoundRectImageView mRoundRectImageView;

    @BindView(R.id.btn4)
    Button mButton;

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
        SpringAnimatorUtil.animateViewDirection(DeviceUtil.getDeviceHeight(this), 0, DEFAULT_TENSION, DEFAULT_FRICTION, new SpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                Log.i("aa" , spring.getCurrentValue() + "  ---");
            }

            @Override
            public void onSpringAtRest(Spring spring) {

            }

            @Override
            public void onSpringActivate(Spring spring) {

            }

            @Override
            public void onSpringEndStateChange(Spring spring) {

            }
        });
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

    @OnClick(R.id.btn3)
    public void onBtn3Click(){
        SpringChain springChain = SpringChain.create(40,6,50,7);
        int childCount = mAnimLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View view = mAnimLayout.getChildAt(i);
            springChain.addSpring(new SimpleSpringListener() {
                @Override
                public void onSpringUpdate(Spring spring) {
                    view.setTranslationY((float) spring.getCurrentValue());
                }
            });
        }

        List<Spring> springs = springChain.getAllSprings();
        for (int i = 0; i < springs.size(); i++) {
            springs.get(i).setCurrentValue(DeviceUtil.getDeviceHeight(this));
        }
        springChain.setControlSpringIndex(springs.size() - 1).getControlSpring().setEndValue(0);

    }

    @OnClick(R.id.btn4)
    public void onBtn4Cilci(){
        int[] itemPos = new int[2];
        mButton.getLocationOnScreen(itemPos);
        int itemPoX = itemPos[0];
        int itemPoY = itemPos[1];

        Log.i("aa" , "itemPoX  " + itemPoX   + "itemPoY " + itemPoY);
        AnimatorSet animatorSet = new AnimatorSet();


        animatorSet.playTogether(
                ObjectAnimator.ofFloat(mRoundRectImageView, "alpha", 0, 0.475f, 1),
                ObjectAnimator.ofFloat(mRoundRectImageView, "scaleX", 0.1f, 0.475f, 1),
                ObjectAnimator.ofFloat(mRoundRectImageView, "scaleY", 0.1f, 0.475f, 1),
                ObjectAnimator.ofFloat(mRoundRectImageView, "translationY", -DeviceUtil.getDeviceHeight(this) / 2  + itemPoY, -60, 0),
                ObjectAnimator.ofFloat(mRoundRectImageView, "translationX", itemPoX / 2, 60, 0)
        );

        animatorSet.setDuration(1000);
        animatorSet.start();



//                ObjectAnimator.ofFloat(linearLayout, "scaleX", 0.1f, 0.475f, 1),
//                ObjectAnimator.ofFloat(linearLayout, "scaleY", 0.1f, 0.475f, 1),
//                ObjectAnimator.ofFloat(linearLayout, "translationY", -DeviceUtil.getDeviceHeight(context), 60, 0),
//                ObjectAnimator.ofFloat(linearLayout, "translationX", deviceWidth, 60, 0)





//        SpringSystem.create()
//                .createSpring()
//                .setCurrentValue(-DeviceUtil.getDeviceHeight(this))
//                .setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(DEFAULT_TENSION, DEFAULT_FRICTION))
//                .addListener(new SpringListener() {
//                    @Override
//                    public void onSpringUpdate(Spring spring) {
//                        Log.i("aa" , "onSpringUpdate = " + spring.getCurrentValue());
//                        //不断的 3
//                        mRoundRectImageView.setTranslationY((float) spring.getCurrentValue());
//                        mRoundRectImageView.setTranslationX((float) -spring.getCurrentValue());
//                        mRoundRectImageView.setAlpha((float) -spring.getCurrentValue());
//                    }
//
//                    @Override
//                    public void onSpringAtRest(Spring spring) {
//                        Log.i("aa" , "onSpringAtRest = " + spring.getCurrentValue());
//                        //结束 4
//                    }
//
//                    @Override
//                    public void onSpringActivate(Spring spring) {
//                        Log.i("aa" , "onSpringActivate = " + spring.getCurrentValue());
//                        //2
//                    }
//
//                    @Override
//                    public void onSpringEndStateChange(Spring spring) {
//                        Log.i("aa" , "onSpringEndStateChange = " + spring.getCurrentValue());
//                        //打印情况   这个方法先调用 1
//                    }
//                })
//                .setEndValue(0);
    }
}
