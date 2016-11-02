package com.example.waitou.rxjava.demos.expandablerecycler_demo7;

/**
 * Created by stelian on 25/09/2016.
 */

public class ListItem {

    public static final int TYPE_PARENT = 30;
    public static final int TYPE_CHILD = 31;

    private int   type;
    private Model mModel;

    public static ListItem newParentItem(Model model) {
        return new ListItem(TYPE_PARENT, model);
    }

    public static ListItem newChildItem(Model model) {
        return new ListItem(TYPE_CHILD, model);
    }

    private ListItem(int type, Model model) {
        this.type = type;
        mModel = model;
    }

    public int getType() {
        return type;
    }

    public Model getModel() {
        return mModel;
    }
}
