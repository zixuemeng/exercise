package com.joany.recycleviewsample.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by joany on 2016/8/10.
 */
public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable divider;

    public GridDividerItemDecoration(Context context) {
        TypedArray ar = context.obtainStyledAttributes(ATTRS);
        divider = ar.getDrawable(0);
        ar.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if(layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if(layoutManager instanceof StaggeredGridLayoutManager){
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + divider.getIntrinsicWidth();
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() - params.bottomMargin;

            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin + divider.getIntrinsicWidth();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

//    private boolean isLastColumn(RecyclerView parent, int pos, int spanCount, int childCount){
//        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
//        if(layoutManager instanceof GridLayoutManager) {
//            if((pos+1)%spanCount == 0) {
//                return true;
//            }
//        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
//            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
//            if(orientation == StaggeredGridLayoutManager.VERTICAL) {
//                if((pos+1)%spanCount == 0){
//                    return true;
//                }
//            } else {
//                int remainder = childCount%spanCount;
//                if(remainder==0){
//                    childCount = childCount - spanCount;
//                } else {
//                    childCount = childCount - remainder;
//                }
//                if(pos >= childCount) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
//        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
//        if( layoutManager instanceof  GridLayoutManager) {
//            int remainder = childCount%spanCount;
//            if(remainder==0){
//                childCount = childCount - spanCount;
//            } else {
//                childCount = childCount - remainder;
//            }
//            if(pos >= childCount) {
//                return true;
//            }
//        } else if(layoutManager instanceof StaggeredGridLayoutManager) {
//            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
//            if(orientation == StaggeredGridLayoutManager.HORIZONTAL) {
//                if((pos+1)%spanCount == 0) {
//                    return true;
//                }
//            } else {
//                int remainder = childCount%spanCount;
//                if(remainder==0){
//                    childCount = childCount - spanCount;
//                } else {
//                    childCount = childCount - remainder;
//                }
//                if(pos >= childCount) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }

//    @Override
//    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
//        int spanCount = getSpanCount(parent);
//        int childCount = parent.getChildCount();
//        if(isLastColumn(parent,itemPosition,spanCount,childCount)) {
//            outRect.set(0,0,0,divider.getIntrinsicHeight());
//        } else if(isLastRaw(parent,itemPosition,spanCount,childCount)){
//            outRect.set(0,0,divider.getIntrinsicWidth(),0);
//        }
//    }
}
