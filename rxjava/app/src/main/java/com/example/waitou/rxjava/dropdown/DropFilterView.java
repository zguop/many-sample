package com.example.waitou.rxjava.dropdown;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

/**
 * auth aboom
 * date 2018/8/3
 */
public class DropFilterView extends LinearLayout {

    private Context               mContext;
    private NestFullFlexboxLayout mNestFullFlexboxLayout;
    private TopFilterView         mTopFilterView;

    private int mChosenItem = -1;

    public DropFilterView(Context context) {
        this(context, null);
    }

    public DropFilterView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DropFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        setOrientation(VERTICAL);
        initViews();
    }

    private void initViews() {
        mNestFullFlexboxLayout = new NestFullFlexboxLayout(mContext);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mNestFullFlexboxLayout.setLayoutParams(params);
        addView(mNestFullFlexboxLayout);
        mTopFilterView = new TopFilterView(mContext);
        addView(mTopFilterView);
    }


    public <T> void bindUI(List<T> data, OnDropFilterBind<T> onDropFilterBind) {
        if (onDropFilterBind == null) {
            return;
        }
        if (onDropFilterBind.createTabViewId() == 0) {
            return;
        }

        mNestFullFlexboxLayout.setAdapter(onDropFilterBind.createTabViewId(), data, new NestFullFlexboxLayout.OnBindData<T>() {
            @Override
            public void onBind(int pos, int itemCount, T t, NestFullFlexboxLayout.NestFullViewHolder holder) {
                onDropFilterBind.onBindTabView(pos, t, holder);
                holder.getConvertView().setOnClickListener(v -> {
                    if (mChosenItem == pos) {
                        mTopFilterView.toggle();
                        return;
                    }
                    mChosenItem = pos;
                    onDropFilterBind.initRecyclerView(pos, mTopFilterView.getContentView());


                });
            }

            @Override
            public void setLayoutParams(int pos, NestFullFlexboxLayout.NestFullViewHolder holder) {

            }
        });
    }


    public interface OnDropFilterBind<T> {
        /**
         * 初始化tabView布局
         */
        int createTabViewId();

        void onBindTabView(int pos, T data, NestFullFlexboxLayout.NestFullViewHolder holder);

        void initRecyclerView(int pos, RecyclerView recyclerView);

    }
}

