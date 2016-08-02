package com.joany.floatwindowsample.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.joany.floatwindowsample.FloatWindowManager;
import com.joany.floatwindowsample.R;

import java.lang.reflect.Field;

/**
 * Created by joany on 2016/7/27.
 */
public class SmallView extends LinearLayout{

    private WindowManager windowManager;
    public static int viewW;
    public static int viewH;
    private int statusBarH;
    private WindowManager.LayoutParams layoutParams;
    private float xInScreen;
    private float yInScreen;
    private float xDownInScreen;
    private float yDownInScreen;
    private float xInView;
    private float yInView;

    public SmallView(Context context) {
        super(context);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        LayoutInflater.from(context).inflate(R.layout.small_view,this);
        View view = findViewById(R.id.smallViewLayout);
        viewW = view.getLayoutParams().width;
        viewH = view.getLayoutParams().height;
        TextView percentTv = (TextView) findViewById(R.id.percentTv);
        percentTv.setText(FloatWindowManager.getUsedPercentValue(context));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();
                xInScreen = xDownInScreen = event.getRawX();
                yInScreen = yDownInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                updateViewPosition();
                break;
            case MotionEvent.ACTION_UP:
                if(xDownInScreen == xInScreen && yDownInScreen == yInScreen) {
                    openBigWindow();
                }
                break;
        }
        return true;
    }

    public void setParams(WindowManager.LayoutParams params){
        layoutParams = params;
    }

    private void updateViewPosition() {
        layoutParams.x = (int) (xInScreen - xInView);
        layoutParams.y = (int) (yInScreen - yInView);
        windowManager.updateViewLayout(this,layoutParams);
    }

    private void openBigWindow() {
        FloatWindowManager.createBigView(getContext());
        FloatWindowManager.removeSmallView(getContext());
    }

    private float getStatusBarHeight() {
        if(statusBarH == 0) {
            try {
                Class<?> clazz = Class.forName("com.android.internal.R$dimen");
                Object o = clazz.newInstance();
                Field field = clazz.getField("status_bar_height");
                int x = (int) field.get(o);
                statusBarH = getResources().getDimensionPixelOffset(x);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return statusBarH;
    }
}
