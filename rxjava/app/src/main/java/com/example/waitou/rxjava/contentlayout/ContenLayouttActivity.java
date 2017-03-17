package com.example.waitou.rxjava.contentlayout;

import android.view.View;
import android.widget.Button;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.base.BaseActivity;

/**
 * Created by waitou on 17/1/5.
 */

public class ContenLayouttActivity extends BaseActivity implements View.OnClickListener{

    Button bt_showLoading;
    Button bt_showError;
    Button bt_showEmpty;
    Button bt_showContent;

    QTContentLayout contentLayout;

    @Override
    protected boolean isOverridePendingTransition() {
        return false;
    }

    @Override
    protected int initContentViewID() {
        return R.layout.activity_contentlayout;
    }


    @Override
    protected void initData() {

        bt_showLoading = (Button) findViewById(R.id.bt_showLoading);
        bt_showError = (Button) findViewById(R.id.bt_showError);
        bt_showEmpty = (Button) findViewById(R.id.bt_showEmpty);
        bt_showContent = (Button) findViewById(R.id.bt_showContent);
        contentLayout = (QTContentLayout) findViewById(R.id.contentLayout);

        bt_showLoading.setOnClickListener(this);
        bt_showEmpty.setOnClickListener(this);
        bt_showError.setOnClickListener(this);
        bt_showContent.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_showContent:
                contentLayout.showContent();
                break;

            case R.id.bt_showEmpty:
                contentLayout.showEmpty();
                break;

            case R.id.bt_showError:
                contentLayout.showError();
                break;

            case R.id.bt_showLoading:
                contentLayout.showLoading();
                break;
        }
    }
}
