package com.example.waitou.rxjava.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.databinding.info.MianInfo;
import com.example.waitou.rxjava.databinding.persenter.MainPresenter;

/**
 * Created by waitou on 16/12/28.
 */

public class DataActivity extends AppCompatActivity {

    private ActivityDataBinding mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_data);
        mDataBinding.setPresenter(new MainPresenter(this));
        mDataBinding.setMainInfo(new MianInfo("Simple" ,"List" ,"Two-way binding","Expression","Lambda","animation","change"));


    }
}
