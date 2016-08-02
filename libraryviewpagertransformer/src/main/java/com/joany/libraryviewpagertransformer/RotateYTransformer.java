package com.joany.libraryviewpagertransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

public class RotateYTransformer implements ViewPager.PageTransformer
{
    private static final float DEFAULT_MAX_ROTATE = 35f;
    private static final float DEFAULT_CENTER = 0.5f;
    private float mMaxRotate = DEFAULT_MAX_ROTATE;

    public RotateYTransformer() {
    }


    @Override
    public void transformPage(View page, float position) {
        page.setPivotY(page.getHeight()/2);

        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setRotationY(-1 * mMaxRotate);
            page.setPivotX(page.getWidth());
        } else if (position <= 1) { // [-1,1]
            // Modify the default slide transition to shrink the page as well

            page.setRotationY(position * mMaxRotate);

            if (position < 0) {//[0,-1]
                page.setPivotX(page.getWidth() * (DEFAULT_CENTER + DEFAULT_CENTER * (-position)));
                page.setPivotX(page.getWidth());
            } else {//[1,0]
                page.setPivotX(page.getWidth() * DEFAULT_CENTER * (1 - position));
                page.setPivotX(0);
            }
            // Scale the page down (between MIN_SCALE and 1)
        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setRotationY(1 * mMaxRotate);
            page.setPivotX(0);
        }
    }
}
