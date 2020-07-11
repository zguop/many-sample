package com.example.waitou.rxjava.dropdown;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by waitou on 16/10/12.
 * 动态添加view 带缓存 减少inflate
 */
public class NestFullFlexboxLayout<T> extends FlexboxLayout {

    private LayoutInflater           mInflater;
    private List<NestFullViewHolder> mCachesList;//缓存ViewHolder,按照add的顺序缓存，
    private List<T>                  mData; // 数据源
    private int                      mItemLayoutId; //itemId
    private OnBindData<T>            mBindData;

    public NestFullFlexboxLayout(Context context) {
        this(context, null);
    }

    public NestFullFlexboxLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NestFullFlexboxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mInflater = LayoutInflater.from(context);
        mCachesList = new ArrayList<>();
    }

    /**
     * 外部调用  同时刷新视图
     */
    public void setAdapter(int itemId, List<T> data, OnBindData<T> onBindData) {
        mItemLayoutId = itemId;
        mData = data;
        mBindData = onBindData;
        updateUI();
    }

    public interface OnBindData<T> {
        //更新数据
        void onBind(int pos, int itemCount, T t, NestFullViewHolder holder);

        //动态设置view大小 在add到父控件时调用
        void setLayoutParams(int pos, NestFullViewHolder holder);
    }

    public static abstract class OnSimpleBindData<T> implements OnBindData<T> {

        @Override
        public void setLayoutParams(int pos, NestFullViewHolder holder) {
        }
    }

    /**
     * 刷新数据
     */
    public void updateUI() {
        if (mData != null && mData.size() > 0) {
            if (mData.size() < getChildCount()) {//数据源小于现有子View，删除后面多的
                removeViews(mData.size(), getChildCount() - mData.size());
                //删除View也清缓存
                while (mCachesList.size() > mData.size()) {
                    mCachesList.remove(mCachesList.size() - 1);
                }
            }
            for (int i = 0; i < mData.size(); i++) {
                NestFullViewHolder holder;
                if (mCachesList.size() - 1 >= i) {//说明有缓存，不用inflate，否则inflate
                    holder = mCachesList.get(i);
                } else {
                    holder = new NestFullViewHolder(mInflater.inflate(mItemLayoutId, this, false));
                    mCachesList.add(holder);//inflate 出来后 add进来缓存
                }
                if (mBindData != null) {
                    mBindData.onBind(i, mData.size(), mData.get(i), holder);
                }
                //如果View没有父控件 添加
                if (holder.getConvertView().getParent() == null) {
                    addView(holder.getConvertView());
                    if (mBindData != null) {
                        mBindData.setLayoutParams(i, holder);
                    }
                }
            }
        } else {
            removeAllViews();
        }
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

        public View getConvertView() {
            return mConvertView;
        }
    }
}
