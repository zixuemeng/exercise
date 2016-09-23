package com.joany.searchanimation.elastic;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.animation.LinearInterpolator;

/**
 * Created by joany on 2016/9/23.
 */
public class ElasticCircle extends Circle {
    /**
     * 向上下左右运动
     */
    private static final int DIRECTION_UP = 1;
    private static final int DIRECTION_DOWN = 2;
    private static final int DIRECTION_LEFT = 3;
    private static final int DIRECTION_RIGHT = 4;

    /**
     * 动画方向
     */
    private int direction;
    /**
     * 动画完成百分比
     */
    private float animPercent;
    /**
     * 弹性距离、百分比
     */
    private float elasticDistance;
    private float elasticPercent = 0.8f;
    /**
     * 起点到终点圆心移动距离
     */
    private float moveDistance;
    /**
     * 动画时间
     */
    private long duration = 5000;
    /**
     * 控制点偏移值
     */
    private float offsetLeft,offsetTop,offsetRight,offsetBottom;
    /**
     * 圆形偏移比例
     */
    private float c = 0.551915024494f;
    private float c2 = 0.65f;
    /**
     * 动画开始结束点
     */
    private Circle startPoint;
    private Circle endPoint;

    public ElasticCircle(float centerX, float centerY, float radius) {
        super(centerX, centerY, radius);
        init();
    }

    private void init(){
        elasticDistance = elasticPercent * radius;
        offsetLeft = offsetTop = offsetRight = offsetBottom = c * radius;
    }

    public interface ElasticBallInterface {
        void onChange(Path path);
        void onFinish();
    }
    private ElasticBallInterface elasticBallInterface;

    public void setElasticBallInterface(ElasticBallInterface elasticBallInterface) {
        this.elasticBallInterface = elasticBallInterface;
    }

    private ElasticCircle animEndPoint1;
    private ElasticCircle animEndPoint2;
    private ElasticCircle animEndPoint3;
    private ElasticCircle animEndPoint4;
    private ElasticCircle animEndPoint5;

    public void startAnim(PointF endPoint, final ElasticBallInterface elasticBallInterface){
        this.endPoint = new ElasticCircle(endPoint.x,endPoint.y,radius);
        this.startPoint = new ElasticCircle(centerX,centerY,radius);
        this.animEndPoint1 = new ElasticCircle(centerX,centerY,radius);
        this.animEndPoint2 = new ElasticCircle(centerX,centerY,radius);
        this.animEndPoint3 = new ElasticCircle(centerX,centerY,radius);
        this.animEndPoint4 = new ElasticCircle(centerX,centerY,radius);
        this.animEndPoint5 = new ElasticCircle(centerX,centerY,radius);
        this.elasticBallInterface = elasticBallInterface;

        judgeDirection();

        moveDistance = getDistance(startPoint.centerX,startPoint.centerY,endPoint.x,endPoint.y);

        anim0();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0,1);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animPercent = (float) valueAnimator.getAnimatedValue();
                if (animPercent >= 0 && animPercent <= 0.2) {
                    anim1();
                } else if (animPercent > 0.2 && animPercent <= 0.5) {
                    anim2();
                } else if (animPercent > 0.5 && animPercent <= 0.8) {
                    anim3();
                } else if (animPercent > 0.8 && animPercent <= 0.9) {
                    anim4();
                } else {
                    anim5();
                }
                if (elasticBallInterface != null) {
                    elasticBallInterface.onChange(drawElasticCircle(leftX, leftY, offsetLeft, offsetLeft,
                            topX, topY, offsetTop, offsetTop, rightX, rightY, offsetRight, offsetRight,
                            bottomX, bottomY, offsetBottom, offsetBottom));
                }
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(elasticBallInterface != null) {
                    elasticBallInterface.onFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        valueAnimator.start();
    }

    private void judgeDirection(){
        if(endPoint.centerX > startPoint.centerX) {
            direction = DIRECTION_RIGHT;
        } else if(endPoint.centerX < startPoint.centerX) {
            direction = DIRECTION_LEFT;
        } else if (endPoint.centerY > startPoint.centerY) {
            direction = DIRECTION_DOWN;
        } else {
            direction = DIRECTION_UP;
        }
    }

    private void anim0(){
        offsetLeft = offsetTop = offsetRight = offsetBottom = c * radius;
    }

    private void anim1(){//[0,0.2]
        //使每次动画的百分比从0到1
        float percent = animPercent * 5f;
        if(direction == DIRECTION_RIGHT) {
            rightX = startPoint.rightX + percent * elasticDistance;
        } else if(direction == DIRECTION_LEFT) {
            leftX = startPoint.leftX - percent * elasticDistance;
        } else if(direction == DIRECTION_UP) {
            topY = startPoint.topY - percent * elasticDistance;
        } else if(direction == DIRECTION_DOWN) {
            bottomY = startPoint.bottomY + percent * elasticDistance;
        }
        animEndPoint1.refresh(centerX,centerY,leftX,leftY,topX,topY,
                rightX,rightY,bottomX,bottomY);
    }

