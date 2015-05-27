package com.seable.potato.util.dialog;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * @author 王维玉copy
 * @ClassName: Shake
 * @Description: dialog相关
 * @date 2015-01-17 15:32
 */
public class Shake extends BaseEffects {

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, .10f, -25, .26f, 25, .42f, -25, .58f, 25, .74f, -25, .90f, 1, 0).setDuration(mDuration)

        );
    }
}
