package com.joany.searchanimation.elastic;

/**
 * Created by joany on 2016/9/23.
 */
public class Circle {
    /**
     * 圆心坐标及半径
     */
    public float centerX;
    public float centerY;
    public float radius;

    /**
     * 圆形左上右下边中心坐标
     */
    public float leftX;
    public float leftY;
    public float topX;
    public float topY;
    public float rightX;
    public float rightY;
    public float bottomX;
    public float bottomY;

    public Circle(float centerX, float centerY, float radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.leftX = centerX - radius;
        this.leftY = centerY;
        this.topX = centerX;
        this.topY = centerY - radius;
        this.rightX = centerX + radius;
        this.rightY = centerY;
        this.bottomX = centerX;
        this.bottomY = centerY + radius;
    }

    public void refresh(float centerX, float centerY,float leftX,float leftY, float topX,
                        float topY,float rightX,float rightY,float bottomX,float bottomY){
        this.centerX = centerX;
        this.centerY = centerY;
        this.leftX = leftX;
        this.leftY = leftY;
        this.topX = topX;
        this.topY = topY;
        this.rightX = rightX;
        this.rightY = rightY;
        this.bottomX = bottomX;
        this.bottomY = bottomY;
    }
}
