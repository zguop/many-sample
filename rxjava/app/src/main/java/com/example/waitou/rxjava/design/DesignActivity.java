package com.example.waitou.rxjava.design;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;

/**
 * Created by waitou on 16/12/25.
 */

public class DesignActivity extends BaseActivity {
    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_design;
    }
}
