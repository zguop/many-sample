package com.example.waitou.rxjava;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2018/7/31
 */
public class CircleProgressView extends View {

    private List<Float> pointRadiusList;

    private float radius;
    private int   pointNum;
    private float pointRadius;
    private float pointPadding;

    private Paint mPaint;
    private Paint mPointPaint;

    private AnimatorSet mAnimatorSet;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        int pointColor = typedArray.getColor(R.styleable.CircleProgressView_qy_point_color, Color.WHITE);
        int bgColor = typedArray.getColor(R.styleable.CircleProgressView_qy_bg_color, Color.parseColor("#4C000000"));
        radius = typedArray.getFloat(R.styleable.CircleProgressView_qy_radius, dp2px(28));
        pointNum = typedArray.getInt(R.styleable.CircleProgressView_qy_point_num, 3);
        pointRadius = typedArray.getFloat(R.styleable.CircleProgressView_qy_point_radius, dp2px(3));
        pointPadding = typedArray.getDimension(R.styleable.CircleProgressView_qy_point_padding, dp2px(4));
        typedArray.recycle();
        pointRadiusList = new ArrayList<>(pointNum);
        mAnimatorSet = new AnimatorSet();
        for (int i = 0; i < pointNum; i++) {
            pointRadiusList.add(pointRadius);
            mAnimatorSet.playSequentially(buildAnimator(i, i * 300));
        }
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(bgColor);
        mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(pointColor);
    }

    private ValueAnimator buildAnimator(int index, long delay) {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(1f, 1.6f).setDuration(500);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.setStartDelay(delay);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            float value = (float) valueAnimator.getAnimatedValue();
            pointRadiusList.set(index, pointRadius * value);
            postInvalidate();
        });
        return valueAnimator;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    public void start() {
        Log.e("aa " , " start");

        if (mAnimatorSet.isRunning()) {
            return;
        }
        mAnimatorSet.start();
    }

    public void stop() {
        Log.e("aa " , " stop");
        if (mAnimatorSet.isRunning()) {
            mAnimatorSet.end();
            mAnimatorSet.cancel();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        if (wMode != MeasureSpec.EXACTLY) {
            wSize = (int) (radius * 2);
        }
        if (hMode != MeasureSpec.EXACTLY) {
            hSize = (int) (radius * 2);
        }
        setMeasuredDimension(wSize, hSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(radius, radius, radius, mPaint);
        for (int i = 0; i < pointNum; i++) {
            float pointStartX = pointStartX(i);
            float pointRadius = pointRadiusList.get(i);
            canvas.drawCircle(pointStartX, radius, pointRadius, mPointPaint);
        }
    }

    private float pointStartX(int index) {
        float padding = ViewCompat.getPaddingStart(this) + (pointRadius * 2 + pointPadding) * index;
        return getWidth() / 2 + padding - ((pointRadius + pointPadding / 2) * (pointNum - 1));
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
