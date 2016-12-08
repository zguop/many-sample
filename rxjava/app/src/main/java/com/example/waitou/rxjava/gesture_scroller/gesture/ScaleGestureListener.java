package com.example.waitou.rxjava.gesture_scroller.gesture;

import android.util.Log;
import android.view.ScaleGestureDetector;

/**
 * Created by waitou on 16/12/4.
 */

public class ScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

    /**
     * ScaleGestureDetector
     * ScaleGestureDetector这个类是专门用于处理缩放的工具类，用法与GestureDetector类似,
     * 都是通过onTouchEvent()关联相应的MotionEvent的。
     * <p>
     * ScaleGestureDetector的一些公共方法介绍
     * onTouchEvent()
     * 关联MotionEvent。返回true代表该detector想继续接收后继的motion事件；否则反之。默认时该方法返回true。
     * getCurrentSpan ()
     * 返回手势过程中，组成该手势的两个触点的当前距离，以像素为单位的触点距离。
     * getEventTime ()
     * 返回事件被捕捉时的时间。
     * getFocusX ()
     * 返回当前手势焦点的 X 坐标。
     * getFocusY ()
     * 返回当前手势焦点的 Y 坐标。
     * getPreviousSpan ()
     * 返回手势过程中，组成该手势的两个触点的前一次距离。
     * getScaleFactor ()
     * 返回从前一个伸缩事件至当前伸缩事件的伸缩比率。该值定义为  (getCurrentSpan()  / getPreviousSpan())。
     * getTimeDelta ()
     * 返回前一次接收到的伸缩事件距当前伸缩事件的时间差，以毫秒为单位。
     * isInProgress ()
     * 如果手势处于进行过程中，返回 true.
     * 方法介绍参考文章 http://www.mamicode.com/info-detail-276128.html
     * 使用参考文章 http://blog.csdn.net/u010410408/article/details/39577399
     *
     */

    /**
     * 缩放时调用
     * 返回值代表本次缩放事件是否已被处理。
     * 如果已被处理，那么detector就会重置缩放事件；如果未被处理，detector会继续进行计算，修改getScaleFactor()的返回值，
     * 直到被处理为止。因此，它常用在判断只有缩放值达到一定数值时才进行缩放
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //获取比率
        float scaleFactor = detector.getScaleFactor();
        Log.i("aa", " onScale scaleFactor = " + scaleFactor);
        if (scaleFactor < 2) {
            return false;
        }
        Log.i("aa" , "onScale -- " + "可以做些该做的事情了 ");
        //进行缩放处理
        return true;
    }

    /**
     * 缩放开始调用。该detector是否处理后继的缩放事件。返回false时，不会执行onScale()。
     */
    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    /**
     * 缩放结束时调用。
     */
    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

    }


/**
 *  onScale scaleFactor = 1.0
 onTouch
 onScale scaleFactor = 1.0409456
 onTouch
 onScale scaleFactor = 1.0864315
 onTouch
 onScale scaleFactor = 1.10482
 onTouch
 onScale scaleFactor = 1.1187431
 onTouch
 onScale scaleFactor = 1.1325734
 onTouch
 onScale scaleFactor = 1.1460583
 onTouch
 onScale scaleFactor = 1.1394207
 */
}
