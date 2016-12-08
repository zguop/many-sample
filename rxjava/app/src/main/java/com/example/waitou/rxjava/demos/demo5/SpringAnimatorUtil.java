package com.example.waitou.rxjava.demos.demo5;

import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;

/**
 * Created by waitou on 16/11/3.
 * 弹簧动画
 */

public class SpringAnimatorUtil {

    public static void animateViewDirection(float from, float to, double tension, double friction, SpringListener listener) {
        SpringSystem.create().createSpring()
                .setCurrentValue(from)
                .setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, friction))
                .addListener(listener)
                .setEndValue(to);
    }
}
