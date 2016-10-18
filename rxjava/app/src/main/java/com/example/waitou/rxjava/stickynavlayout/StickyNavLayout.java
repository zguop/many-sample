package com.example.waitou.rxjava.stickynavlayout;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import com.example.waitou.rxjava.R;

/**
 * Created by waitou on 16/9/10.
 */
public class StickyNavLayout extends LinearLayout implements NestedScrollingParent{

    private OverScroller mScroller;
    private View mTopView;
    private View mNav;
    private ViewPager mViewPager;

    private int mTopViewHeight;

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //1onStartNestedScroll
        return super.onStartNestedScroll(child, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        //2onNestedScrollAccepted
        super.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onStopNestedScroll(View child) {
        //5onStopNestedScroll
        super.onStopNestedScroll(child);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //3onNestedPreScroll
        super.onNestedPreScroll(target, dx, dy, consumed);
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //       4onNestedFling
        return super.onNestedPreFling(target, velocityX, velocityY);
    }

    public StickyNavLayout(Context context) {
        this(context,null);
    }

    public StickyNavLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTopView = findViewById(R.id.id_stickynavlayout_topview);
        mNav = findViewById(R.id.id_stickynavlayout_indicator);
        mViewPager = (ViewPager) findViewById(R.id.id_stickynavlayout_viewpager);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("aa" ,"onMeasure"  + " widthMeasureSpec=" + widthMeasureSpec + " heightMeasureSpec=" + heightMeasureSpec);
        getChildAt(0).measure(widthMeasureSpec,MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = getMeasuredHeight() - mNav.getMeasuredHeight();
        setMeasuredDimension(getMeasuredWidth(), mTopView.getMeasuredHeight() + mNav.getMeasuredHeight() + mViewPager.getMeasuredHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("aa" ,"onSizeChanged" + " w =" + w + " h=" + h + " oldw=" + oldw + " oldh=" + oldh);
        mTopViewHeight = mTopView.getMeasuredHeight();
    }

    @Override
    public void scrollTo(int x, int y) {

        if(y < 0){
            y = 0;
        }

        if(y > mTopViewHeight){
            y = mTopViewHeight;
        }

        if(y != getScrollY()){
            super.scrollTo(x, y);
        }
    }
}
