package com.example.waitou.rxjava.databinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.example.waitou.rxjava.R;
import com.example.waitou.rxjava.databinding.adapter.EmployeeAdapter;

/**
 * Created by waitou on 16/12/28.
 */

public class ListDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.waitou.rxjava.databinding.ActivityListBinding listBinding = DataBindingUtil.setContentView(this, R.layout.activity_list);
        listBinding.setPersenter(new persenter());
        listBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        EmployeeAdapter adapter = new EmployeeAdapter();
        listBinding.recyclerview.setAdapter(adapter);


    }


    private class persenter {

    }
}
