package com.example.waitou.rxjava.dropdown;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.waitou.rxjava.R;

/**
 * auth aboom
 * date 2018/8/2
 */
public class FilterAdapter extends BaseQuickAdapter<FilterDownBean, BaseViewHolder> {

    private int lastPosition = -1;

    public FilterAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, FilterDownBean item) {
        helper.setText(R.id.text, item.content);
        if (helper.getAdapterPosition() == lastPosition) {
            helper.setTextColor(R.id.text, Color.RED);
        } else {
            helper.setTextColor(R.id.text, Color.BLACK);
        }
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public int getLastPosition() {
        if (lastPosition == -1) {
            return 0;
        }
        return lastPosition;
    }
}
