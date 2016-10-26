package com.example.waitou.rxjava.demos.demo6;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;

import com.example.waitou.rxjava.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by waitou on 16/10/12.
 * 动态添加view 带缓存 减少inflate
 */
public class NestFullAnimationLayout<T> extends LinearLayout {

    private LayoutInflater           mInflater;
    //缓存ViewHolder,按照add的顺序缓存，
    private List<NestFullViewHolder> mCahcesList;
    // 数据源
    private List<T>                  mDatas;
    //itemId
    private int                      mItemLayoutId;
    //事件接口
    private OnBindDatas<T>           mBindDatas;
    //是否测量过子孩子
    private boolean isCalculatedSize = false;
    //记录每个子孩子的高度--叠加的
    private List<Integer> mChildSizeList;
    //是否正在执行动画
    private boolean isAnimating = false;
    //动画监听
    private OnAnimationListener mOnAnimationListener;
    //默认是展开的
    private boolean             mDefaultExpanded;
    //测量后的初始化 ，只进行一次
    private boolean isArranged        = false;
    //当前显示的view大小
    private int     closePositionSize = 0;
    //动画执行的时间
    private int mDuration;
    //默认的动画控制器
    private TimeInterpolator mInterpolator = new LinearInterpolator();
    //列表总高度
    private int     mLayoutSize;
    //列表是否是展开的 true 展开
    private boolean isExpanded;

    public NestFullAnimationLayout(Context context) {
        this(context, null);
    }

