package com.joany.launcheranimationview.view;

import android.animation.TypeEvaluator;

/**
 * Created by jiqiong.jq on 2016/10/28.
 */
public class ViewPathEvaluator implements TypeEvaluator<ViewPathPoint> {

    public ViewPathEvaluator(){
    }

    @Override
    public ViewPathPoint evaluate(float t, ViewPathPoint startPoint, ViewPathPoint endPoint) {
        /**
         * 在当下时间，动画终点坐标
         */
        float x,y;
        /**
         * 动画起点坐标
         */
        float startX, startY;

        if(endPoint.operation == ViewPathPoint.LINE) {
            x = startPoint.endX + t * (endPoint.endX - startPoint.endX);
            y = startPoint.endY + t * (endPoint.endY - startPoint.endY);
        } else if(endPoint.operation == ViewPathPoint.QUAD) {
            x = (1-t) * (1-t) * startPoint.endX
                    + 2 * (1-t) * t * endPoint.c1X + t * t * endPoint.endX;
            y = (1-t) * (1-t) * startPoint.endY
                    + 2 * (1-t) * t * endPoint.c1Y + t * t * endPoint.endY;
        } else if(endPoint.operation == ViewPathPoint.CUBIC) {
            x = (1-t)*(1-t)*(1-t)*startPoint.endX
                    + 3*(1-t)*(1-t)*t * endPoint.c1X
                    + 3*(1-t)*t*t*endPoint.c2X
                    +t*t*t*endPoint.endX;
            y = (1-t)*(1-t)*(1-t)*startPoint.endY
                    + 3*(1-t)*(1-t)*t * endPoint.c1Y
                    + 3*(1-t)*t*t*endPoint.c2Y
                    +t*t*t*endPoint.endY;
        } else {
            x = endPoint.endX;
            y = endPoint.endY;
        }
        return new ViewPathPoint(x,y);
    }
}
