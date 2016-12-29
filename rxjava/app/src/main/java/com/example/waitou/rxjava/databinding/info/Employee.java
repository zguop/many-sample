package com.example.waitou.rxjava.databinding.info;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableBoolean;

import com.android.databinding.library.baseAdapters.BR;

/**
 * Created by waitou on 16/12/29.
 */

public class Employee extends BaseObservable{

    /**
     * 如果希望数据变更后 UI 会即时刷新，就需要继承 Observable 类
     * BaseObservable 提供了 notifyChange 和 notifyPropertyChanged 两个方法来刷新 UI ，
     * 前者刷新所有的值，而后者则是刷新 BR 类中有标记的属性，而 BR 类中的标记生成需要用Bindable的标签修饰对应的 getter 方法
     *
     */

    private String lastName;
    private String firsName;

    public ObservableArrayMap<String,String> user = new ObservableArrayMap<>();

    public ObservableBoolean isFired = new ObservableBoolean();

    public Employee(String lastName,String firsName){
        this.lastName = lastName;
        this.firsName = firsName;

        user.put("hello",firsName);
        isFired.set(false);
    }

    @Bindable
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;

    }

    @Bindable
    public String getFirsName() {
        return firsName;
    }

    public void setFirsName(String firsName) {
        this.firsName = firsName;
        notifyPropertyChanged(BR.firsName);
    }


    public void setFired(boolean fired) {
        isFired.set(fired);
    }
}
