package com.example.waitou.rxjava.demos.demo2;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.waitou.rxjava.*;
import com.example.waitou.rxjava.base.BaseActivity;
import com.example.waitou.rxjava.demos.demo2.transforms.ZoomOutTransformer;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by waitou on 16/10/12.
 */

public class VerticalViewPagerActivity extends BaseActivity {

    int[]    icon1 = {R.drawable.img1,R.drawable.img2,R.drawable.img3,R.drawable.img5, R.drawable.img6, R.drawable.img7};
    int[]    icon2 = {R.drawable.img7, R.drawable.img6, R.drawable.img5,R.drawable.img3,R.drawable.img2,R.drawable.img1};
    String[] text1 = {"美女1", "美女2", "美女3","美女4", "美女5", "美女6"};
    String[] text2 = {"美女6", "美女5", "美女4","美女3", "美女2", "美女1"};

    @BindView(R.id.loadingView)
    BounceLoadingView mBounceLoadingView;

    @BindView(R.id.layout)
    RelativeLayout mRelativeLayout;

    @BindView(R.id.page1)
    VerticalViewPager mViewPager1;

    @BindView(R.id.page2)
    VerticalViewPager mViewPager2;

    @Override
    protected boolean isOverridePendingTransition() {
        return true;
    }

    @Override
    protected TransitionMode initOverridePendingTransitionModel() {
        return TransitionMode.BOTTOM;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_verticalviewpager;
    }

    @OnClick(R.id.change_btn)
    public void onChangeBtnClick() {
        mViewPager1.setCurrentItem(mViewPager1.getCurrentItem() - 1 == -1 ? icon1.length - 1 : mViewPager1.getCurrentItem() - 1);
        mViewPager2.setCurrentItem(mViewPager2.getCurrentItem() - 1 == -1 ? icon2.length - 1 : mViewPager2.getCurrentItem() - 1);
    }

    @Override
    protected void initData() {
        mBounceLoadingView.addBitmap(R.drawable.img2);
        mBounceLoadingView.addBitmap(R.drawable.img3);
        mBounceLoadingView.addBitmap(R.drawable.img5);
        mBounceLoadingView.addBitmap(R.drawable.img6);
        mBounceLoadingView.addBitmap(R.drawable.img7);
        mBounceLoadingView.addBitmap(R.drawable.my);
        mBounceLoadingView.setShadowColor(Color.LTGRAY);
        mBounceLoadingView.setDuration(700);
        mBounceLoadingView.start();
        mSubscription = Observable.timer(3, TimeUnit.SECONDS, AndroidSchedulers.mainThread()).subscribe(res -> {
            mBounceLoadingView.stop();
            mBounceLoadingView.setVisibility(View.GONE);
            mRelativeLayout.setVisibility(View.VISIBLE);
        });
        mPendingSubscriptions.add(mSubscription);

        FixedSpeedScroller scroller = new FixedSpeedScroller(this);
        scroller.setmDuration(2500);
        scroller.initViewPagerScroll(mViewPager1);

        FixedSpeedScroller scroller2 = new FixedSpeedScroller(this);
        scroller2.setmDuration(1500);
        scroller2.initViewPagerScroll(mViewPager2);

        mViewPager1.setPageTransformer(true, new ZoomOutTransformer());
        mViewPager2.setPageTransformer(true, new ZoomOutTransformer());

        mViewPager1.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PageFragment.newInstance(icon1[position], text1[position]);
            }

            @Override
            public int getCount() {
                return icon1.length;
            }
        });


        mViewPager2.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PageFragment.newInstance(icon2[position], text2[position]);
            }

            @Override
            public int getCount() {
                return icon2.length;
            }
        });
        mViewPager1.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mViewPager2.setOverScrollMode(View.OVER_SCROLL_NEVER);
        mViewPager1.setCurrentItem(icon1.length - 1);
        mViewPager2.setCurrentItem(icon1.length - 1);
    }
}
