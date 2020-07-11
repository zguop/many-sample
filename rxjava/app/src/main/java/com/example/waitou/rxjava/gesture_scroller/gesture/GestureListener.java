package com.example.waitou.rxjava.gesture_scroller.gesture;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by waitou on 16/11/28.
 */

public class GestureListener extends GestureDetector.SimpleOnGestureListener {

    /*--------------- OnGestureListener ---------------*/

    /**
     * 手指按下就会触发（调用onTouch()方法的ACTION_DOWN事件时触发）
     */
    @Override
    public boolean onDown(MotionEvent e) {
        Log.i("aa", "onDown ");
        return false;
    }

    /**
     * down事件发生而move或则up还没发生前触发该事件 (100毫秒内)
     * Touch了还没有滑动时触发（与onDown，onLongPress）比较onDown只要Touch down一定立刻触发。
     * 而Touchdown后过一会没有滑动先触发onShowPress再是onLongPress。所以Touchdown后一直不滑动onLongPress之前触发
     */
    @Override
    public void onShowPress(MotionEvent e) {
        Log.i("aa", " onShowPress ");
    }

    /**
     * 一次点击up事件；在touch down后又没有滑动（onScroll），也没有长按（onLongPress），然后Touch up时触发
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i("aa", " onSingleTapUp ");
        return false;
    }

    /**
     * 滚动，触摸屏按下后移动（执行onTouch()方法的ACTION_MOVE事件时会触发）
     *
     * @param e1        按下的点
     * @param e2        滑动后结束的点
     * @param distanceX x轴方向的速度，单位：像素/秒
     * @param distanceY y轴方向的速度
     * @return 事件是否自己消费，拦截。
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.i("aa", "onScroll distanceX = " + distanceX + " distanceY = " + distanceY);
        return false;
    }

    /**
     * 长按，触摸屏按下后既不抬起也不移动，过一段时间后触发
     */
    @Override
    public void onLongPress(MotionEvent e) {
        Log.i("aa", " onLongPress ");
    }

    /**
     * 滑动，触摸屏按下后快速移动并抬起，会先触发滚动手势，跟着触发一个滑动手势
     *
     * @param e1        按下的点
     * @param e2        滑动后结束的点
     * @param velocityX x轴方向的速度，单位：像素/秒
     * @param velocityY y轴方向的速度
     * @return 事件是否自己消费，拦截。
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float e2X = e2.getX();
        float e1X = e1.getX();
        if(e2X - e1X > 50){
            Log.i("aa" , " 从左往右滑");
        }else if(e2X - e1X < - 50){
            Log.i("aa" , " 从右往左滑");
        }
        Log.i("aa", " onFling velocityX = " + velocityX + " velocityY = " + velocityY);
        return false;
    }

    /*--------------- OnDoubleTapListener ---------------*/

    /**
     * 1. 单击确认，用来判定该次点击是SingleTap而不是DoubleTap，如果连续点击两次就是DoubleTap手势，
     * 如果只点击一次，系统等待一段时间后没有收到第二次点击则判定该次点击为SingleTap而不是DoubleTap，
     * 然后触发SingleTapConfirmed事件。
     *
     * onSingleTapConfirmed和onSingleTapUp的一点区别
     * onSingleTapUp，只要手抬起就会执行，而对于onSingleTapConfirmed来说，如果双击的话，则onSingleTapConfirmed不会执行。
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.i("aa", " onSingleTapConfirmed ");
        return super.onSingleTapConfirmed(e);
    }

    /**
     * 在双击的第二下，Touch down时触发 。
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i("aa", " onDoubleTap ");
        return super.onDoubleTap(e);
    }

    /**
     * 双击的第二下Touch down和up都会触发，可用e.getAction()区分。 双击后 改方法调用二次
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.i("aa", " onDoubleTapEvent " + e.getAction());
        return super.onDoubleTapEvent(e);
    }


    /**
     * 调用顺序
     * 单击
     * onDown -> onSingleTapUp -> onSingleTapConfirmed
     * 长按
     * onDown -> onShowPress -> onLongPress
     * 双击 调用该onDoubleTap 方法 说明已经发生了双击
     * onDown -> onSingleTapUp -> onDoubleTap -> onDoubleTapEvent 0 -> onDown -> onDoubleTapEvent 1
     * 拖动滑动      onFling触发 滑动速度大于  MINIMUM_FLING_VELOCITY = 50 否则不调用
     * onDown -> onScroll -> onScroll ... -> onFling
     */

    /**
     * 经典例子 左右滑动的判断
     * 注意点：
     * 每次有效的滑动都会调用onScroll()方法，也就是说在一个完成的滑动事件中，onScroll()会进行频繁的调用，
     * 这会带来一些不必要的问题。
     * 而onFing()方法的优点是，在一个完整的滑动周期中，它只会调用一次，因为它代表的是一个完整的快速滑动的行为。
     *
     * 在onFing 中判断进行左右滑动的判断
     */
}
