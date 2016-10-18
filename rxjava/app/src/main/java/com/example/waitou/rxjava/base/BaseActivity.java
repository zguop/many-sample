package com.example.waitou.rxjava.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;

import com.example.waitou.rxjava.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * author   itxp
 * date     2016/7/2 14:04
 * des      baseActivity基类,可扩展
 */
public abstract class BaseActivity extends FragmentActivity {

    protected final CompositeSubscription mPendingSubscriptions = new CompositeSubscription();
    protected Subscription mSubscription;

    /**
     * 屏幕高宽 子类需复写 isScreenDisplayMetrics 进行获取
     */
    protected int mScreenHeight;
    protected int mScreenWidth;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (isOverridePendingTransition()) {
            switch (initOverridePendingTransitionModel()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(R.anim.appear_top_left_in,0);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
        super.onCreate(savedInstanceState);


        if(isScreenDisplayMetrics()){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            mScreenHeight = displayMetrics.heightPixels;
            mScreenWidth = displayMetrics.widthPixels;
        }

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        if(initContentViewID() != 0){
            setContentView(initContentViewID());
            mUnbinder = ButterKnife.bind(this);
        }

        initData();
    }

    /**
     * 是否让当前Activity使用动画 必须
     */
    protected abstract boolean isOverridePendingTransition();

    /**
     * 返回动画切换的模式 isOverridePendingTransition 返回true 需复写该方法返回模式
     */
    protected TransitionMode initOverridePendingTransitionModel() {
        return null;
    }

    /**
     * 返回布局视图 必须
     */
    protected abstract int initContentViewID();

    /**
     * 初始化数据
     */
    protected void initData() {}

    /**
     * 是否开启EventBus 子类复写开启 可选
     */
    protected boolean isRegisterEventBus(){
        return false;
    }

    /**
     * 是否获取屏幕高宽值 可选
     */
    protected boolean isScreenDisplayMetrics(){
        return false;
    }

    /**
     * 获取Bundle 可选
     */
    protected void getBundleExtras(Bundle extras){}

    /**
     * 页面跳转方法
     */
    protected void gotoActivity(Class<?> clazz){
        Intent intent = new Intent(getApplicationContext(),clazz);
        startActivity(intent);
    }

    /**
     * 绑定bundle数据页面跳转方法
     */
    protected void gotoActivity(Class<?> clazz, Bundle bundle){
        Intent intent = new Intent(getApplicationContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 页面跳转方法,并关闭当前activity
     */
    protected void gotoFinishActivity(Class<?> clazz){
        Intent intent = new Intent(getApplicationContext(),clazz);
        startActivity(intent);
        finish();
    }

    /**
     * 绑定bundle数据页面跳转方法,并关闭当前activity
     */
    protected void gotoFinishActivity(Class<?> clazz, Bundle bundle){
        Intent intent = new Intent(getApplicationContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * 页面跳转方法, requestCode回调
     */
    protected void gotoActivityForResult(Class<?> clazz, int requestCode){
        Intent intent = new Intent(getApplicationContext(), clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 页面跳转方法, requestCode回调 绑定bundle数据
     */
    protected void gotoActivityForResult(Class<?> clazz, int requestCode, Bundle bundle){
        Intent intent = new Intent(getApplicationContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void finish() {
        super.finish();
        if (isOverridePendingTransition()) {
            switch (initOverridePendingTransitionModel()) {
                case LEFT:
                    overridePendingTransition(R.anim.left_in,R.anim.left_out);
                    break;
                case RIGHT:
                    overridePendingTransition(R.anim.right_in,R.anim.right_out);
                    break;
                case TOP:
                    overridePendingTransition(R.anim.top_in,R.anim.top_out);
                    break;
                case BOTTOM:
                    overridePendingTransition(R.anim.bottom_in,R.anim.bottom_out);
                    break;
                case SCALE:
                    overridePendingTransition(0,R.anim.appear_top_left_out);
                    break;
                case FADE:
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                    break;
            }
        }
    }

    /**
     * 动画切换模式
     */
    public enum TransitionMode {
        LEFT, RIGHT, TOP, BOTTOM, SCALE, FADE
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(initContentViewID() != 0){
            mUnbinder.unbind();
        }
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mPendingSubscriptions.clear();
    }
}
