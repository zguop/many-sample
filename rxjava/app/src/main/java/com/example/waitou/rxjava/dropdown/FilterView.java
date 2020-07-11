package com.example.waitou.rxjava.dropdown;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.util.DeviceUtil;

/**
 * auth aboom
 * date 2018/8/2
 */
public class FilterView extends FrameLayout {

    private RecyclerView      mRecyclerView;
    private onDismissListener mOnDismissListener;
    private View              mOutSide;

    public FilterView(Context context) {
        this(context, null);
    }

    public FilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        int heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        initViews(context);
        setVisibility(GONE);
    }

    private void initViews(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_filter, this, true);
        mRecyclerView = findViewById(R.id.rv_filter);
        mOutSide = findViewById(R.id.view_outSide);
        mOutSide.setOnClickListener(v -> dismiss());
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    /**
     * 设置recyclerView 最大高度
     */
    public void setMaxContentView(int itemCount) {
        ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
        int height = DeviceUtil.getDeviceHeight(getContext()) - dp2px(93f);
        int planHeight = itemCount * dp2px(44.5f);// 原本高度
        int maxHeight = height - dp2px(140f);  // 按钮和底部最小阴影
        if (planHeight > maxHeight) {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = maxHeight;
        } else {
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        mRecyclerView.setLayoutParams(layoutParams);
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void dismiss() {
        ObjectAnimator.ofFloat(mRecyclerView, View.TRANSLATION_Y.getName(), -mRecyclerView.getHeight())

                .start();
        setVisibility(GONE);
        if (mOnDismissListener != null) mOnDismissListener.onDismiss();
    }

    public void show() {
        setVisibility(VISIBLE);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRecyclerView, View.TRANSLATION_Y.getName(), 0);
        objectAnimator.setDuration(300);
        objectAnimator.start();

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mOutSide, View.ALPHA.getName(), 0, 1);
        objectAnimator1.setDuration(300);
        objectAnimator1.start();

    }

    public interface onDismissListener {
        void onDismiss();
    }
}
