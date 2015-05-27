package com.seable.potato.util.dialog;

import android.view.View;

import com.nineoldandroids.animation.ObjectAnimator;

/**
 * @author 王维玉copy
 * @ClassName: SlideRight
 * @Description: dialog相关
 * @date 2015-01-17 15:32
 */
public class SlideRight extends BaseEffects {

    @Override
    protected void setupAnimation(View view) {
        getAnimatorSet().playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 300, 0).setDuration(mDuration),
                ObjectAnimator.ofFloat(view, "alpha", 0, 1).setDuration(mDuration * 3 / 2)

        );
    }
}
