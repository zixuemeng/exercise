package com.joany.searchanimation;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by joany on 2016/9/22.
 */
public class WaveView extends View {

    private float waveHeight = 200f;
    /**
     * 波峰、波谷
     */
    private float wavePeak = 30f;
    private int waterColor = 0xff00bfff;

    private Paint paint;
    private Path path;

    /**
     * 屏幕宽高
     */
    private float width, height;
    /**
     * 起点
     */
    private PointF start;
    /**
     * 从左至右五个点
     */
    private PointF pointF1,pointF2,pointF3,pointF4,pointF5;

    /**
     *从左至右四个控制点
     */
    private PointF contorl1,control2,control3,control4;

    private boolean hasInit = false;
    private boolean isRunning = false;

    private WaveHandler waveHandler = new WaveHandler();

    private class WaveHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            initValue();
            startAnimation();
            isRunning = true;
        }
    }
    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(waterColor);
        paint.setAntiAlias(true);
        path = new Path();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!hasInit) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                waveHandler.sendEmptyMessage(0);
            }
        }).start();

    }

    private void initValue(){
        float y = height - waveHeight;
        start = new PointF(-width, y);
        pointF1 = new PointF(-width, y);
        setValue();
    }

    private void startAnimation(){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start.x, 0);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                pointF1.x = (float) valueAnimator.getAnimatedValue();
                setValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    private void setValue(){
        float y = height -waveHeight;
        pointF2 = new PointF(pointF1.x+width/2,y);
        pointF3 = new PointF(pointF2.x + width/2,y);
        pointF4 = new PointF(pointF3.x + width/2, y);
        pointF5 = new PointF(pointF4.x + width/2,y);
        contorl1 = new PointF(pointF1.x+width/4,pointF1.y-wavePeak);
        control2 = new PointF(pointF2.x+width/4,pointF2.y + wavePeak);
        control3 = new PointF(pointF3.x+width/4,pointF3.y-wavePeak);
        control4 = new PointF(pointF4.x+width/4,pointF4.y+wavePeak);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        hasInit = true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isRunning || !hasInit){
            return;
        }
        path.reset();
        path.moveTo(pointF1.x, pointF1.y);
        path.quadTo(contorl1.x, contorl1.y, pointF2.x, pointF2.y);
        path.quadTo(control2.x, control2.y, pointF3.x, pointF3.y);
        path.quadTo(control3.x, control3.y, pointF4.x, pointF4.y);
        path.quadTo(control4.x, control4.y, pointF5.x, pointF5.y);
        path.lineTo(pointF5.x, height);
        path.lineTo(pointF1.x, height);
        path.lineTo(pointF1.x, pointF1.y);
        canvas.drawPath(path,paint);
    }

}
