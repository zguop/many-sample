package com.example.waitou.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by waitou on 16/9/10.
 */
public abstract class LazyLoadFragment extends Fragment {

    /**
     * 控件是否初始化完毕
     */
    private boolean isViewCreated ;

    /**
     * 数据是否已经加载完毕
     */
    private boolean isLoadDataCompleted;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayout(), container, false);
        initViews(inflate);
        isViewCreated = true;
        return inflate;

    }

    protected abstract int getLayout();
    protected abstract void initViews(View inflate);


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser && isViewCreated && !isLoadDataCompleted){
            loadData();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(getUserVisibleHint()){
            loadData();
        }
    }

    public void loadData() {
        isLoadDataCompleted = true;

    }


}
