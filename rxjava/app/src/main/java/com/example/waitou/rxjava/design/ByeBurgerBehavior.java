package com.example.waitou.rxjava.design;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

/**
 * Created by waitou on 17/1/20.
 */

public class ByeBurgerBehavior extends CoordinatorLayout.Behavior<View> {

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }
}
