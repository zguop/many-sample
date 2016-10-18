package com.example.waitou.rxjava.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author   itxp
 * date     2016/7/2 17:11
 * des      fragment 基类 ,可扩展
 */
public abstract class BaseFragment extends Fragment {

    /**
     * 屏幕高宽 子类需复写 isScreenDisplayMetrics 进行获取
     */
    protected int mScreenHeight;
    protected int mScreenWidth;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(initContentViewID() != 0){
            View inflate = inflater.inflate(initContentViewID(), null);
            mUnbinder = ButterKnife.bind(this, inflate);
            return inflate;
        }else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isScreenDisplayMetrics()){
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            mScreenHeight = displayMetrics.heightPixels;
            mScreenWidth = displayMetrics.widthPixels;
        }

        Bundle arguments = getArguments();
        if(arguments != null){
            getBundleExtras(arguments);
        }

        initData();
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
     * 是否获取屏幕高宽值 可选
     */
    protected boolean isScreenDisplayMetrics(){
        return false;
    }

    /**
     * 获取Bundle 可选
     */
    protected void getBundleExtras(Bundle arguments) {}

    /**
     * 页面跳转方法
     */
    protected void gotoActivity(Class<?> clazz){
        Intent intent = new Intent(getActivity(),clazz);
        startActivity(intent);
    }

    /**
     * 绑定bundle数据页面跳转方法
     */
    protected void gotoActivity(Class<?> clazz, Bundle bundle){
        Intent intent = new Intent(getActivity(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

}
