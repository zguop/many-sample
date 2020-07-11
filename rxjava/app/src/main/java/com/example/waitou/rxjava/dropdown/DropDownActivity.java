package com.example.waitou.rxjava.dropdown;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * auth aboom
 * date 2018/8/2
 */
public class DropDownActivity extends BaseActivity {

    List<FilterBean> mStringList = new ArrayList<>();

    private List<FilterAdapter> mFilterAdapters = new ArrayList<>();

    private int lastPosition = -1;

    private TopFilterView         mFilterView;
    private NestFullFlexboxLayout mFullFlexboxLayout;

    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_drop;
    }

    public List<FilterDownBean> getItemBean(int size) {
        List<FilterDownBean> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            FilterDownBean filterDownBean = new FilterDownBean();
            filterDownBean.content = "条目 " + i;
            list.add(filterDownBean);
        }
        return list;
    }

    public void onFilterDataChanged(List<FilterBean> list) {
        if (list == null || list.size() == 0) return;
        mFullFlexboxLayout.setAdapter(R.layout.item_tab, list, new NestFullFlexboxLayout.OnBindData<FilterBean>() {
            @Override
            public void onBind(int pos, int itemCount, FilterBean o, NestFullFlexboxLayout.NestFullViewHolder holder) {
                TextView tab = holder.getView(R.id.tab);
                tab.setText(o.tab);
                tab.setOnClickListener(v -> {
                    onFilterTabClick(list, pos);
                });

            }

            @Override
            public void setLayoutParams(int pos, NestFullFlexboxLayout.NestFullViewHolder holder) {

            }
        });


    }

    public void onFilterTabClick(List<FilterBean> list, int position) {
        createFilterSAdapter(list.size());
        fillFilterSelections(list, position);
        toggleFilterView(position);
    }

    /**
     * tab 筛选项
     */
    private void fillFilterSelections(List<FilterBean> list, int position) {
        FilterBean filterBean = list.get(position);
        FilterAdapter filterAdapter = mFilterAdapters.get(position);
        RecyclerView.LayoutManager layoutManager = null;
        if (position == 2) {
            layoutManager = getGrid();
        } else {
            layoutManager = getManger();
        }

        mFilterView.getContentView().setLayoutManager(layoutManager);
        mFilterView.getContentView().setAdapter(filterAdapter);
        mFilterView.getContentView().scrollToPosition(filterAdapter.getLastPosition());
        filterAdapter.replaceData(filterBean.list);
        filterAdapter.setOnItemClickListener((adapter, view, position1) -> {
            FilterDownBean item = filterAdapter.getItem(position1);
            FilterBean filterBean1 = list.get(position);
            filterBean1.tab = item.content;
            filterAdapter.setLastPosition(position1);
            onFilterDataChanged(list);
            mFilterView.dismiss();
        });
    }

    /**
     * 创建 筛选项适配器
     */
    public void createFilterSAdapter(int size) {
        if (mFilterAdapters != null && mFilterAdapters.size() > 0) return;
        for (int i = 0; i < size; i++) {
            FilterAdapter adapter = new FilterAdapter(R.layout.item_text);
            mFilterAdapters.add(adapter);
        }
    }

    /**
     * 下拉filter 显示和不显示
     */
    public void toggleFilterView(int position) {
        if (lastPosition != position) {
            mFilterView.show();
        } else {
            mFilterView.toggle();
        }
        lastPosition = position;
    }


    @Override
    protected void initData() {
        FilterBean filterBean = new FilterBean();
        filterBean.tab = "筛选";
        filterBean.list = getItemBean(20);
        mStringList.add(filterBean);

        FilterBean filterBean1 = new FilterBean();
        filterBean1.tab = "楼栋-A";
        filterBean1.list = getItemBean(9);
        mStringList.add(filterBean1);


        FilterBean filterBean2 = new FilterBean();
        filterBean2.tab = "楼层-全部";
        filterBean2.list = getItemBean(9);
        mStringList.add(filterBean2);

        mFullFlexboxLayout = findViewById(R.id.full_layout);
        mFilterView = findViewById(R.id.filter);
        mFilterView.setMaxDis(0.7f);
        onFilterDataChanged(mStringList);

    }


    public RecyclerView.LayoutManager getManger() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);


        return linearLayoutManager;
    }

    public RecyclerView.LayoutManager getGrid() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        return gridLayoutManager;
    }
}
