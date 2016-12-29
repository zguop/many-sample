package com.example.waitou.rxjava.databinding.persenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.waitou.rxjava.databinding.AnimationActivity;
import com.example.waitou.rxjava.databinding.ExpressionActivity;
import com.example.waitou.rxjava.databinding.LambdaActivity;
import com.example.waitou.rxjava.databinding.ListDataActivity;
import com.example.waitou.rxjava.databinding.SimpleActivity;
import com.example.waitou.rxjava.databinding.TwoWayActivity;

/**
 * Created by waitou on 16/12/28.
 */

public class MainPresenter {

    private Activity mActivity;


    public MainPresenter(Activity activity) {
        mActivity = activity;
    }

    public void onClickSimpleDemo(View view) {
        Log.i("aa" , " view " + view);
        mActivity.startActivity(new Intent(mActivity, SimpleActivity.class));
    }

    public void onClickListDemo(View view) {
        mActivity.startActivity(new Intent(mActivity, ListDataActivity.class));
    }

    public void onClickTwoWayDemo(View view) {
        mActivity.startActivity(new Intent(mActivity, TwoWayActivity.class));
    }

    public void onClickExpressionDemo(View view) {
        mActivity.startActivity(new Intent(mActivity, ExpressionActivity.class));
    }

    public void onClickAnimationDemo(View view) {
        mActivity.startActivity(new Intent(mActivity, AnimationActivity.class));
    }

    public void onClickLambdaDemo(View view) {
        mActivity.startActivity(new Intent(mActivity, LambdaActivity.class));
    }

    public void onClickInjectDemo(View view) {
//        if (DemoApplication.isTest) {
//            DataBindingUtil.setDefaultComponent(new ProductionComponent());
//        } else {
//            DataBindingUtil.setDefaultComponent(new TestComponent());
//        }
//        DemoApplication.isTest = !DemoApplication.isTest;
//        recreate();
    }

}
