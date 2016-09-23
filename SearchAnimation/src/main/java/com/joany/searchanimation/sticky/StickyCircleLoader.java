package com.joany.searchanimation.sticky;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joany on 2016/9/22.
 */
public class StickyCircleLoader extends View {

    private int width;
    private int height;

    /**
     * 外围圆弧轨道
     */
    private Circle bigCircle = new Circle();
    private float bigCircleRadius = 50;

    /**
     * 在圆弧轨道上分布的静态圆
     */
    private Circle staticCircle;
    private float staticCircleRaduis = bigCircleRadius/5;
    private int staticCircleCount = 6;
    private float degree = 360.0f / staticCircleCount;
    private List<Circle> staticCircleList = new ArrayList<Circle>();

    /**
     * 静态圆半径变化的最大比例
     */
    private float maxStaticCircleRadiusScale = 0.4f;
    private float maxStickyLength = staticCircleRaduis * 3;

    /**
     * 动态运动的圆
     */
    private Circle dynamicCircle = new Circle();

    private Paint paint = new Paint();
    private int color = 0xff00bfff;

    public StickyCircleLoader(Context context) {
        this(context, null);
    }

    public StickyCircleLoader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyCircleLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        paint.setAntiAlias(true);

        width = height = (int) (2 * (bigCircleRadius +
                staticCircleRaduis * (1 + maxStaticCircleRadiusScale)));

        bigCircle.centerX = width / 2;
        bigCircle.centerY = height / 2;
        bigCircle.radius = bigCircleRadius;

        //将动态圆起始坐标设置为第一个静态圆坐标
        dynamicCircle.centerX = (float) (bigCircle.centerX
                + bigCircleRadius * Math.cos(Math.toRadians(degree)));
        dynamicCircle.centerY = (float) (bigCircle.centerY
                + bigCircleRadius * Math.sin(Math.toRadians(degree)));
        dynamicCircle.radius = staticCircleRaduis * 1.3f;

        for(int i = 0; i < staticCircleCount; i++) {
            staticCircle = new Circle();
            //零度方向为x轴方向
            staticCircle.centerX = (float) (bigCircle.centerX
                    + bigCircleRadius * Math.cos(Math.toRadians(degree * i)));
            staticCircle.centerY = (float) (bigCircle.centerY
                    + bigCircleRadius * Math.sin(Math.toRadians(degree * i)));
            staticCircle.radius = staticCircleRaduis;
            staticCircleList.add(staticCircle);
        }

        startAnim();
    }

    private void startAnim(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(-90,270);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float degree = (float) valueAnimator.getAnimatedValue();
                dynamicCircle.centerX = (float) (bigCircle.centerX
                        + bigCircleRadius * Math.cos(Math.toRadians(degree)));
                dynamicCircle.centerY = (float) (bigCircle.centerY
                        + bigCircleRadius * Math.sin(Math.toRadians(degree)));
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(dynamicCircle.centerX,dynamicCircle.centerY,dynamicCircle.radius,paint);

        for(int i = 0 ; i < staticCircleCount; i ++) {
            staticCircle = staticCircleList.get(i);
            if(doSticky(i)) {
                canvas.drawCircle(staticCircle.centerX,staticCircle.centerY,staticCircleRaduis,paint);
                Path path = StickyUtils.drawStickyCircle(staticCircle.centerX,staticCircle.centerY,
                        staticCircleRaduis,45,dynamicCircle.centerX,dynamicCircle.centerY,
                        dynamicCircle.radius,45);
                canvas.drawPath(path,paint);
            } else {
                canvas.drawCircle(staticCircle.centerX,staticCircle.centerY,staticCircle.radius,paint);
            }
        }
    }

    /**
     * 判断哪个静态圆可以做粘性连接
     * @param position
     * @return
     */
    private  boolean doSticky(int position) {
        staticCircle = staticCircleList.get(position);

        float distance = (float) Math.sqrt(Math.pow(staticCircle.centerX - dynamicCircle.centerX, 2)
                + Math.pow(staticCircle.centerY - dynamicCircle.centerY,2));
        if(distance < maxStickyLength) {
            staticCircleRaduis = staticCircle.radius * (1 +
                    maxStaticCircleRadiusScale * (1 - distance / maxStickyLength));
            return true;
        } else {
            return false;
        }

    }
    /**
     * 圆类
     */
    private class Circle{
        float centerX;
        float centerY;
        float radius;
    }
}
