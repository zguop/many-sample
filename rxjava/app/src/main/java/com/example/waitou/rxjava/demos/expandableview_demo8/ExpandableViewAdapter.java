package com.example.waitou.rxjava.demos.expandableview_demo8;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.waitou.rxjava.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by waitou on 16/11/1.
 */

public class ExpandableViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<QueryInfo> mInfos;
    private LayoutInflater  mInflater;
    private Context         mContext;


    public ExpandableViewAdapter(Context context, List<QueryInfo> infos) {
        this.mInfos = infos;
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_expanble_recycle, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder holder1 = (ViewHolder) holder;
        QueryInfo queryInfo = mInfos.get(position);
        holder1.mExpandingList.setAdapter(queryInfo.uniques, new ExpandableView.OnBindListener<UniquesInfo>() {
            @Override
            public int addClickView() {
                return R.layout.item_expanble_querycar;
            }

            @Override
            public void onBindClickView(ExpandableView.ViewHolder clickHolder) {

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
                if (position != getItemCount() - 1 && contentPos == contentCount - 1) {
                    lineView.setVisibility(View.VISIBLE);
                } else {
                    lineView.setVisibility(View.GONE);
                }
                boolean delivered = uniquesInfo.delivered;
                if (!delivered) {
                    tvRight.setText("呵呵哒");
                    tvRight.setTextColor(ActivityCompat.getColor(mContext, R.color.orange_FF8903));
                    tvLeft.setTextColor(ActivityCompat.getColor(mContext, R.color.orange_FF8903));
                }
                tvLeft.setText(uniquesInfo.snap);
            }

            @Override
            public boolean expandableUpdataView() {
                return true;
            }
        });



        //        holder1.mExpandingList.setAdapter(R.layout.item_expandble_view, mInfos, new NestFullFlexboxLayout.OnBindDatas<QueryInfo>() {
//
//            @Override
//            public void onBind(int pos, int itemCount, QueryInfo queryInfo, NestFullFlexboxLayout.NestFullViewHolder holder) {
//                ExpandableView expandableView = holder.getView(R.id.item_expanble);
//                expandableView.setAdpater(queryInfo.uniques, new ExpandableView.OnBindDatas<UniquesInfo>() {
//                    @Override
//                    public int addClickView() {
//                        return R.layout.item_arror;
//                    }
//
//                    @Override
//                    public void onBindClickView(ExpandableView.ViewHolder clickHolder) {
//                        ImageView view = clickHolder.getView(R.id.btn_fold);
//                        expandableView.setArrorAnimationView(view);
//
//                    }
//
//                    @Override
//                    public int addChildView() {
//                        return R.layout.item_car_type;
//                    }
//
//                    @Override
//                    public void onBindChildView(int ChildPos, int ChildCount, UniquesInfo uniquesInfo, ExpandableView.ViewHolder childHolder) {
//                        TextView tvLeft = childHolder.getView(R.id.tv_name);
//
//                        tvLeft.setText(uniquesInfo.snap);
//                    }
//
//                    @Override
//                    public boolean expandableUpdataView() {
//                        return true;
//                    }
//                });
//
//            }
//
//            @Override
//            public void setLayoutParams(int pos, NestFullFlexboxLayout.NestFullViewHolder holder) {
//
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mInfos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.flex_label)
        ExpandableView mExpandingList;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
