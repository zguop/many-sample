package com.example.waitou.rxjava.demos.expandablerecycler;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by waitou on 16/10/26.
 */

public class ExpandableRecyclerActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView mRecyclerView;

    private ExpandableRecyclerAdapter mAdapter;

    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.item_recyclerview;
    }

    @Override
    protected void initData() {

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));

        mAdapter = new ExpandableRecyclerAdapter(this);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setItems(DataUtils.getDummyData());
    }
}
