package com.example.waitou.rxjava.demos.expandableview_demo8;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by waitou on 16/10/28.
 */

public class ExpandableViewActivity extends BaseActivity {

    private String[] modelName  = {"ALFA", "V8","CM", "DPPPP"};
    private String[] uniques = {"444","奔驰2015款 改款 E260L运动型1白很长的话就另起一行（白／白）5辆","444","444","444"};

    @BindView(R.id.flex_label)
    NestFullFlexboxLayout mNestFullFlexboxLayout;

    @BindView(R.id.expanding_list_main)
    ExpandingList mExpandingList;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    private List<QueryInfo> mInfos = new ArrayList<>();

    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_exp_view;
    }

    @OnClick(R.id.btn1)
    public void btn1(){
        UniquesInfo uniquesInfo =  new UniquesInfo();
        uniquesInfo.snap = "哒哒哒";
        mInfos.get(0).uniques.add(uniquesInfo);
        setView();
    }

    @OnClick(R.id.btn2)
    public void btn2(){
        mInfos.get(0).uniques.remove( mInfos.get(0).uniques.size() - 1);
        setView();
    }


    @Override
    protected void initData() {
        for (int i = 0; i < 1; i++) {
            QueryInfo queryInfo = new QueryInfo();
            queryInfo.finished = false;
            queryInfo.name = modelName[i];
            queryInfo.uniques = new ArrayList<>();

            for (int j = 0; j < uniques.length; j++) {
                UniquesInfo uniquesInfo = new UniquesInfo();
                uniquesInfo.snap = uniques[j];
                uniquesInfo.delivered = false;
                queryInfo.uniques.add(uniquesInfo);
            }
            mInfos.add(queryInfo);
        }

        setView();

//       mExpandingList.getBoxLayout().setAdapter(R.layout.item_expandble_view, mInfos, new NestFullFlexboxLayout.OnBindDatas<QueryInfo>() {
//
//            @Override
//            public void onBind(int pos, int itemCount, QueryInfo queryInfo, NestFullFlexboxLayout.NestFullViewHolder holder) {
//                ExpandableView expandableView = holder.getView(R.id.item_expanble);
//                expandableView.setAdpater(queryInfo.uniques, new ExpandableView.OnBindDatas() {
//                    @Override
//                    public int addClickView() {
//                        return R.layout.item_expanble_querycar;
//                    }
//
//                    @Override
//                    public void onBindClickView(ExpandableView.ViewHolder clickHolder) {
//                        ImageView view = clickHolder.getView(R.id.iv_arrow);
//                        expandableView.setArrorAnimationView(view);
//                    }
//
//                    @Override
//                    public int addChildView() {
//                        return R.layout.item_expanble_caruniques;
//                    }
//
//                    @Override
//                    public void onBindChildView(int pos, int itemCount, Object o, ExpandableView.ViewHolder holder) {
//
//                    }
//
//                    @Override
//                    public boolean expandableUpdataView() {
//                        return false;
//                    }
//                });
//            }
//
//            @Override
//            public void setLayoutParams(int pos, NestFullFlexboxLayout.NestFullViewHolder holder) {
//
//            }
//        });


        mRecyclerView.setLayoutManager(getLinearLayoutManager());
        mRecyclerView.setAdapter(new ExpandableViewAdapter(this,mInfos));

    }

    private void setView() {
        mNestFullFlexboxLayout.setAdapter(R.layout.item_expandble_view, mInfos, new NestFullFlexboxLayout.OnBindDatas<QueryInfo>() {

            @Override
            public void onBind(int pos, int itemCount, QueryInfo queryInfo, NestFullFlexboxLayout.NestFullViewHolder holder) {
                ExpandableView expandableView = holder.getView(R.id.item_expanble);
                expandableView.setAdapter(queryInfo.uniques, new ExpandableView.OnBindListener<UniquesInfo>() {
                    @Override
                    public int addClickView() {
                        return R.layout.item_arror;
                    }

                    @Override
                    public void onBindClickView(ExpandableView.ViewHolder clickHolder) {
                        ImageView view = clickHolder.getView(R.id.btn_fold);
                        expandableView.setArrorAnimationView(view);
                    }

                    @Override
                    public int addChildView() {
                        return R.layout.item_expanble_caruniques;
                    }

                    @Override
                    public void onBindChildView(int contentPos, int contentCount, UniquesInfo uniquesInfo, ExpandableView.ViewHolder holder) {
                        TextView tvRight = holder.getView(R.id.tv_right);
                        TextView tvLeft = holder.getView(R.id.tv_left);
                        View lineView = holder.getView(R.id.line_view);

                        if(contentCount <= 4){
                            expandableView.setClickableVisibility(View.GONE);
                            expandableView.setKeepChild(contentCount);
                        }else {
                            expandableView.setClickableVisibility(View.VISIBLE);
                            expandableView.setKeepChild(2);
                        }
                        tvLeft.setText(uniquesInfo.snap);
                    }

                    @Override
                    public boolean expandableUpdataView() {
                        return true;
                    }
                });


            }

            @Override
            public void setLayoutParams(int pos, NestFullFlexboxLayout.NestFullViewHolder holder) {

            }
        });
    }
}
