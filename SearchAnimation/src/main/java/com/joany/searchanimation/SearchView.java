package com.joany.searchanimation;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by joany on 2016/9/21.
 */
public class SearchView extends View{
    private Paint paint;
    private int width;
    private int height;
    //放大镜
    private Path path_search;
    //外部圆环
    private Path path_circle;

    private PathMeasure pathMeasure;

    private ValueAnimator startingAnimator;
    private ValueAnimator searchingAnimator;
    private ValueAnimator endingAnimator;

    //动画进度
    private float animatorValue;

    //动效过程监听
    private ValueAnimator.AnimatorUpdateListener animatorUpdateListener;
    private Animator.AnimatorListener animatorListener;

    //用于控制动画状态转换
    private Handler animatorHandler;

    private boolean isEnd = false;

    private int count = 0;
    private int defaultDuration = 3000;

    enum State{
        NONE,
        STARTING,
        SEARCHING,
        ENDING
    }

    private State currentState = State.NONE;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();

        currentState = State.STARTING;
        startingAnimator.start();
    }

    private void init(){
        //initPaint
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(15);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);

        //initPath
        path_search = new Path();
        path_circle = new Path();

        pathMeasure = new PathMeasure();

        RectF rectF1 = new RectF(-30,-30,30,30);
        path_search.addArc(rectF1,45,359.9f);

        RectF rectF2 = new RectF(-100,-100,100,100);
        path_circle.addArc(rectF2, 45, -359.9f);

        float[] pos = new float[2];

        pathMeasure.setPath(path_circle,false);
        pathMeasure.getPosTan(0, pos, null);

        path_search.lineTo(pos[0], pos[1]);


        //initListener
        animatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        };

        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                animatorHandler.sendEmptyMessage(0);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        };

        //initAnimator
        startingAnimator = ValueAnimator.ofFloat(0,1).setDuration(defaultDuration);
        searchingAnimator = ValueAnimator.ofFloat(0,1).setDuration(defaultDuration);
        endingAnimator = ValueAnimator.ofFloat(1,0).setDuration(defaultDuration);

        startingAnimator.addUpdateListener(animatorUpdateListener);
        searchingAnimator.addUpdateListener(animatorUpdateListener);
        endingAnimator.addUpdateListener(animatorUpdateListener);

        startingAnimator.addListener(animatorListener);
        searchingAnimator.addListener(animatorListener);
        endingAnimator.addListener(animatorListener);

        //initHandler
        animatorHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (currentState){
                    case STARTING:
                        isEnd = false;
                        currentState = State.SEARCHING;
                        startingAnimator.removeAllUpdateListeners();
                        searchingAnimator.start();
                        count++;
                        break;
                    case SEARCHING:
                        if(isEnd) {
                            currentState = State.ENDING;
                            searchingAnimator.removeAllUpdateListeners();
                            endingAnimator.start();
                        } else {
                            searchingAnimator.start();
                            count++;
                            if(count >= 3) {
                                isEnd = true;
                            }
                        }
                        break;
                    case ENDING:
                        currentState = State.NONE;
                        endingAnimator.removeAllUpdateListeners();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(width / 2, height / 2);
        canvas.drawColor(getResources().getColor(R.color.colorPrimary));

        switch (currentState) {
            case NONE:
                canvas.drawPath(path_search,paint);
                break;
            case STARTING:
                pathMeasure.setPath(path_search,false);
                Path dst1 = new Path();
                pathMeasure.getSegment(pathMeasure.getLength()*animatorValue,
                        pathMeasure.getLength(),dst1,true);
                canvas.drawPath(dst1,paint);
                break;
            case SEARCHING:
                pathMeasure.setPath(path_circle,false);
                Path dst2 = new Path();

//                float stop = pathMeasure.getLength()*animatorValue;
//                float start = (float) (stop - ((0.5 - Math.abs(animatorValue - 0.5)) * (2 * Math.PI * 100f / 4)));
//                pathMeasure.getSegment(start,stop,dst2,true);

                int num = (int) (animatorValue/0.05);
                float s,y,x;
                switch(num){
                    //各case间无break
                    default:
                    case 3:
                        x = animatorValue-0.15f*(1-animatorValue);
                        s = pathMeasure.getLength();
                        y = -s*x*x+2*s*x;
                        /**
                         * startD:开始截取位置距离Path起点的长度
                         * stopD:结束截取位置距离Path起点的长度
                         * dst:截取的Path将会添加到dst中
                         * startWithMoveTo,true:截取出来的path片段保持原状，
                         * false:截取出来的path片段的起点移动到dst的最后一个点，保持连续性
                         */
                        pathMeasure.getSegment(y, y + 1, dst2, true);
                    case 2:
                        x = animatorValue-0.10f*(1-animatorValue);
                        s = pathMeasure.getLength();
                        y = -s*x*x+2*s*x;
                        pathMeasure.getSegment(y,y+1,dst2,true);
                    case 1:
                        x = animatorValue-0.05f*(1-animatorValue);
                        s = pathMeasure.getLength();
                        y = -s*x*x+2*s*x;
                        pathMeasure.getSegment(y,y+1,dst2,true);
                    case 0:
                        x = animatorValue;
                        s = pathMeasure.getLength();
                        y = -s*x*x+2*s*x;
                        pathMeasure.getSegment(y, y + 1, dst2, true);
                        break;
                }

                canvas.drawPath(dst2,paint);
                break;
            case ENDING:
                pathMeasure.setPath(path_search,false);
                Path dst3 = new Path();
                pathMeasure.getSegment(pathMeasure.getLength()*animatorValue,
                        pathMeasure.getLength(),dst3,true);
                canvas.drawPath(dst3,paint);
                break;
            default:
                break;
        }

    }
}
