package com.joany.launcheranimationview.view;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by jiqiong.jq on 2016/10/28.
 */
public class ViewPath {
    private ArrayList<ViewPathPoint> mPointsList;

    public ViewPath(){
        mPointsList = new ArrayList<>();
    }

    public void moveTo(float x, float y) {
        mPointsList.add(ViewPathPoint.moveTo(x,y));
    }

    public void lineTo(float x, float y) {
        mPointsList.add(ViewPathPoint.lineTo(x, y));
    }

    public void quadTo(float c1X, float c1Y, float x, float y) {
        mPointsList.add(ViewPathPoint.quadTo(c1X, c1Y, x, y));
    }

    public void cubicTo(float c1X, float c1Y, float c2X,float c2Y,float x,float y) {
        mPointsList.add(ViewPathPoint.cubicTo(c1X,c1Y,c2X,c2Y,x,y));
    }

    public Collection<ViewPathPoint> getPoints(){
        return mPointsList;
    }
}
