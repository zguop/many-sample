package com.example.waitou.rxjava.roundlndicator;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.waitou.rxjava.R;

/**
 * 仿支付宝芝麻信用圆形仪表盘
 */

public class RoundIndicatorView extends View {

    private int typeCanArrival = 0;//默认支持到店服务

    private static final String STRING_TEXT_YES = "支持到店服务";
    private static final String STRING_WAN      = "万";

    private Paint mRoundPaint;
    private Paint paint;
    private Paint paint_2;
    private Paint paint_3;
    private Paint paint_4;

    private Context context;
    private int     maxNum;
    private int     startAngle;
    private int     sweepAngle;
    private float   radius;
    private float   sweepInWidth;//内圆的宽度
    private float   sweepOutWidth;//外圆的宽度
    private float   pointShadowLayer;
    private int   currentNum     = 0;//需设置setter、getter 供属性动画使用
    private int[] indicatorColor = {0xffffffff, 0x00ffffff, 0x99ffffff, 0xffffffff};
    private OnCalculateColorListener mOnCalculateColorListener;
    private RectF                    inRectF;
    private RectF                    outRectF;

    public RoundIndicatorView(Context context) {
        this(context, null);
    }

    public RoundIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundIndicatorView(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttr(attrs);
        initPaint();
    }