    public NestFullAnimationLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestFullAnimationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mInflater = LayoutInflater.from(context);
        mCahcesList = new ArrayList<>();
        mChildSizeList = new ArrayList<>();
        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.nestFullFlexboxLayout, defStyleAttr, 0);
        mDefaultExpanded = a.getBoolean(R.styleable.nestFullFlexboxLayout_nest_expanded, true);
        mDuration = a.getInteger(R.styleable.nestFullFlexboxLayout_nest_duration, 300);
        isExpanded = mDefaultExpanded;
        a.recycle();
    }

    /**
     * 外部调用  同时刷新视图
     */
    public void setAdapter(int itemId, List datas, OnBindDatas onBindDatas) {
        mItemLayoutId = itemId;
        mDatas = datas;
        mBindDatas = onBindDatas;
        updateUI();
    }

    public interface OnBindDatas<T> {
        //更新数据
        void onBind(int pos, int itemCount, T t, NestFullViewHolder holder);

        //动态设置view大小 在add到父控件时调用
        void setLayoutParams(int pos, NestFullViewHolder holder);

        //动态增删view 是否直接进行显示
        boolean addViewUpState();

        //view每次测量结束时调用，可确定 closePositionSize起始的关闭值
        int onMoveChildView(List<Integer> size, int closePositionSize);
    }

    public interface OnAnimationListener {
        //动画开始
        void onAnimationStart();

        //动画结束
        void onAnimationEnd();
    }

    /**
     * 刷新数据
     */
    private void updateUI() {
        if (mDatas != null && mDatas.size() > 0) {
            if (mDatas.size() < getChildCount()) {//数据源小于现有子View，删除后面多的
                removeViews(mDatas.size(), getChildCount() - mDatas.size());
                //删除View也清缓存
                initLayout();
                while (mCahcesList.size() > mDatas.size()) {
                    mCahcesList.remove(mCahcesList.size() - 1);
                }
            }
            for (int i = 0; i < mDatas.size(); i++) {
                NestFullViewHolder holder;
                if (mCahcesList.size() - 1 >= i) {//说明有缓存，不用inflate，否则inflate
                    holder = mCahcesList.get(i);
                } else {
                    holder = new NestFullViewHolder(mInflater.inflate(mItemLayoutId, this, false));
                    mCahcesList.add(holder);//inflate 出来后 add进来缓存
                }
                if (mBindDatas != null) {
                    mBindDatas.onBind(i, mDatas.size(), mDatas.get(i), holder);
                }
                //如果View没有父控件 添加
                if (holder.getConvertView().getParent() == null) {
                    addView(holder.getConvertView());
                    if (mBindDatas != null) {
                        mBindDatas.setLayoutParams(i, holder);
                    }
                    //新view添加到父控件中 重新更新测量子孩子的size
                    initLayout();
                }
            }
        } else {
            removeAllViews();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //如果没有进行子孩子大小的测量
        if (!isCalculatedSize) {
            //进行测量 清楚childSizeList集合中保存的子孩子的高度值
            mChildSizeList.clear();
            //获取数量
            final int childCount = getChildCount();
            if (childCount > 0) {
                //子View的大小
                int sumSize = 0;
                View view;
                LayoutParams params;
                for (int i = 0; i < childCount; i++) {
                    //获取每个view
                    view = getChildAt(i);
                    params = (LayoutParams) view.getLayoutParams();
                    //如果 大于了0 说明 获取到的子View不是第一个了
                    if (i > 0) {
                        //那么就获取到集合中保存的上一个子view的大小
                        sumSize = mChildSizeList.get(i - 1);
                    }
                    //添加当前子view的高度的集合中
                    mChildSizeList.add((isVertical() ?
                            view.getMeasuredHeight() + params.topMargin + params.bottomMargin :
                            view.getMeasuredWidth() + params.leftMargin + params.rightMargin)
                            + sumSize);
                }
                //获取最大的size大小
                mLayoutSize = mChildSizeList.get(childCount - 1);
                //已经测量过子View
                isCalculatedSize = true;
                //当view测量完毕时
                if (mChildSizeList.size() == mDatas.size()) {
                    //如果是打开的状态 设置一下最大的size
                    if (mBindDatas.addViewUpState() && isExpanded) {
                        setLayoutSize(mLayoutSize);
                    }
                    //每次view发生变量重新测量哈子时调用，closePositionSize 默认返回0 起始的关闭点，其实关闭点展开关闭可以进行控制
                    closePositionSize = mBindDatas.onMoveChildView(mChildSizeList, closePositionSize);
                }
            }
        }

        if(isArranged){
            return;
        }
        //如果没有展开  默认隐藏
        if (!mDefaultExpanded) {
            setLayoutSize(closePositionSize);
        }

        isArranged = true;
    }

    public static class OnSimpBindDatas<T> implements OnBindDatas<T> {

        @Override
        public void onBind(int pos, int itemCount, T t, NestFullViewHolder holder) {

        }

        @Override
        public void setLayoutParams(int pos, NestFullViewHolder holder) {

        }

        @Override
        public boolean addViewUpState() {
            return false;
        }

        @Override
        public int onMoveChildView(List<Integer> size, int closePositionSize) {
            return 0;
        }
    }

    /**
     * Initializes this layout.
     */
    void initLayout() {
        mLayoutSize = 0;
        isCalculatedSize = false;
        if (isVertical()) {
            measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.UNSPECIFIED));
        } else {
            measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.EXACTLY));
        }
    }

    /**
     * 列表展开收起
     */
    public void toggle() {
        toggle(mDuration, mInterpolator);
    }

    /**
     * 列表展开收起
     *
     * @param duration     执行时间
     * @param interpolator 动画控制器
     */
    public void toggle(final long duration, final @Nullable TimeInterpolator interpolator) {
        //closePositionSize 始终都是 0 如果收起的状态 getCurrentPosition测量结果为 0，则打开，
        if (closePositionSize < getCurrentPosition()) {
            collapse(duration, interpolator);
        } else {
            expand(duration, interpolator);
        }
    }

    /**
     * 收起列表
     */
    private void collapse(final long duration, final @Nullable TimeInterpolator interpolator) {
        if (isAnimating) {
            return;
        }
        createExpandAnimator(getCurrentPosition(), closePositionSize, duration < 0 ? 0 : duration, interpolator == null ? mInterpolator : interpolator).start();
    }

    /**
     * 展开列表
     */
    private void expand(final long duration, final @Nullable TimeInterpolator interpolator) {
        if (isAnimating) {
            return;
        }
        createExpandAnimator(getCurrentPosition(), mLayoutSize, duration < 0 ? 0 : duration, interpolator == null ? mInterpolator : interpolator).start();
    }

    public void collapse() {
        if (isAnimating) {
            return;
        }
        createExpandAnimator(getCurrentPosition(), closePositionSize, mDuration, mInterpolator).start();
    }

    /**
     * 显示index view
     */
    public void moveChild(int index) {
        moveChild(index, mDuration, mInterpolator);
    }

    public void moveChild(int index, long duration, @Nullable TimeInterpolator interpolator) {
        if (isAnimating) {
            return;
        }
        final int destination = getChildPosition(index);
        createExpandAnimator(getCurrentPosition(), destination, duration < 0 ? 0 : duration, interpolator == null ? mInterpolator : interpolator).start();
    }

    public void moveChild(int startIndex, int toIndex) {
        moveChild(startIndex, toIndex, mDuration, mInterpolator);
    }

    public void moveChild(int startIndex, int toIndex, long duration, @Nullable TimeInterpolator interpolator) {
        if (isAnimating) {
            return;
        }
        final int start = getChildPosition(startIndex);
        final int to = getChildPosition(toIndex);
        if (start > to && getCurrentPosition() < start || start < to && getCurrentPosition() > start) {
            return;
        }
        createExpandAnimator(start, to, duration < 0 ? 0 : duration, interpolator == null ? mInterpolator : interpolator).start();
    }

    /**
     * 设置动画世界
     */
    public void setDuration(int duration) {
        this.mDuration = duration;
    }

    /**
     * 获取index view的size
     */
    public int getChildPosition(final int index) {
        if (index < 0 || index >= mChildSizeList.size()) {
            throw new IllegalArgumentException("There aren't the view having this index.");
        }
        return mChildSizeList.get(index);
    }


    public void setOnAnimationListener(OnAnimationListener listener) {
        this.mOnAnimationListener = listener;
    }


    private ValueAnimator createExpandAnimator(
            final int from, final int to, final long duration, final TimeInterpolator interpolator) {
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(duration);
        valueAnimator.setInterpolator(interpolator);
        valueAnimator.addUpdateListener(animator -> {
            if (isVertical()) {
                getLayoutParams().height = (int) animator.getAnimatedValue();
            } else {
                getLayoutParams().width = (int) animator.getAnimatedValue();
            }
            requestLayout();
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                isAnimating = true;
                if (mOnAnimationListener != null) {
                    mOnAnimationListener.onAnimationStart();
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                isAnimating = false;
                //如果结束的to大于了关闭的值说明是展开的状态
                isExpanded = to > closePositionSize;
                if (mOnAnimationListener != null) {
                    mOnAnimationListener.onAnimationEnd();
                }
            }
        });
        return valueAnimator;
    }


    private int getCurrentPosition() {
        return isVertical() ? getMeasuredHeight() : getMeasuredWidth();
    }

    private void setLayoutSize(int size) {
        if (isVertical()) {
            getLayoutParams().height = size;
        } else {
            getLayoutParams().width = size;
        }
    }

    private boolean isVertical() {
        return getOrientation() == LinearLayout.VERTICAL;
    }

    public static class NestFullViewHolder {
        private View              mConvertView;
        private SparseArray<View> mViews;

        NestFullViewHolder(View view) {
            this.mConvertView = view;
            this.mViews = new SparseArray<>();
        }

        /**
         * 通过viewId获取控件
         */
        public <T extends View> T getView(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }

        View getConvertView() {
            return mConvertView;
        }
    }
}
