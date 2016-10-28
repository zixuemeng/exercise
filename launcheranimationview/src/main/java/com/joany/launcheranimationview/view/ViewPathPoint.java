package com.joany.launcheranimationview.view;

/**
 * Created by jiqiong.jq on 2016/10/28.
 */
public class ViewPathPoint {
    /**
     * 贝塞尔曲线控制点1的坐标
     */
    float c1X,c1Y;
    /**
     * 贝塞尔曲线控制点2的坐标
     */
    float c2X, c2Y;
    /**
     * path终点坐标
     */
    float endX, endY;
    /**
     * 记录操作名称，方便后面evalueator时计算
     */
    int operation;

    public static final int MOVE = 0;
    public static final int LINE = 1;
    public static final int QUAD = 2;
    public static final int CUBIC = 3;

    public ViewPathPoint(float endX, float endY) {
        this.endX = endX;
        this.endY = endY;
    }

    private ViewPathPoint(float endX, float endY, int operation) {
        this.endX = endX;
        this.endY = endY;
        this.operation = operation;
    }

    private ViewPathPoint(float c1X,float c1Y,float endX, float endY,int operation) {
        this.c1X = c1X;
        this.c1Y = c1Y;
        this.endX = endX;
        this.endY = endY;
        this.operation = operation;
    }

    private ViewPathPoint(float c1X, float c1Y, float c2X, float c2Y, float endX, float endY,int operation) {
        this.c1X = c1X;
        this.c1Y = c1Y;
        this.c2X = c2X;
        this.c2Y = c2Y;
        this.endX = endX;
        this.endY = endY;
        this.operation = operation;
    }

    public static ViewPathPoint moveTo(float endX, float endY) {
        return new ViewPathPoint(endX,endY,MOVE);
    }

    public static ViewPathPoint lineTo(float endX, float endY) {
        return new ViewPathPoint(endX,endY,LINE);
    }

    public static ViewPathPoint quadTo(float c1X,float c1Y,float endX,float endY) {
        return new ViewPathPoint(c1X,c1Y,endX,endY,QUAD);
    }

    public static ViewPathPoint cubicTo(float c1X,float c1Y,float c2X, float c2Y,float endX, float endY) {
        return new ViewPathPoint(c1X,c1Y,c2X,c2Y,endX,endY,CUBIC);
    }
}
