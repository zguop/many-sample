package com.example.waitou.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.waitou.rxjava.base.BaseFragment;

/**
 * Created by waitou on 16/12/13.
 */

public class AbsFragment extends BaseFragment {
    @Override
    protected int initContentViewID() {
        return R.layout.message_layout;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i("aa" , " onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i("aa" , " AbsFragment");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
