package com.joany.launcheranimationview.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.joany.launcheranimationview.R;

/**
 * Created by jiqiong.jq on 2016/10/28.
 */
public class LauncherAnimationView extends RelativeLayout{
    private int mHeight;
    private int mWidth;
    private boolean mIsStart;

    public LauncherAnimationView(Context context) {
        this(context, null);
    }

    public LauncherAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LauncherAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    ImageView mBlue,mGray,mRed,mYellow;

    private void init(){
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_HORIZONTAL,TRUE);
        lp.addRule(CENTER_VERTICAL,TRUE);
        lp.setMargins(0, 0, 0, 0);

        mBlue = new ImageView(getContext());
        mBlue.setLayoutParams(lp);
        mBlue.setImageResource(R.drawable.blue_circle);
        addView(mBlue);

        mGray = new ImageView(getContext());
        mGray.setLayoutParams(lp);
        mGray.setImageResource(R.drawable.gray_circle);
        addView(mGray);

        mRed = new ImageView(getContext());
        mRed.setLayoutParams(lp);
        mRed.setImageResource(R.drawable.red_circle);
        addView(mRed);

        mYellow = new ImageView(getContext());
        mYellow.setLayoutParams(lp);
        mYellow.setImageResource(R.drawable.yellow_circle);
        addView(mYellow);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus && !mIsStart) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    start();
                    mIsStart = true;
                }
            },500);
        }
    }

    private void start(){
        ViewPath bluePath1 = new ViewPath();
        bluePath1.moveTo(0, 0);
        bluePath1.lineTo(mWidth / 5 - mWidth / 2, 0);
        ViewPath bluePath2 = new ViewPath();
        bluePath2.moveTo(mWidth / 5 - mWidth / 2, 0);
        bluePath2.cubicTo(-700,-mHeight/2,mWidth/3*2,-mHeight/3*2,0,0);
        setAnimation(mBlue, bluePath1, bluePath2);

        ViewPath grayPath1 = new ViewPath();
        grayPath1.moveTo(0, 0);
        grayPath1.lineTo(mWidth / 5 * 2 - mWidth / 2, 0);
        ViewPath grayPath2 = new ViewPath();
        grayPath2.moveTo(mWidth / 5 * 2- mWidth / 2, 0);
        grayPath2.cubicTo(-300, -mHeight / 2, mWidth, -mHeight / 9 * 5, 0, 0);
        setAnimation(mGray, grayPath1, grayPath2);

        ViewPath redPath1 = new ViewPath();
        redPath1.moveTo(0, 0);
        redPath1.lineTo(mWidth / 5 * 3 - mWidth / 2, 0);
        ViewPath redPath2 = new ViewPath();
        redPath2.moveTo(mWidth / 5 * 3- mWidth / 2, 0);
        redPath2.cubicTo(300, mHeight,-mWidth, -mHeight  / 9 * 5, 0, 0);
        setAnimation(mRed, redPath1, redPath2);

        ViewPath yellowPath1 = new ViewPath();
        yellowPath1.moveTo(0, 0);
        yellowPath1.lineTo(mWidth / 5 *4 - mWidth / 2, 0);
        ViewPath yellowPath2 = new ViewPath();
        yellowPath2.moveTo(mWidth / 5  *4- mWidth / 2, 0);
        yellowPath2.cubicTo(700, mHeight / 3 * 2, -mWidth / 2, mHeight / 2, 0, 0);
        setAnimation(mYellow, yellowPath1, yellowPath2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLogo();
            }
        },1800);
    }

    private void setAnimation(ImageView view,ViewPath path1,ViewPath path2) {
        ObjectAnimator anim1 = ObjectAnimator.ofObject(new ViewObject(view),"location",
                new ViewPathEvaluator(),path1.getPoints().toArray());
        anim1.setInterpolator(new AccelerateDecelerateInterpolator());
        anim1.setDuration(500);

        ObjectAnimator anim2 = ObjectAnimator.ofObject(new ViewObject(view),"location",
                new ViewPathEvaluator(),path2.getPoints().toArray());
        anim2.setInterpolator(new AccelerateDecelerateInterpolator());
        anim2.setDuration(1500);

        addAnimation(view, anim1, anim2);
    }

    private void addAnimation(final ImageView view,ObjectAnimator anim1,ObjectAnimator anim2) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, View.ALPHA,1f,0.5f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view,View.SCALE_X,getScale(view),1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view,View.SCALE_Y,getScale(view),1.0f);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(1500);
        animatorSet.playTogether(alpha, scaleX, scaleY, anim2);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                removeView(view);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playSequentially(anim1,animatorSet);
        animatorSet1.start();
    }

    private float getScale(ImageView view) {
        if(view == mBlue) {
            return 2.0f;
        }
        if(view == mGray) {
            return 1.5f;
        }
        if(view == mRed) {
            return 3.0f;
        }
        if(view == mYellow) {
            return 2.5f;
        }
        return 2.0f;
    }

    private class ViewObject{
        private ImageView view;

        public ViewObject(ImageView view) {
            this.view = view;
        }

        public void setLocation(ViewPathPoint viewPathPoint) {
            view.setTranslationX(viewPathPoint.endX);
            view.setTranslationY(viewPathPoint.endY);
        }
    }

    private void showLogo(){
        View view = View.inflate(getContext(),R.layout.logo_view,this);
        View launcher = view.findViewById(R.id.launcher_img);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(launcher,View.ALPHA,0f,1f);
        alpha.setDuration(500);
        alpha.start();
    }
}
