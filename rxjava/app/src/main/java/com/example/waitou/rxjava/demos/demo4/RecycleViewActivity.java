package com.example.waitou.rxjava.demos.demo4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.waitou.rxjava.R;

/**
 * Created by waitou on 16/9/18.
 */

public class RecycleViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_recyclerview);

        // 实例化控件
        final RecyclerView rv = (RecyclerView) findViewById(R.id.rv);

        // 设置启动列表的修改动画效果(默认为关闭状态)
       // rv.getItemAnimator().setSupportsChangeAnimations(true);
        // 设置动画时长
        rv.getItemAnimator().setChangeDuration(300);
        rv.getItemAnimator().setMoveDuration(300);

        // 实现RecyclerView实现竖向列表展示模式
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);

        // 实例化数据适配器并绑定在控件上
        final MainAdapter adapter = new MainAdapter();
        rv.setAdapter(adapter);

    }
}
