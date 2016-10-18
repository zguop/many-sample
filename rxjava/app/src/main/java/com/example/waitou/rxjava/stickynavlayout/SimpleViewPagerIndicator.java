package com.example.waitou.rxjava.stickynavlayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by waitou on 16/9/10.
 */
public class SimpleViewPagerIndicator extends LinearLayout {

    private static final int COLOR_TEXT_NORMAL     = 0xFF000000;
    private static final int COLOR_INDICATOR_COLOR = Color.GREEN;

    private Paint mPaint          = new Paint();
    private int   mIndicatorColor = COLOR_INDICATOR_COLOR;
    private int      mTabWidth;
    private float    mTranslationX;
    private int      mTabCount;
    private String[] mTitles;
    private onSeleClick mSeleClick;

    public SimpleViewPagerIndicator(Context context) {
        this(context, null);
    }

    public SimpleViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint.setColor(mIndicatorColor);
        mPaint.setStrokeWidth(9.0F);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTabWidth = w / mTabCount;
    }

    public void setIndicatorColor(int indicatorColor) {
        this.mIndicatorColor = indicatorColor;
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
        mTabCount = titles.length;
        generateTitleView();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        canvas.save();
        canvas.translate(mTranslationX, getHeight() - 2);
        canvas.drawLine(0, 0, mTabWidth, 0, mPaint);
        canvas.restore();
    }

    public void srcool(int position,float offset){
        mTranslationX = getWidth() / mTabCount * (position + offset);
        invalidate();
    }

    private void generateTitleView() {
        if(getChildCount() > 0 ){
            removeAllViews();
        }
        int count = mTitles.length;
        //平分
        setWeightSum(count);

        for (int i = 0; i < count; i++) {
            TextView tv = new TextView(getContext());
            LayoutParams lp = new LayoutParams(0, LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            tv.setGravity(Gravity.CENTER);
            tv.setTextColor(COLOR_TEXT_NORMAL);
            tv.setText(mTitles[i]);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            addView(tv,lp);
            final int finalI = i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mSeleClick != null){
                        mSeleClick.seleClikc(finalI);
                    }
                }
            });
        }
    }



    public interface onSeleClick{
        void seleClikc(int index);
    }

    public void setOnSeleCilick(onSeleClick cilick){
        mSeleClick = cilick;
    }

}
