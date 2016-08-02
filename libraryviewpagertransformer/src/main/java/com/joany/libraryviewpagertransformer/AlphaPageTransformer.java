package com.joany.libraryviewpagertransformer;

import android.support.v4.view.ViewPager;
import android.view.View;

public class AlphaPageTransformer implements ViewPager.PageTransformer {

    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    public AlphaPageTransformer() {
    }

    @Override
    public void transformPage(View page, float position) {
        page.setScaleX(0.999f);

        if(position < -1) {
            page.setAlpha(mMinAlpha);
        } else if(position <= 1) {
            if(position < 0) {
                float factor = mMinAlpha + (1-mMinAlpha)*(1+position);
                page.setAlpha(factor);
            } else {
                float factor = mMinAlpha + (1-mMinAlpha)*(1-position);
                page.setAlpha(factor);
            }
        } else {
            page.setAlpha(mMinAlpha);
        }
    }
}