    private void anim2(){//(0.2,0.5]
        float percent = (float) ((animPercent - 0.2) * 10f / 3);
        if(direction == DIRECTION_RIGHT) {
            rightX = animEndPoint1.rightX + percent * (moveDistance - elasticDistance) / 2;
            leftX = animEndPoint1.leftX + percent * (moveDistance - elasticDistance) / 2;
            centerX = animEndPoint1.centerX + percent * (moveDistance / 2);
            topX = bottomX = centerX;
            offsetTop = offsetBottom = c * radius + percent * (c2 - c) * radius;
        } else if(direction == DIRECTION_LEFT) {
            rightX = animEndPoint1.rightX - percent * (moveDistance - elasticDistance) / 2;
            leftX = animEndPoint1.leftX - percent * (moveDistance - elasticDistance) / 2;
            centerX = animEndPoint1.centerX - percent * (moveDistance / 2);
            topX = bottomX = centerX;
            offsetTop = offsetBottom = c * radius + percent * (c2 - c) * radius;
        } else if(direction == DIRECTION_UP) {
            topY = animEndPoint1.topY - percent * (moveDistance - elasticDistance) / 2;
            bottomY = animEndPoint1.bottomY - percent * (moveDistance - elasticDistance) / 2;
            centerY = animEndPoint1.centerY - percent * (moveDistance / 2);
            leftY = rightY = centerY;
            offsetLeft = offsetRight = c * radius + percent * (c2 - c) * radius;
        } else if(direction == DIRECTION_DOWN) {
            topY = animEndPoint1.topY + percent * (moveDistance - elasticDistance) / 2;
            bottomY = animEndPoint1.bottomY + percent * (moveDistance - elasticDistance) / 2;
            centerY = animEndPoint1.centerY + percent * (moveDistance / 2);
            leftY = rightY = centerY;
            offsetLeft = offsetRight = c * radius + percent * (c2 - c) * radius;
        }
        animEndPoint2.refresh(centerX,centerY,leftX,leftY,topX,topY,
                rightX,rightY,bottomX,bottomY);
    }

    private void anim3(){//(0.5,0.8]
        float percent = (float) ((animPercent - 0.5) * 10f / 3);
        if(direction == DIRECTION_RIGHT) {
            rightX = animEndPoint2.rightX + percent * (endPoint.rightX - animEndPoint2.rightX);//moveDistance/2
            leftX = animEndPoint2.leftX + percent * (endPoint.centerX - animEndPoint2.centerX) / 2;
            centerX = animEndPoint2.centerX + percent * (endPoint.centerX - animEndPoint2.centerX);
            topX = bottomX = centerX;
            offsetTop = offsetBottom = c2 * radius - percent * (c2 - c) * radius;
        } else if(direction == DIRECTION_LEFT) {
            rightX = animEndPoint2.rightX - percent * (endPoint.rightX - animEndPoint2.rightX);//moveDistance/2
            leftX = animEndPoint2.leftX - percent * (endPoint.centerX - animEndPoint2.centerX) / 2;
            centerX = animEndPoint2.centerX - percent * (endPoint.centerX - animEndPoint2.centerX);
            topX = bottomX = centerX;
            offsetTop = offsetBottom = c2 * radius - percent * (c2 - c) * radius;
        } else if(direction == DIRECTION_UP) {
            topY = animEndPoint2.topY - percent * (endPoint.topY - animEndPoint2.topY) / 2;
            bottomY = animEndPoint2.bottomY - percent * (endPoint.centerY - animEndPoint2.centerY) / 2;
            centerY = animEndPoint2.centerY - percent * (endPoint.centerY - animEndPoint2.centerY);
            leftY = rightY = centerY;
            offsetLeft = offsetRight = c2 * radius - percent * (c2 - c) * radius;
        } else if(direction == DIRECTION_DOWN) {
            topY = animEndPoint2.topY + percent * (endPoint.topY - animEndPoint2.topY) / 2;
            bottomY = animEndPoint2.bottomY + percent * (endPoint.centerY - animEndPoint2.centerY) / 2;
            centerY = animEndPoint2.centerY + percent * (endPoint.centerY - animEndPoint2.centerY);
            leftY = rightY = centerY;
            offsetLeft = offsetRight = c2 * radius - percent * (c2 - c) * radius;
        }
        animEndPoint3.refresh(centerX,centerY,leftX,leftY,topX,topY,
                rightX,rightY,bottomX,bottomY);
    }

