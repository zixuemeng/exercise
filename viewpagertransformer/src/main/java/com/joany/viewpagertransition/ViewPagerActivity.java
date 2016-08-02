package com.joany.viewpagertransition;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.joany.libraryviewpagertransformer.AlphaPageTransformer;

public class ViewPagerActivity extends Activity {

    private ViewPager mViewPager;

    int[] imgResId = {R.mipmap.ic_1,R.mipmap.ic_2,R.mipmap.ic_3,R.mipmap.ic_4};

    private ImageView[] mImageView = new ImageView[imgResId.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mViewPager.setPageMargin(20);
        mViewPager.setOffscreenPageLimit(1);

        for(int i =0; i< imgResId.length; i++) {
            ImageView view = new ImageView(ViewPagerActivity.this);
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            view.setImageResource(imgResId[i]);
            mImageView[i] = view;
        }

        mViewPager.setAdapter(new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mImageView[position%imgResId.length]);
                return mImageView[position%imgResId.length];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mImageView[position%imgResId.length]);
            }

            @Override
            public int getCount() {//循环播放
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        mViewPager.setPageTransformer(true, new AlphaPageTransformer());
        //mViewPager.setPageTransformer(true, new RotateDownPageTransformer());
        //mViewPager.setPageTransformer(true, new RotateYTransformer());
        //mViewPager.setPageTransformer(true, new ScaleInTransformer());
    }
}
