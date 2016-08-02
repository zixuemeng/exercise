package com.joany.floatwindowsample;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import com.joany.floatwindowsample.view.BigView;
import com.joany.floatwindowsample.view.SmallView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by joany on 2016/7/27.
 */
public class FloatWindowManager {

    private static WindowManager windowManager;
    private static ActivityManager activityManager;
    private static BigView bigView;
    private static SmallView smallView;
    private static WindowManager.LayoutParams smallLayoutParams;
    private static WindowManager.LayoutParams bigLayoutParams;


    public static String getUsedPercentValue(Context context){
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr,2048);
            String memoryLine = br.readLine();
            String subMemeoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemeoryLine.replaceAll("\\D+",""));
            long availableSize = getAvailableMemory(context)/1024;
            int percent = (int) ((totalMemorySize - availableSize)/(float)totalMemorySize*100);
            return percent+"%";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateUsedPercent(Context context){
        if(smallView != null) {
            TextView percentTv = (TextView) smallView.findViewById(R.id.percentTv);
            percentTv.setText(getUsedPercentValue(context));
        }
    }

    public static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }

    public static ActivityManager getActivityManager(Context context) {
        if(activityManager == null){
            activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return activityManager;
    }

    public static WindowManager getWindowManager(Context context){
        if(windowManager == null){
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return windowManager;
    }

    public static void createBigView(Context context){
        WindowManager windowManager = getWindowManager(context);
        int screenW = 0;
        int screenH = 0;
        if(Build.VERSION.SDK_INT > 13) {
            Point point = new Point();
            windowManager.getDefaultDisplay().getSize(point);
            screenW = point.x;
            screenH = point.y;
        } else {
            screenW = windowManager.getDefaultDisplay().getWidth();
            screenH = windowManager.getDefaultDisplay().getHeight();
        }
        if(bigView == null) {
            bigView = new BigView(context);
            if(bigLayoutParams == null){
                bigLayoutParams = new WindowManager.LayoutParams();
                bigLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                bigLayoutParams.format = PixelFormat.RGBA_8888;
                bigLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                bigLayoutParams.width = BigView.viewW;
                bigLayoutParams.height = BigView.viewH;
                bigLayoutParams.x = screenW/2 - BigView.viewW/2;
                bigLayoutParams.y = screenH/2 - BigView.viewH/2;
            }
            windowManager.addView(bigView,bigLayoutParams);
        }
    }

    public static void removeBigView(Context context){
        if(bigView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(bigView);
            bigView = null;
        }
    }

    public static void createSmallView(Context context){
        WindowManager windowManager = getWindowManager(context);
        int screenW = 0;
        int screenH = 0;
        if(Build.VERSION.SDK_INT > 13) {
            Point point = new Point();
            windowManager.getDefaultDisplay().getSize(point);
            screenW = point.x;
            screenH = point.y;
        } else {
            screenW = windowManager.getDefaultDisplay().getWidth();
            screenH = windowManager.getDefaultDisplay().getHeight();
        }
        if(smallView == null) {
            smallView = new SmallView(context);
            if(smallLayoutParams == null){
                smallLayoutParams = new WindowManager.LayoutParams();
                smallLayoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                smallLayoutParams.format = PixelFormat.RGBA_8888;
                smallLayoutParams.gravity = Gravity.LEFT | Gravity.TOP;
                smallLayoutParams.width = SmallView.viewW;
                smallLayoutParams.height = SmallView.viewH;
                //如果忽略gravity属性，那么它表示窗口的绝对Y位置。
                //当设置了 Gravity.TOP 或 Gravity.BOTTOM 之后，y值就表示到特定边的距离。
                smallLayoutParams.x = screenW/2 - SmallView.viewW/2;
                smallLayoutParams.y = screenH/2 - SmallView.viewH/2;
            }
            smallView.setParams(smallLayoutParams);
            windowManager.addView(smallView,smallLayoutParams);
        }
    }

    public static void removeSmallView(Context context){
        if(smallView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(smallView);
            smallView = null;
        }
    }

    public static boolean isFloatWindowShowing() {
        return smallView!=null || bigView!=null;
    }
}
