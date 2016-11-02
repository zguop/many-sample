package com.example.waitou.rxjava.demos.expandableview_demo8;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.app.ActivityCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.util.DeviceUtil;

/**
 * Created by waitou on 16/9/25.
 * textView 右上角绘制一个小圆点
 */
public class DotTipTextView extends TextView {

    private int tipVisibility = 0;

    public DotTipTextView(Context context) {
        this(context, null);
    }

    public DotTipTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.DotTipTextView);
            tipVisibility = array.getInt(R.styleable.DotTipTextView_dotTipsVisibility, 0);
            array.recycle();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (tipVisibility == 1) {
            int paddingRight = DeviceUtil.dip2px(5);
            Paint paint = getPaint();
            paint.setColor(ActivityCompat.getColor(getContext(), R.color.orange_FF9933));
            paint.setAntiAlias(true);
            paint.setDither(true);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            float textWidth = paint.measureText(getText().toString());
            if (textWidth <= getWidth()) {
                canvas.drawCircle(textWidth + DeviceUtil.dip2px(20), paddingRight * 3, paddingRight / 2, paint);
            } else {
                canvas.drawCircle(getWidth() - DeviceUtil.dip2px(20), paddingRight * 3, paddingRight / 2, paint);
            }

        }
    }


    public void setCircleVisibility(int visibility) {
        tipVisibility = visibility;
        invalidate();
    }

}
