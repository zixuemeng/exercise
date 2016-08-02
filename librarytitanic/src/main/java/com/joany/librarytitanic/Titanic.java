package com.joany.librarytitanic;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

/**
 * Created by joany on 2016/7/26.
 */
public class Titanic {

    private AnimatorSet animatorSet;

    public void start(final TitanicTextView textView) {
        final Runnable animate = new Runnable() {
            @Override
            public void run() {
                ObjectAnimator maskXAnimator = ObjectAnimator.ofFloat(textView,"maskX",0,200);
                maskXAnimator.setRepeatCount(ValueAnimator.INFINITE);
                maskXAnimator.setDuration(3000);
                maskXAnimator.setStartDelay(0);
                maskXAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        textView.setMaskX(((float)valueAnimator.getAnimatedValue()));
                    }
                });

                int h = textView.getHeight();

                ObjectAnimator maskYAnimator = ObjectAnimator.ofFloat(textView,"maskY",h/2,-h/2);
                maskYAnimator.setRepeatCount(ValueAnimator.INFINITE);
                maskYAnimator.setRepeatMode(ValueAnimator.REVERSE);
                maskYAnimator.setDuration(3000);
                maskYAnimator.setStartDelay(0);
                maskYAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        textView.setMaskY((float)valueAnimator.getAnimatedValue());
                    }
                });

                animatorSet = new AnimatorSet();
                animatorSet.playTogether(maskXAnimator,maskYAnimator);
                animatorSet.setInterpolator(new LinearInterpolator());
                animatorSet.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        textView.postInvalidate();
                        animatorSet = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

                animatorSet.start();
            }
        };

        textView.setAnimationSetupCallback(new TitanicTextView.AnimationSetupCallback() {
            @Override
            public void onSetupAnimation(TitanicTextView titanicTextView) {
                animate.run();
            }
        });
        animate.run();
    }
}
