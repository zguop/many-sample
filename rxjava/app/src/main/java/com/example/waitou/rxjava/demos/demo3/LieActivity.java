package com.example.waitou.rxjava.demos.demo3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.waitou.rxjava.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by waitou on 16/9/19.
 */
public class LieActivity extends AppCompatActivity {

    private ListView mListView;

    private String s[] = {"1111", "2222", "3333", "4444", "5555", "6666","7777"};
    List<LieBean> mLieBeanList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lie);

            for (int i = 0; i < s.length; i++) {
                LieBean lieBean = new LieBean();
                lieBean.name = s[i];
                mLieBeanList.add(lieBean);
            }
        mListView = (ListView) findViewById(R.id.list_item);
        mListView.setAdapter(new ListAdpater());
    }

    class ListAdpater extends BaseAdapter {

        private boolean mOpen =false;

        @Override
        public int getCount() {
            if (mLieBeanList != null) {
                if (mLieBeanList.size() <= 4) {
                    return mLieBeanList.size();
                } else {
                    return mOpen ? mLieBeanList.size() : 2;
                }
            }else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return mLieBeanList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LieBean  item = (LieBean) getItem(position);
            ViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(LieActivity.this).inflate(R.layout.item_cartype, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            if ((mOpen && position == getCount() - 1) || (!mOpen && position == 1)) {
                vh.btnFold.setVisibility(View.VISIBLE);
            } else {
                vh.btnFold.setVisibility(View.GONE);
            }

            vh.btnFold.setOnClickListener(view -> {
                mOpen = !mOpen;
                notifyDataSetChanged();
            });

            vh.tvName.setText(item.name);

            return convertView;
        }

         class ViewHolder {
            @BindView(R.id.tv_name)
            TextView  tvName;
            @BindView(R.id.btn_fold)
            ImageView btnFold;

            public ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
