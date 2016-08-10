package com.joany.recycleviewsample.divider;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by joany on 2016/8/10.
 */
public class ListDividerItemDecoration extends RecyclerView.ItemDecoration{

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    private Drawable divider;
    private int orientation;

    public ListDividerItemDecoration(Context context,int orientation){
        TypedArray ar = context.obtainStyledAttributes(ATTRS);
        divider = ar.getDrawable(0);
        ar.recycle();
        this.orientation = orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent) {
        if(orientation == HORIZONTAL_LIST) {
            drawHorizontal(c,parent);
        } else if(orientation == VERTICAL_LIST){
            drawVertical(c,parent);
        }
    }

    private void drawHorizontal(Canvas c,RecyclerView parent){
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + divider.getIntrinsicWidth();
            divider.setBounds(left,top,right,bottom);
            divider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent){
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for(int i = 0; i < childCount; i++){
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left,top,right,bottom);
            divider.draw(c);
        }
    }

//    @Override
//    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
//        if(orientation == HORIZONTAL_LIST) {
//            outRect.set(0,0,divider.getIntrinsicWidth(),0);
//        } else if (orientation == VERTICAL_LIST){
//            outRect.set(0,0,0,divider.getIntrinsicHeight());
//        }
//    }
}
