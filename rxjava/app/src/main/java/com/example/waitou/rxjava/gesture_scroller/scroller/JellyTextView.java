package com.example.waitou.rxjava.gesture_scroller.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.BounceInterpolator;
import android.widget.OverScroller;
import android.widget.TextView;

/**
 * Created by waitou on 16/11/29.
 */

public class JellyTextView extends TextView {
    private OverScroller mScroller;
    private float        lastX;//记录view停止滑动的X位置
    private float        lastY;//记录view停止滑动的Y位置

    private float startX;//记录最初始位置X坐标
    private float startY;//记录最初始位置Y坐标

    public JellyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //实例化对象OverScroller
        mScroller = new OverScroller(context, new BounceInterpolator());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //第一次获取静止的坐标
                lastX = event.getRawX();
                lastY = event.getRawY();
                return true; //注意，这里是让事件不要被拦截，继续触发ACTION_MOVE,ACTION_UP
            case MotionEvent.ACTION_MOVE:
                //移动的偏移量
                float disX = event.getRawX() - lastX;
                float disY = event.getRawY() - lastY;
                //完成控件的位置移动
                offsetLeftAndRight((int) disX);
                offsetTopAndBottom((int) disY);
                //获取移动后的坐标
                lastX = event.getRawX();
                lastY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                //移动view到原位置，同时触发computeScroll();
//                mScroller.startScroll((int)getX(), (int)getY(),  -(int) (getX() - startX),
//                        -(int) (getY() - startY));

                spingBack();
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {  //判断当前的滑动动作是否完成的
            setX(mScroller.getCurrX());
            setY(mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //记录View 的初始位置
        startX = getX();
        startY = getY();
        Log.d("aa", "onSizeChanged startX = " + startX + " startY = " + startY);
    }

    /**
     * 参数，前两个是开始位置，是绝对坐标，minX和maxX是用来设定滚动范围的，
     * 也是绝对坐标范围,如果startX不在这个范围里面，
     * 比如大于maxX，就会触发computeScroll()，我们可以移动距离，
     * 最终回弹到maxX所在的位置，并返回true，从而完成后续的滚动效果，比minX小的话，
     * 就会回弹到minX，一样的道理。所以我们可以像上面代码里面一样，判断是否在范围内，在的话，
     * 就invalidate()一下，触发滚动动画
     */
    public void spingBack() {
        //回滚到初始位置
        if (mScroller.springBack((int) getX(), (int) getY(), 0, (int) startX, 0,
                (int) startY)) {
            Log.d("aa", "getX()=" + getX() + "__getY()=" + getY());
            invalidate();
        }
    }
}