    public void setCurrentNumAnim(int num) {
        float duration = (float) Math.abs(num - currentNum) / maxNum * 50 + 1800; //根据进度差计算动画时间
        Log.i("aa", " duration" + duration);
        ObjectAnimator anim = ObjectAnimator.ofInt(this, "currentNum", num);
        anim.setDuration((long) Math.min(duration, 2500));
        anim.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            int color = calculateColor(value);
            if (mOnCalculateColorListener != null) {
                mOnCalculateColorListener.calculateColor(color);
            }
        });
        anim.start();
    }

    private int calculateColor(int value) {
        ArgbEvaluator evealuator = new ArgbEvaluator();
        float fraction;
        int color;
        if (value <= maxNum / 2) {
            fraction = (float) value / (maxNum / 2);
            color = (int) evealuator.evaluate(fraction, 0xFFFF6347, 0xFFFF8C00); //由红到橙
        } else {
            fraction = ((float) value - maxNum / 2) / (maxNum / 2);
            color = (int) evealuator.evaluate(fraction, 0xFFFF8C00, 0xFF00CED1); //由橙到蓝
        }
        return color;
    }

    private void initPaint() {
        //内外圆的宽度
        sweepInWidth = dp2px(10);
        sweepOutWidth = dp2px(4);
        pointShadowLayer = dp2px(13);

        //内外圆的笔
        mRoundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRoundPaint.setStrokeCap(Paint.Cap.ROUND);
        mRoundPaint.setStyle(Paint.Style.STROKE);
        mRoundPaint.setColor(Color.WHITE);
        mRoundPaint.setDither(true);
        mRoundPaint.setAntiAlias(true);

        //刻度的笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(dp2px(12));
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);

        //矩形进度的笔
        paint_2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_2.setStyle(Paint.Style.STROKE);
        paint_2.setStrokeCap(Paint.Cap.ROUND);
        paint_2.setStrokeWidth(sweepOutWidth);
        paint_2.setShader(new SweepGradient(0, 0, indicatorColor, null));

        //圆点的笔
        paint_3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_3.setStyle(Paint.Style.FILL);
        paint_3.setColor(Color.WHITE);
        paint_3.setMaskFilter(new BlurMaskFilter(dp2px(3), BlurMaskFilter.Blur.SOLID)); //需关闭硬件加速

        //中间文字的笔
        paint_4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_4.setStyle(Paint.Style.FILL);
        paint_4.setColor(Color.WHITE);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundIndicatorView);
        //最大数值
        maxNum = array.getInt(R.styleable.RoundIndicatorView_maxNum, 1000);
        //圆盘起始角度
        startAngle = array.getInt(R.styleable.RoundIndicatorView_startAngle, 160);
        //圆盘扫过角度
        sweepAngle = array.getInt(R.styleable.RoundIndicatorView_sweepAngle, 220);
        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        if (wMode != MeasureSpec.EXACTLY) {
            wSize = (int) dp2px(300);
        }
        if (hMode != MeasureSpec.EXACTLY) {
            hSize = (int) dp2px(400);
        }
        setMeasuredDimension(wSize, hSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w
                , h, oldw, oldh);
        float ra = getMeasuredWidth() / 3.5f;
        if (radius != ra) {
            radius = ra; //不要在构造方法里初始化，那时还没测量宽高
            inRectF = new RectF(-radius - pointShadowLayer, -radius - pointShadowLayer, radius + pointShadowLayer, radius + pointShadowLayer);
            outRectF = new RectF(-radius, -radius, radius, radius);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getMeasuredWidth() / 2, (float) ((getMeasuredHeight() / 2 + Math.sin(Math.toRadians(startAngle)) * radius) + 6f));
        drawRound(canvas);  //画内外圆
        drawScale(canvas);//画刻度
        drawIndicator(canvas); //画当前进度值
        drawCenterText(canvas);//画中间的文字
        canvas.restore();
    }

    private void drawCenterText(Canvas canvas) {
        canvas.save();
        paint_4.setTextSize(radius / 2.5f);
        float currentNumWidth = paint_4.measureText(currentNum + "");
        canvas.drawText(currentNum + "", -currentNumWidth / 1.75f, 0, paint_4);
        paint_4.setTextSize(radius / 6);
        canvas.drawText(STRING_WAN, currentNumWidth / 2.2f, 0, paint_4);
        if (typeCanArrival == 1) {
            Rect r = new Rect();
            paint_4.getTextBounds(STRING_TEXT_YES, 0, STRING_TEXT_YES.length(), r);
            canvas.drawText(STRING_TEXT_YES, -r.width() / 2, r.height() + dp2px(20), paint_4);
        }
        canvas.restore();
    }

    private void drawIndicator(Canvas canvas) {
        canvas.save();
        //绘制进度矩形
        int sweep;
        if (currentNum <= maxNum) {
            sweep = (int) ((float) currentNum / (float) maxNum * sweepAngle);
        } else {
            sweep = sweepAngle;
        }

        if(sweep != 0){
            canvas.drawArc(inRectF, startAngle, sweep, false, paint_2);
        }

        //绘制圆点
        float x = (float) ((radius + pointShadowLayer) * Math.cos(Math.toRadians(startAngle + sweep)));
        float y = (float) ((radius + pointShadowLayer) * Math.sin(Math.toRadians(startAngle + sweep)));
        canvas.drawCircle(x, y, dp2px(3), paint_3);
        canvas.restore();
    }

    private void drawScale(Canvas canvas) {
        canvas.save();
        float angle = (float) sweepAngle / 30;//刻度间隔
        canvas.rotate(-270 + startAngle); //将起始刻度点旋转到正上方（270)
        for (int i = 0; i <= 30; i++) {
            if (i % 6 == 0) {   //画粗刻度和刻度值
                paint.setStrokeWidth(dp2px(2));
                paint.setAlpha(255);
                canvas.drawLine(0, -radius - sweepInWidth / 2, 0, -radius + sweepInWidth / 2, paint);
                drawText(canvas, i * maxNum / 30 + "", paint);
            } else {         //画细刻度
                paint.setStrokeWidth(dp2px(1));
                paint.setAlpha(150);
                canvas.drawLine(0, -radius - sweepInWidth / 2, 0, -radius + sweepInWidth / 2, paint);
            }
//            if(i==3 || i==9 || i==15 || i==21 || i==27){  //画刻度区间文字
//                paint.setStrokeWidth(dp2px(2));
//                paint.setAlpha(0x50);
//                drawText(canvas,text[(i-3)/6], paint);
//            }
            canvas.rotate(angle); //逆时针
        }
        canvas.restore();
    }

    private void drawText(Canvas canvas, String text, Paint paint) {
        paint.setStyle(Paint.Style.FILL);
        float width = paint.measureText(text); //相比getTextBounds来说，这个方法获得的类型是float，更精确些
        canvas.drawText(text, -width / 2, -radius + dp2px(20), paint);
        paint.setStyle(Paint.Style.STROKE);
    }

    private void drawRound(Canvas canvas) {
        canvas.save();
        //内圆
        mRoundPaint.setStrokeWidth(sweepInWidth);
        mRoundPaint.setAlpha(150);
        canvas.drawArc(outRectF, startAngle, sweepAngle, false, mRoundPaint);
        //外圆
        mRoundPaint.setStrokeWidth(sweepOutWidth);
        mRoundPaint.setAlpha(50);
        canvas.drawArc(inRectF, startAngle, sweepAngle, false, mRoundPaint);
        canvas.restore();
    }

    private float dp2px(float dp) {
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                getResources().getDisplayMetrics());
    }


    public void setDrawCanArrival(int drawCanArrival) {
        this.typeCanArrival = drawCanArrival;
        if (drawCanArrival == 1) {
            invalidate();
        }
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
        invalidate();
    }

    public interface OnCalculateColorListener {
        void calculateColor(int color);
    }

    public void setOnCalculateColorListener(OnCalculateColorListener listener) {
        this.mOnCalculateColorListener = listener;
    }
}
