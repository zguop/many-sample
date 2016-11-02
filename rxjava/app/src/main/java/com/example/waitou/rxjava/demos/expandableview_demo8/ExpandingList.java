package com.example.waitou.rxjava.demos.expandableview_demo8;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.google.android.flexbox.FlexboxLayout;

/**
 * Created by waitou on 16/11/1.
 */

public class ExpandingList extends ScrollView {

    private NestFullFlexboxLayout mFullFlexboxLayout;

    public ExpandingList(Context context) {
        this(context,null);
    }

    public ExpandingList(Context context, AttributeSet attrs) {
        super(context, attrs);
        mFullFlexboxLayout = new NestFullFlexboxLayout(context);
        mFullFlexboxLayout.setFlexDirection(FlexboxLayout.FLEX_DIRECTION_COLUMN);
        addView(mFullFlexboxLayout);

//        setOnTouchListener((v, event) -> {
//            if (event.getAction() == MotionEvent.ACTION_UP)
//                requestDisallowInterceptTouchEvent(false);
//            else
//                requestDisallowInterceptTouchEvent(true);
//            return false;
//        });
    }

    public NestFullFlexboxLayout getBoxLayout(){
        return mFullFlexboxLayout;
    }

    public void scrollUpByDelta(int delta) {
        post(() -> smoothScrollTo(0, getScrollY() + delta));
    }
}
