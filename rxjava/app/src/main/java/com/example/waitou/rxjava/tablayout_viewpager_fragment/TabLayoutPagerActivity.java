package com.example.waitou.rxjava.tablayout_viewpager_fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.example.waitou.rxjava.R;

/**
 * Created by waitou on 16/9/10.
 */
public class TabLayoutPagerActivity extends FragmentActivity {

//    http://www.jianshu.com/p/ce429d72228f
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_pager);

    }
}
