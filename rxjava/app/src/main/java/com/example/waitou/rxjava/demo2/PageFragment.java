package com.example.waitou.rxjava.demo2;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waitou.rxjava.base.BaseFragment;
import com.example.waitou.rxjava.R;

import butterknife.BindView;

/**
 * Created by waitou on 16/10/18.
 */

public class PageFragment extends BaseFragment {

    private static final String ARG_IMG_ID="image_id";
    private static final String ARG_TEXT="text";

    @BindView(R.id.img)
    RoundRectImageView mRectImageView;

    @BindView(R.id.text)
    TextView mTextView;

    @Override
    protected int initContentViewID() {
        return R.layout.fragment_page;
    }

    @Override
    protected void getBundleExtras(Bundle arguments) {
        int anInt = arguments.getInt(ARG_IMG_ID);
        String text = arguments.getString(ARG_TEXT);
        mRectImageView.setImageResource(anInt);
        mTextView.setText(text);
        mRectImageView.setOnClickListener(v -> Toast.makeText(getActivity(),text,Toast.LENGTH_SHORT).show());
    }

    public static PageFragment newInstance(int imgId, String text){
        Bundle args=new Bundle();
        args.putSerializable(ARG_IMG_ID,imgId);
        args.putSerializable(ARG_TEXT,text);
        PageFragment fragment=new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
