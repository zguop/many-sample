package com.example.waitou.rxjava.demos.demo6;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.waitou.rxjava.demos.demo6.reference.ExpandableLinearLayout;
import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;
import com.example.waitou.rxjava.demos.demo3.LieBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by waitou on 16/10/20.
 */

public class ExpandableActivity extends BaseActivity {

    private String s[]  = {"1111", "2222", "3333", "4444"};


    @BindView(R.id.layout)
    ExpandableLinearLayout mExpandableLinearLayout;

    @BindView(R.id.layout3)
    LinearLayout mLayout;

    @BindView(R.id.layout2)
    NestFullAnimationLayout mFullFlexboxLayout;

    @BindView(R.id.layout4)
    NestFullAnimationLayout mFullAnimationLayout;

    List<LieBean> mList = new ArrayList<>();

    @BindView(R.id.btn)
    Button mBtn;

    private boolean is;


    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_expandable;
    }


    @Override
    protected void initData() {
        mFullFlexboxLayout.setAdapter(R.layout.item_lie1, mList, new NestFullAnimationLayout.OnBindDatas<LieBean>() {
            @Override
            public void onBind(int pos, int itemCount, LieBean lieBean, NestFullAnimationLayout.NestFullViewHolder holder) {
                TextView view = holder.getView(R.id.name);
                view.setText(lieBean.name);
            }

            @Override
            public void setLayoutParams(int pos, NestFullAnimationLayout.NestFullViewHolder holder) {
            }

            @Override
            public boolean addViewUpState() {
                return true;
            }

            @Override
            public int onMoveChildView(List<Integer> size, int closePositionSize) {
                if (closePositionSize == 0 && size.size() > 4) {
                    mFullFlexboxLayout.moveChild(1, 0, null);
                    mBtn.setVisibility(View.VISIBLE);
                    mBtn.setOnClickListener(v -> {
                        if (is) {
                            mFullFlexboxLayout.moveChild(mList.size() - 1, 1);
                            mBtn.setText("展开");
                        } else {
                            mFullFlexboxLayout.moveChild(1, mList.size() - 1);
                            mBtn.setText("收起");
                        }
                        is = !is;
                    });
                }
                if (size.size() <= 4) {
                    mFullFlexboxLayout.moveChild(mList.size() - 1, 0, null);
                    mBtn.setVisibility(View.GONE);
                    return 0;
                }
                return size.size() > 4 ? size.get(1) : 0;
            }
        });


        List<LieBean> li = new ArrayList<>();
        for (int i = 0; i < s.length; i++) {
            LieBean liebern = new LieBean();
            liebern.name = s[i];
            List<LieBean> lie = new ArrayList<>();
            for (int j = 0; j < s.length; j++) {
                LieBean liebern2 = new LieBean();
                liebern2.name = s[j];
                lie.add(liebern2);
            }
            liebern.mBeanList = lie;
            li.add(liebern);
        }

        mLayout.removeAllViews();
        for (int i = 0; i < li.size(); i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.item_lie3, null);
            TextView title = (TextView) inflate.findViewById(R.id.title);
            NestFullAnimationLayout nestFull = (NestFullAnimationLayout) inflate.findViewById(R.id.layout4);
            title.setText(li.get(i).name);
            title.setOnClickListener(v -> nestFull.toggle());
            nestFull.setAdapter(R.layout.item_lie1, li.get(i).mBeanList, new NestFullAnimationLayout.OnSimpBindDatas<LieBean>() {
                @Override
                public void onBind(int pos, int itemCount, LieBean lieBean, NestFullAnimationLayout.NestFullViewHolder holder) {
                    TextView tv = holder.getView(R.id.name);
                    tv.setText(lieBean.name);
                }
            });
            mLayout.addView(inflate);
        }

        mFullAnimationLayout.setAdapter(R.layout.item_lie3, li, new NestFullAnimationLayout.OnSimpBindDatas<LieBean>() {
            @Override
            public void onBind(int pos, int itemCount, LieBean lieBeen, NestFullAnimationLayout.NestFullViewHolder holder) {
                NestFullAnimationLayout nestFull = holder.getView(R.id.layout4);
                TextView title = holder.getView(R.id.title);
                title.setText(lieBeen.name);
                title.setOnClickListener(v -> nestFull.toggle());
                nestFull.setAdapter(R.layout.item_lie1, lieBeen.mBeanList, new NestFullAnimationLayout.OnSimpBindDatas<LieBean>() {
                    @Override
                    public void onBind(int pos, int itemCount, LieBean lieBean, NestFullAnimationLayout.NestFullViewHolder holder) {
                        TextView tv = holder.getView(R.id.name);
                        tv.setText(lieBean.name);
                    }
                });
            }
        });

    }

    int i = 0;
    @OnClick(R.id.tv_click)
    public void onClick() {
        LieBean bean = new LieBean();
        bean.name = s[i++ % s.length];
        mList.add(bean);
        initData();
    }

    @OnClick(R.id.tv_click1)
    public void onClick1() {
       mList.remove(mList.size() - 1);
        initData();
    }

    @OnClick(R.id.tv_click2)
    public void onClick2() {

    }

    @OnClick(R.id.tv_click3)
    public void onClick3() {

    }

}
