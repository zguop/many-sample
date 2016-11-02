package com.example.waitou.rxjava.demos.expandablerecycler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by stelian on 25/09/2016.
 */

public class DataUtils {

    private static HashMap<ListItem, List<ListItem>> mData;

    static {
        mData = new HashMap<>();

        ListItem p1 = ListItem.newParentItem(new Model("Model 1"));
        List<ListItem> p1Children = new ArrayList<>();

        ListItem p1c1 = ListItem.newChildItem(new Model("Child 1 - parent 1"));
        p1Children.add(p1c1);

        ListItem p1c2 = ListItem.newChildItem(new Model("Child 2 - parent 1"));
        p1Children.add(p1c2);

        mData.put(p1, p1Children);

        ListItem p2 = ListItem.newParentItem(new Model("Model 2"));
        List<ListItem> p2Children = new ArrayList<>();

        ListItem p2c1 = ListItem.newChildItem(new Model("Child 1 - parent 2"));
        p2Children.add(p2c1);

        ListItem p2c2 = ListItem.newChildItem(new Model("Child 2 - parent 2"));
        p2Children.add(p2c2);

        ListItem p2c3 = ListItem.newChildItem(new Model("Child 3 - parent 2"));
        p2Children.add(p2c3);

        mData.put(p2, p2Children);

        ListItem p3 = ListItem.newParentItem(new Model("Model 3"));
        List<ListItem> p3Children = new ArrayList<>();

        ListItem p3c1 = ListItem.newChildItem(new Model("Child 1 - parent 3"));
        p3Children.add(p3c1);


        mData.put(p3, p3Children);

        ListItem p4 = ListItem.newParentItem(new Model("Model 4"));
        mData.put(p4, null);

        ListItem p5 = ListItem.newParentItem(new Model("Model 5"));
        List<ListItem> p5Children = new ArrayList<>();

        ListItem p5c1 = ListItem.newChildItem(new Model("Child 1 - parent 5"));
        p5Children.add(p5c1);

        ListItem p5c2 = ListItem.newChildItem(new Model("Child 2 - parent 5"));
        p5Children.add(p5c2);

        ListItem p5c3 = ListItem.newChildItem(new Model("Child 3 - parent 5"));
        p5Children.add(p5c3);

        mData.put(p5, p5Children);

    }


    public static HashMap<ListItem, List<ListItem>> getDummyData() {
        return mData;
    }
}
