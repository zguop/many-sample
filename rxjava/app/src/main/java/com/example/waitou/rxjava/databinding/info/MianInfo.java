package com.example.waitou.rxjava.databinding.info;

import android.databinding.BaseObservable;

/**
 * Created by waitou on 16/12/28.
 */

public class MianInfo extends BaseObservable{

    private String simple;
    private String list;
    private String binding;
    private String expression;
    private String lambda;
    private String animation;
    private String change;


    public MianInfo(String simple,String list,String binding,String expression ,String lambda,String animation ,String change){
        this.simple = simple;
        this.list = list;
        this.binding = binding;
        this.expression = expression;
        this.lambda = lambda;
        this.animation = animation;
        this.change = change;
    }

    public String getSimple() {
        return simple;
    }

    public void setSimple(String simple) {
        this.simple = simple;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getLambda() {
        return lambda;
    }

    public void setLambda(String lambda) {
        this.lambda = lambda;
    }

    public String getAnimation() {
        return animation;
    }

    public void setAnimation(String animation) {
        this.animation = animation;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }
}
