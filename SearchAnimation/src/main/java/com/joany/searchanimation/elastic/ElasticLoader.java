package com.joany.searchanimation.elastic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joany on 2016/9/23.
 */
public class ElasticLoader extends View{

    /**
     * 静态圆个数颜色，动态圆颜色，半径
     */
    private int staticCircleCount = 3;
    private int staticCircleColor = 0xffbf3eff;
    private ElasticCircle elasticCircle;
    private int elasticCircleColor = 0xff00bfff;
    private int radius = 30;
    private List<ElasticCircle> circleList = new ArrayList<>();

    /**
     * 静态圆圆心间隔
     */
    private int staticCircleSpace = radius * 5;

    private Paint paint;
    private Path path;

    private int margin = 30;

    private int currentPos = 0;
    private int nextPos = 1;

    public ElasticLoader(Context context) {
        this(context, null);
    }

    public ElasticLoader(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ElasticLoader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        //静态圆
        for(int i = 0; i < staticCircleCount; i++) {
            ElasticCircle circle = new ElasticCircle(radius + staticCircleSpace * i + margin,
                    radius + margin,radius);
            circleList.add(circle);
        }

        startAnim();
    }

    private void startAnim(){
        elasticCircle = new ElasticCircle(circleList.get(currentPos).centerX,
                circleList.get(currentPos).centerY,radius);
        PointF endPoint = new PointF(circleList.get(nextPos).centerX,
                circleList.get(nextPos).centerY);
        elasticCircle.startAnim(endPoint, new ElasticCircle.ElasticBallInterface() {
            @Override
            public void onChange(Path mPath) {
                path = new Path(mPath);
                invalidate();
            }

            @Override
            public void onFinish() {
                currentPos++;
                if(currentPos >= circleList.size() - 1) {
                    currentPos = 0;
                }
                nextPos = currentPos + 1;
                startAnim();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(staticCircleColor);
        for(int i = 0; i < circleList.size(); i++) {
            canvas.drawCircle(circleList.get(i).centerX,circleList.get(i).centerY,radius,paint);
        }
        paint.setColor(elasticCircleColor);
        if(path != null){
            canvas.drawPath(path,paint);
        }
    }
}
