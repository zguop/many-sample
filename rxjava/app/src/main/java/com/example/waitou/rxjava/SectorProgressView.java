package com.example.waitou.rxjava;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 小视频扇形进度条
 *
 * @version 5.2.0
 * @since 2016-06-01
 */
public class SectorProgressView extends View {

    private int bgColor;
    private int fgColor;
    /**
     * 背景画笔
     */
    private Paint bgPaint;
    /**
     * 进度画笔
     */
    private Paint fgPaint;
    private RectF oval;
    /**
     * 开始加载进度的位置
     */
    private float startAngle;
    /**
     * 当前进度
     */
    private float percent;
    /**
     * 中心坐标
     */
    private int mCentre;
    /**
     * 半径
     */
    private int center;

    private float interval;

    public SectorProgressView(Context context) {
        this(context, null);
    }

    public SectorProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SectorProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SectorProgressView, 0, 0);
        try {
            bgColor = a.getColor(R.styleable.SectorProgressView_bgColor, 0xFFFFFFFF);
            fgColor = a.getColor(R.styleable.SectorProgressView_fgColor, 0xFFFFFFFF);
            percent = a.getFloat(R.styleable.SectorProgressView_percent, 0);
            startAngle = a.getFloat(R.styleable.SectorProgressView_startAngle, 0) + 270;
            interval = a.getInt(R.styleable.SectorProgressView_interval, dip2px(2.5f));
        } finally {
            a.recycle();
        }

        init();
    }

    /**
     * 初始化画笔属性
     */
    private void init() {
        bgPaint = new Paint();
        // 设置圆环的颜色
        bgPaint.setColor(bgColor);
        // 消除锯齿
        bgPaint.setAntiAlias(true);
        // 设置空心
        bgPaint.setStyle(Paint.Style.STROKE);
        // 设置圆环的宽度
        bgPaint.setStrokeWidth(3.0f);

        fgPaint = new Paint();
        fgPaint.setColor(fgColor);
        fgPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // 获取圆心的x坐标
        center = getWidth() / 2; //20

        float mRadius = center - 5;
        // 设置进度是实心还是空心
        oval = new RectF(center - mRadius + 6, center - mRadius + 6, center + mRadius - 6, center + mRadius - 6); // 用于定义的圆弧的形状和大小的界限
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画出圆环
        canvas.drawCircle(center, center, center - bgPaint.getStrokeWidth(), bgPaint);
        // 画出进度
        canvas.drawArc(oval, startAngle, percent * 3.6f, true, fgPaint);

    }

    /**
     * 返回进度背景颜色
     *
     * @return 进度背景颜色
     */
    public int getBgColor() {
        return bgColor;
    }

    /**
     * 设置进度背景颜色
     *
     * @param bgColor 进度背景颜色
     */
    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        refreshTheLayout();
    }

    /**
     * 返回进度显示颜色
     *
     * @return 进度显示颜色
     */
    public int getFgColor() {
        return fgColor;
    }

    /**
     * 设置进度显示颜色
     *
     * @param fgColor 进度显示颜色
     */
    public void setFgColor(int fgColor) {
        this.fgColor = fgColor;
        refreshTheLayout();
    }

    /**
     * 刷新当前布局
     */
    private void refreshTheLayout() {
        invalidate();
        requestLayout();
    }

    /**
     * 返回进度显示的位置
     *
     * @return 进度显示的位置
     */
    public float getStartAngle() {
        return startAngle;
    }

    /**
     * 设置进度显示的位置
     *
     * @param startAngle 进度显示的位置
     */
    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle + 270;
        invalidate();
        requestLayout();
    }

    /**
     * 当前进度
     *
     * @return 进度
     */
    public float getPercent() {
        return percent;
    }

    /**
     * 当前正在下载
     *
     * @return 当前正在下载
     */
    public boolean isPercenting() {
        return percent > 0 && percent < 100;
    }

    /**
     * 设置当前进度
     *
     * @param percent 当前进度
     */
    public void setPercent(float percent) {
        this.percent = percent;
        invalidate();
        requestLayout();
    }

    private int dip2px(float dp) { return (int) (dp * getContext().getResources().getDisplayMetrics().density); }

}