    private void anim4(){//(0.8,0.9]
        float percent = (float) ((animPercent - 0.8) * 10f);
        if(direction == DIRECTION_RIGHT) {
//            rightX = endPoint.rightX;
            leftX = animEndPoint3.leftX + percent * (Math.abs(endPoint.leftX - animEndPoint3.leftX)
                    + elasticDistance /2);
//            centerX = endPoint.centerX;
//            topX = endPoint.topX;
//            bottomX = endPoint.bottomX;
        } else if(direction == DIRECTION_LEFT) {
//            leftX = endPoint.leftX;
            rightX = animEndPoint3.rightX - percent * (Math.abs(endPoint.rightX - animEndPoint3.rightX)
                    + elasticDistance /2);
//            centerX = endPoint.centerX;
//            topX = endPoint.topX;
//            bottomX = endPoint.bottomX;
        } else if(direction == DIRECTION_UP) {
//            topY = endPoint.topY;
            bottomY = animEndPoint3.bottomY - percent * (Math.abs(endPoint.bottomY - animEndPoint3.bottomY)
                    + elasticDistance / 2);
//            centerY = endPoint.centerY;
//            leftY = endPoint.leftY;
//            rightY = endPoint.rightY;
        } else if(direction == DIRECTION_DOWN) {
//            bottomY = endPoint.bottomY;
            topY = animEndPoint3.topY + percent * (Math.abs(endPoint.topY - animEndPoint3.topY)
                    + elasticDistance / 2);
//            centerY = endPoint.centerY;
//            leftY = endPoint.leftY;
//            rightY = endPoint.rightY;
        }
        animEndPoint4.refresh(centerX,centerY,leftX,leftY,topX,topY,
                rightX,rightY,bottomX,bottomY);
    }

    private void anim5(){//(0.9,1]
        float percent = (float) ((animPercent - 0.9 )* 10f);
        if(direction == DIRECTION_RIGHT) {
            leftX = animEndPoint4.leftX + percent * (endPoint.leftX - animEndPoint4.leftX);
        } else if(direction == DIRECTION_LEFT) {
            rightX = animEndPoint4.rightX + percent * (endPoint.rightX - animEndPoint4.rightX);
        } else if(direction == DIRECTION_UP) {
            bottomY = animEndPoint4.bottomY + percent * (endPoint.bottomY - animEndPoint4.bottomY);
        } else if(direction == DIRECTION_DOWN) {
            topY = animEndPoint4.topY + percent * (endPoint.topY - animEndPoint4.topY);
        }
        animEndPoint5.refresh(centerX,centerY,leftX,leftY,topX,topY,
                rightX,rightY,bottomX,bottomY);
    }

    private float getDistance(float x1, float y1, float x2, float y2){
        return (float) Math.sqrt(Math.pow(x2 - x1,2) + Math.pow(y2 - y1,2));
    }

    private Path drawElasticCircle(float leftX, float leftY, float offsetLeft1, float offsetLeft2,
                                   float topX, float topY, float offsetTop1, float offsetTop2,
                                   float rightX, float rightY, float offsetRight1, float offsetRight2,
                                   float bottomX, float bottomY, float offsetBottom1, float offsetBottom2
                                   ){
        PointF controlLeft1, controlLeft2,controlTop1, controlTop2, controlRight1, controlRight2,
                controlBottom1, controlBottom2;
        controlLeft1 = new PointF();
        controlLeft1.x = leftX;
        controlLeft1.y = leftY - offsetLeft1;
        controlLeft2 = new PointF();
        controlLeft2.x = leftX;
        controlLeft2.y = leftY + offsetLeft2;

        controlTop1 = new PointF();
        controlTop1.x = topX - offsetTop1;
        controlTop1.y = topY;
        controlTop2 = new PointF();
        controlTop2.x = topX + offsetTop2;
        controlTop2.y = topY;

        controlRight1 = new PointF();
        controlRight1.x = rightX;
        controlRight1.y = rightY - offsetRight1;
        controlRight2 = new PointF();
        controlRight2.x = rightX;
        controlRight2.y = rightY + offsetRight2;

        controlBottom1 = new PointF();
        controlBottom1.x = bottomX - offsetBottom1;
        controlBottom1.y = bottomY;
        controlBottom2 = new PointF();
        controlBottom2.x = bottomX + offsetBottom2;
        controlBottom2.y = bottomY;

        Path path = new Path();
        path.moveTo(topX,topY);
        path.cubicTo(controlTop2.x, controlTop2.y, controlRight1.x, controlRight1.y, rightX, rightY);
        path.cubicTo(controlRight2.x, controlRight2.y, controlBottom2.x, controlBottom2.y, bottomX, bottomY);
        path.cubicTo(controlBottom1.x, controlBottom1.y, controlLeft2.x, controlLeft2.y, leftX, leftY);
        path.cubicTo(controlLeft1.x,controlLeft1.y,controlTop1.x,controlTop1.y,topX,topY);
        return path;
    }
}
