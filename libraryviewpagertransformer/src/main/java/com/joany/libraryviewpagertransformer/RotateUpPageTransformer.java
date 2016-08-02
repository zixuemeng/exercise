package com.joany.libraryviewpagertransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

public class RotateUpPageTransformer implements ViewPager.PageTransformer {
    private static final float DEFAULT_MAX_ROTATE = 15.0f;
    private static final float DEFAULT_CENTER = 0.5f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;

    public RotateUpPageTransformer() {
    }

    @Override
    public void transformPage(View page, float position) {
        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setRotation(mMaxRotate);
            page.setPivotX(page.getWidth());
            page.setPivotY(0);

        } else if (position <= 1) // a页滑动至b页 ； a页从 0.0 ~ -1 ；b页从1 ~ 0.0
        { // [-1,1]
            // Modify the default slide transition to shrink the page as well
            if (position < 0) {//[0，-1]
                page.setPivotX(page.getWidth() * DEFAULT_CENTER * (1 - position));
                page.setPivotY(0);
                page.setRotation(-mMaxRotate * position);
            } else {//[1,0]
                page.setPivotX(page.getWidth() * DEFAULT_CENTER * (1 - position));
                page.setPivotY(0);
                page.setRotation(-mMaxRotate * position);
            }
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
//            ViewHelper.setRotation(view, ROT_MAX);
            page.setRotation(-mMaxRotate);
            page.setPivotX(0);
            page.setPivotY(0);
        }
    }
}