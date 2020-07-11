package com.example.waitou.rxjava.gesture_scroller.scroller;

import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by waitou on 16/11/25.
 */

public class ScrollerActivity extends BaseActivity {

    @BindView(R.id.scroll_to_btn)
    Button       mScrollToBtn;
    @BindView(R.id.scroll_by_btn)
    Button       mScrollByBtn;
    @BindView(R.id.parent)
    LinearLayout mParent;
    @BindView(R.id.text)
    JellyTextView mJellyTextView;

    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_scroller;
    }

    @Override
    protected void initData() {
        mJellyTextView.setOnTouchListener((v, event) -> {
            Log.e("aa" , "onTouch");
            return false;
        });

    }

    @OnClick(R.id.scroll_to_btn)
    public void scrollTo() {
        mParent.scrollTo(-60, -100);
    }

    @OnClick(R.id.scroll_by_btn)
    public void scrollBy() {
        mParent.scrollBy(-60, -100);
    }

}
