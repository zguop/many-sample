package com.example.waitou.rxjava.gesture_scroller.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by waitou on 16/11/25.
 */

public class ScrollerLayout extends ViewGroup {

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    /**
     * 手机按下时的屏幕坐标
     */
    private float mXDown;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;

    /**
     * 上次触发ACTION_MOVE事件时的屏幕坐标
     */
    private float mXLastMove;

    /**
     * 界面可滚动的左边界
     */
    private int leftBorder;

    /**
     * 界面可滚动的右边界
     */
    private int rightBorder;

    public ScrollerLayout(Context context) {
        this(context, null);
    }

    public ScrollerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        // 获取TouchSlop值
        mTouchSlop = viewConfiguration.getScaledPagingTouchSlop();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 为ScrollerLayout中的每一个子控件测量大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childView = getChildAt(i);
                // 为ScrollerLayout中的每一个子控件在水平方向上进行布局
                childView.layout(i * childView.getMeasuredWidth(), 0, (i + 1) * childView.getMeasuredWidth(), childView.getMeasuredHeight());
            }
            //初始化左右边界值
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(childCount - 1).getRight();
        }
    }

    /*
    * 如果是 拖拽 那么拦截掉事件传递 自己处理事件 走 onTouchEvent
    * */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下时的屏幕坐标
                mXDown = ev.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                //移动时的坐标
                mXMove = ev.getRawX();
                //移动的绝对值 差值
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;
                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                //上次记录的 坐标 减去本次移动的左边  得到 本地移动的距离
                int scrolledX = (int) (mXLastMove - mXMove);
                //如果移动的距离超出边界值 则 设置到边界
                //getScrollX() 获取移动的距离  每次移动的距离 + 已经移动的距离 如果超过边界值，则设置到最大位置处
                if (getScrollX() + scrolledX < leftBorder) {
                    scrollTo(leftBorder, 0);
                    return true;
                    //右边界需要加上整个view的宽度
                } else if (getScrollX() + getWidth() + scrolledX > rightBorder) {
                    scrollTo(rightBorder - getWidth(), 0);
                    return true;
                }
                //否则 自身不断移动
                scrollBy(scrolledX, 0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                //当手指抬起时，根据当前的滚动值来判定应该滚动到哪个子控件的界面
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                //第几个位置宽度 - 移动的距离  = 还需滚动的距离
                int dx = targetIndex * getWidth() - getScrollX();


                /*--------------- scroller使用完成滚动 ---------------*/
                //通过scroller完成后续的滚动动作
                //设置mScroller滚动的位置时，并不会导致View的滚动，通常是用mScroller记录/计算View滚动的位置，
                // 再重写View的computeScroll()，完成实际的滚动。
                //开始一个动画控制，由(startX , startY)在duration时间内前进(dx,dy)个单位，到达坐标为(startX+dx , startY+dy)处。
                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * computeScroll()是View的一个空方法，在父容器重画自己的孩子时，它会调用孩子的computeScroll方法。
     *
     */
    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            //获取mScroller当前水平滚动的位置
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
           // invalidate();
            postInvalidate();
        }
    }

    /**
     *  Scroller相关方法介绍
     *
     *  getCurrX() //获取Scroller当前水平滚动的位置
     *  getCurrY() //获取Scroller当前竖直滚动的位置
     *  getFinalX() //获取Scroller最终停止的水平位置
     *  getFinalY() //获取Scroller最终停止的竖直位置
     *  setFinalX(int newX) //设置Scroller最终停留的水平位置，没有动画效果，直接跳到目标位置
     *  setFinalY(int newY) //设置Scroller最终停留的竖直位置，没有动画效果，直接跳到目标位置
     *
     *  startScroll(int startX, int startY, int dx, int dy) //使用默认完成时间250ms
     *  startScroll(int startX, int startY, int dx, int dy, int duration)
     *  滚动，startX, startY为开始滚动的位置，dx,dy为滚动的偏移量, duration为完成滚动的时间
     *
     *  fling(int startX, int startY, int velocityX, int velocityY, int minX, int maxX, int minY, int maxY, int overX, int overY)
     *  基于快速滑动触发的滚动， 滚动的距离 由初始的fling速度决定
     *  startX 开始的X坐标
     *  startY 开始的Y坐标
     *  velocityX 初始速度X
     *  velocityY 初始速度Y （可以使用系统仍为的速度值）
     *  minX 最小的X坐标，
     *  maxX 最大的X坐标
     *  minY 最小的Y坐标
     *  maxY 最大的Y坐标  scroller不能滚动出超过这个坐标边界
     *  overX fling滚动超过有效值的范围
     *  overY fling滚动超过有效值的范围   水平 滚动越过两个方向都有可能
     *
     *  computeScrollOffset()
     *  返回值为boolean，true说明滚动尚未完成，false说明滚动已经完成。这是一个很重要的方法，通常放在View.computeScroll()中，
     *  用来判断是否滚动是否结束。
     *
     *  isFinished() 用来判断当前滚动是否结束
     *
     *
     *  --------- 分隔符 ---------
     *
     *  PhotoView 使用到了 OverScroller
     *  OverScroller 和 Scroller 有什么区别呢 ？
     *  OverScroller的介绍
     *  Scroller出现的比较早，在API1就有了，OverScroller是在API9才添加上的，出现的比较晚，所以功能比较完善，
     *  OverScroller提供了对超出滑动边界的情况的处理，这两个类80%的API是一致的。
     *  是 Scroller 的加强版，增加了滚出视图范围之后的回弹效果
     *  OverScroller新增方法介绍
     *
     *  isOverScrolled() //是否超出滚动边界
     *  springBack(int startX, int startY, int minX, int maxX, int minY, int maxY)
     *  //当你想回滚的时候，回滚的范围在有效的坐标范围内
     *
     *  notifyHorizontalEdgeReached(int startX, int finalX, int overX)
     *  notifyVerticalEdgeReached(int startY, int finalY, int overY)
     *  通知滚动是否到达边界,通常 这个信息来处理 知道什么时候已经开始滚动
     *
     *
     *  ##http://blog.csdn.net/u013598111/article/details/50198101?locationNum=3&fps=1
     */
}
