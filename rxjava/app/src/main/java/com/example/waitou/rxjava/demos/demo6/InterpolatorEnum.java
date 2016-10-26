package com.example.waitou.rxjava.demos.demo6;

import android.animation.TimeInterpolator;
import android.support.annotation.IntRange;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

/**
 * Created by waitou on 16/10/20.
 *
 */

public enum InterpolatorEnum {

     accelerate_decelerate_interpolator(new AccelerateDecelerateInterpolator()),
     accelerate_interpolator(new AccelerateInterpolator()),
     anticipate_interpolator(new AnticipateInterpolator()),
     anticipate_overshoot_interpolator(new AnticipateOvershootInterpolator()),
     bounce_interpolator(new BounceInterpolator()),
     decelerate_interpolator(new DecelerateInterpolator()),
     fast_out_linear_in_interpolator(new FastOutLinearInInterpolator()),
     fast_out_slow_in_interpolator(new LinearInterpolator()),
     linear_interpolator(new LinearOutSlowInInterpolator()),
     linear_out_slow_in_interpolator(new OvershootInterpolator()),
     overshoot_interpolator (new LinearInterpolator());

    private  TimeInterpolator interpolator;
    InterpolatorEnum(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }

    public TimeInterpolator getInterpolator() {
        return interpolator;
    }


    public static InterpolatorEnum createInterpolator(@IntRange(from = 0, to = 10) final int interpolatorType) {
        switch (interpolatorType) {
            case 0:
                return accelerate_decelerate_interpolator;
            case 1:
                return accelerate_interpolator;
            case 2:
                return anticipate_interpolator;
            case 3:
                return anticipate_overshoot_interpolator;
            case 4:
                return bounce_interpolator;
            case 5:
                return decelerate_interpolator;
            case 6:
                return fast_out_linear_in_interpolator;
            case 7:
                return fast_out_slow_in_interpolator;
            case 8:
                return linear_interpolator;
            case 9:
                return linear_out_slow_in_interpolator;
            case 10:
                return overshoot_interpolator;
            default:
                return linear_interpolator;
        }
    }
}
