package com.joany.searchanimation.sticky;

import android.graphics.Path;

/**
 * Created by joany on 2016/9/22.
 */
public class StickyUtils {
    /**
     * @param centerX1      圆心坐标
     * @param centerY1
     * @param r1
     * @param offsetDegree1 贝塞尔曲线相比水平线的偏移角度
     * @param centerX2
     * @param centerY2
     * @param r2
     * @param offsetDegree2
     * @return
     */
    public static Path drawStickyCircle(float centerX1, float centerY1, float r1, float offsetDegree1,
                                        float centerX2, float centerY2, float r2, float offsetDegree2) {
        /**
         * 相比水平线的偏移角度
         */
        float degrees = (float) Math.toDegrees(Math.atan(Math.abs(centerY2 - centerY1) / Math.abs(centerX2 - centerX1)));
        //用于判断两圆的相对位置
        float diffX = centerX1 - centerX2;
        float diffY = centerY1 - centerY2;
        //两条贝塞尔曲线的四个端点,坐标位置见sticky.png
        float x1, y1, x2, y2, x3, y3, x4, y4;

        if (diffX == 0 && diffY > 0) {//圆1在圆2的下边
            x1 = centerX1 - r1 * (float) Math.sin(Math.toRadians(offsetDegree1));
            y3 = y1 = centerY1 - r1 * (float) Math.cos(Math.toRadians(offsetDegree1));
            x3 = centerX1 + r1 * (float) Math.sin(Math.toRadians(offsetDegree1));
            x2 = centerX2 - r2 * (float) Math.sin(Math.toRadians(offsetDegree2));
            y4 = y2 = centerY2 + r2 * (float) Math.cos(Math.toRadians(offsetDegree2));
            x4 = centerX2 + r2 * (float) Math.sin(Math.toRadians(offsetDegree2));
        } else if (diffX == 0 && diffY < 0) {//圆1在圆2的上边
            x1 = centerX1 - r1 * (float) Math.sin(Math.toRadians(offsetDegree1));
            y3 = y1 = centerY1 + r1 * (float) Math.cos(Math.toRadians(offsetDegree1));
            x3 = centerX1 + r1 * (float) Math.sin(Math.toRadians(offsetDegree1));
            x2 = centerX2 - r2 * (float) Math.sin(Math.toRadians(offsetDegree2));
            y4 = y2 = centerY2 - r2 * (float) Math.cos(Math.toRadians(offsetDegree2));
            x4 = centerX2 + r2 * (float) Math.sin(Math.toRadians(offsetDegree2));
        } else if (diffX > 0 && diffY == 0) {//圆1在圆2的右边
            x3 = x1 = centerX1 - r1 * (float) Math.cos(Math.toRadians(offsetDegree1));
            y1 = centerY1 - r1 * (float) Math.sin(Math.toRadians(offsetDegree1));
            y3 = centerY1 + r1 * (float) Math.sin(Math.toRadians(offsetDegree1));
            x4 = x2 = centerX2 + r2 * (float) Math.cos(Math.toRadians(offsetDegree2));
            y2 = centerY2 - r2 * (float) Math.sin(Math.toRadians(offsetDegree2));
            y4 = centerX2 + r2 * (float) Math.sin(Math.toRadians(offsetDegree2));
        } else if (diffX < 0 && diffY == 0) {//圆1在圆2的左边
            x3 = x1 = centerX1 + r1 * (float) Math.cos(Math.toRadians(offsetDegree1));
            y1 = centerY1 - r1 * (float) Math.sin(Math.toRadians(offsetDegree1));
            y3 = centerY1 + r1 * (float) Math.sin(Math.toRadians(offsetDegree1));
            x4 = x2 = centerX2 - r2 * (float) Math.cos(Math.toRadians(offsetDegree2));
            y2 = centerY2 - r2 * (float) Math.sin(Math.toRadians(offsetDegree2));
            y4 = centerX2 + r2 * (float) Math.sin(Math.toRadians(offsetDegree2));
        } else if (diffX > 0 && diffY > 0) {//圆1在圆2的右下角
            x1 = centerX1 - r1 * (float) Math.cos(Math.toRadians(offsetDegree1 - degrees));
            y1 = centerY1 + r1 * (float) Math.sin(Math.toRadians(offsetDegree1 - degrees));
            x3 = centerX1 - r1 * (float) Math.cos(Math.toRadians(offsetDegree1 + degrees));
            y3 = centerY1 - r1 * (float) Math.sin(Math.toRadians(offsetDegree1 + degrees));
            x2 = centerX2 + r2 * (float) Math.cos(Math.toRadians(offsetDegree2 + degrees));
            y2 = centerY2 + r2 * (float) Math.sin(Math.toRadians(offsetDegree2 + degrees));
            x4 = centerX2 + r2 * (float) Math.cos(Math.toRadians(offsetDegree2 - degrees));
            y4 = centerY2 - r2 * (float) Math.sin(Math.toRadians(offsetDegree2 - degrees));
        } else if (diffX < 0 && diffY > 0) {//圆1在圆2的左下角
            x1 = centerX1 + r1 * (float) Math.cos(Math.toRadians(offsetDegree1 + degrees));
            y1 = centerY1 - r1 * (float) Math.sin(Math.toRadians(offsetDegree1 + degrees));
            x3 = centerX1 + r1 * (float) Math.cos(Math.toRadians(offsetDegree1 - degrees));
            y3 = centerY1 + r1 * (float) Math.sin(Math.toRadians(offsetDegree1 - degrees));
            x2 = centerX2 - r2 * (float) Math.cos(Math.toRadians(offsetDegree2 - degrees));
            y2 = centerY2 - r2 * (float) Math.sin(Math.toRadians(offsetDegree2 - degrees));
            x4 = centerX2 - r2 * (float) Math.cos(Math.toRadians(offsetDegree2 + degrees));
            y4 = centerY2 + r2 * (float) Math.sin(Math.toRadians(offsetDegree2 + degrees));
        } else if (diffX > 0 && diffY < 0) {//圆1在圆2的右上角
            x1 = centerX1 - r1 * (float) Math.cos(Math.toRadians(offsetDegree1 + degrees));
            y1 = centerY1 + r1 * (float) Math.sin(Math.toRadians(offsetDegree1 + degrees));
            x3 = centerX1 - r1 * (float) Math.cos(Math.toRadians(offsetDegree1 - degrees));
            y3 = centerY1 - r1 * (float) Math.sin(Math.toRadians(offsetDegree1 - degrees));
            x2 = centerX2 + r2 * (float) Math.cos(Math.toRadians(offsetDegree2 - degrees));
            y2 = centerY2 + r2 * (float) Math.sin(Math.toRadians(offsetDegree2 - degrees));
            x4 = centerX2 + r2 * (float) Math.cos(Math.toRadians(offsetDegree2 + degrees));
            y4 = centerY2 - r2 * (float) Math.sin(Math.toRadians(offsetDegree2 + degrees));
        } else {//圆1在圆2的左上角
            x1 = centerX1 + r1 * (float) Math.cos(Math.toRadians(offsetDegree1 - degrees));
            y1 = centerY1 - r1 * (float) Math.sin(Math.toRadians(offsetDegree1 - degrees));
            x3 = centerX1 + r1 * (float) Math.cos(Math.toRadians(offsetDegree1 + degrees));
            y3 = centerY1 + r1 * (float) Math.sin(Math.toRadians(offsetDegree1 + degrees));
            x2 = centerX2 - r2 * (float) Math.cos(Math.toRadians(offsetDegree2 + degrees));
            y2 = centerY2 - r2 * (float) Math.sin(Math.toRadians(offsetDegree2 + degrees));
            x4 = centerX2 - r2 * (float) Math.cos(Math.toRadians(offsetDegree2 - degrees));
            y4 = centerY2 + r2 * (float) Math.sin(Math.toRadians(offsetDegree2 - degrees));
        }
        //贝塞尔曲线控制点
        float controlX1, controlY1, controlX2, controlY2;
        controlX1 = (x2 + x3) / 2;
        controlY1 = (y2 + y3) / 2;
        controlX2 = (x1 + x4) / 2;
        controlY2 = (y1 + y4) / 2;

        Path path = new Path();
        path.reset();
        path.moveTo(x1, y1);
        path.quadTo(controlX1, controlY1, x2, y2);
        path.lineTo(x4, y4);
        path.quadTo(controlX2, controlY2, x3, y3);
        path.lineTo(x1, y1);
        return path;
    }
}
