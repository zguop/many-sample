package com.example.waitou.rxjava.dropdown;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.example.waitou.rxjava.R;


public class TopFilterView extends FrameLayout implements Animation.AnimationListener {

    private static final int DURATION = 350;

    private Context           mContext;
    private onDismissListener dismissListener;

    private ViewGroup    parentView;
    private RecyclerView contentView;
    private View         outSideView;
    private boolean      isAnimation;

    private float heightPercent = 0f;
    private int heightPixels;

    public TopFilterView(Context context) {
        this(context, null);
    }

    public TopFilterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopFilterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        heightPixels = context.getResources().getDisplayMetrics().heightPixels;
        initViews();
    }

    public void setDisMissListener(onDismissListener listener) {
        this.dismissListener = listener;
    }

    /**
     * 返回内置的RecyclerView让你去填充你的视图
     */
    public RecyclerView getContentView() {
        return contentView;
    }

    /**
     * 设置屏幕的最大占据比例
     * 屏幕的高度 * heightPercent - 140dp最小阴影
     * item最大高度超过则显示该最大比例值，否则wrap_content
     */
    public void setMaxDis(float heightPercent) {
        this.heightPercent = heightPercent;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (heightPercent != 0f) {
            int childCount = parentView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = parentView.getChildAt(i);
                if (childAt instanceof RecyclerView) {
                    childAt.measure(widthMeasureSpec, heightMeasureSpec);
                    int planHeight = childAt.getMeasuredHeight();
                    ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                    int height = (int) (heightPixels * heightPercent);
                    int maxHeight = height - dp2px(140f);  // 按钮和底部最小阴影
                    if (planHeight > maxHeight) {
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layoutParams.height = maxHeight;
                    } else {
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    }
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initViews() {
        LayoutInflater.from(mContext).inflate(R.layout.view_top_filter, this, true);
        parentView = findViewById(R.id.view_filter);
        outSideView = findViewById(R.id.view_outSide);
        contentView = findViewById(R.id.list_filter);
        outSideView.setOnClickListener(v -> dismiss());
    }

    public void show() {
        if (isAnimation) {
            return;
        }
        if (getVisibility() == VISIBLE) {
            return;
        }
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0);
        translate.setDuration(DURATION);
        Animation alpha = new AlphaAnimation(0.0f, 1.0f);
        alpha.setDuration(DURATION);
        translate.setAnimationListener(this);
        contentView.startAnimation(translate);
        outSideView.startAnimation(alpha);
        setVisibility(VISIBLE);
    }

    public void dismiss() {
        if (isAnimation) {
            return;
        }
        if (getVisibility() == GONE) {
            return;
        }
        Animation alpha = new AlphaAnimation(1.0f, 0.0f);
        Animation translate = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, -1.0f);
        translate.setDuration(DURATION);
        alpha.setDuration(DURATION);
        alpha.setAnimationListener(this);
        contentView.startAnimation(translate);
        outSideView.startAnimation(alpha);
        if (dismissListener != null) dismissListener.onDismiss();
    }

    public void toggle() {
        if (getVisibility() == VISIBLE) {
            dismiss();
        } else {
            show();
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {
        isAnimation = true;
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        isAnimation = false;
        if (animation instanceof AlphaAnimation) {
            setVisibility(GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    public interface onDismissListener {
        void onDismiss();
    }

    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
