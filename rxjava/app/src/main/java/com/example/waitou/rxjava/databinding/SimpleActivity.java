package com.example.waitou.rxjava.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.databinding.info.Employee;
import com.example.waitou.rxjava.databinding.persenter.SimplePresenter;

/**
 * Created by waitou on 16/12/28.
 */

public class SimpleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySimpleBinding viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple);
        Employee employee = new Employee("abc" , "cba");
        SimplePresenter presenter  = new SimplePresenter(this, employee);
        viewDataBinding.setEmployee(employee);
        viewDataBinding.setSimpePresenter(presenter);
        viewDataBinding.viewStub.getViewStub().inflate();
    }
}
