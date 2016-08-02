package com.joany.floatwindowsample.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;

import com.joany.floatwindowsample.FloatWindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by joany on 2016/7/28.
 */
public class FloatWindowService extends Service {

    private Handler handler = new Handler();

    private Timer timer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(),0,500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        timer = null;
        super.onDestroy();
    }


    private class RefreshTask extends TimerTask {
        @Override
        public void run() {
            if(isHome() && !FloatWindowManager.isFloatWindowShowing()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        FloatWindowManager.createSmallView(getApplicationContext());
                    }
                });
            } else if(!isHome() && FloatWindowManager.isFloatWindowShowing()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        FloatWindowManager.removeBigView(getApplicationContext());
                        FloatWindowManager.removeSmallView(getApplicationContext());
                    }
                });
            } else if(isHome() && FloatWindowManager.isFloatWindowShowing()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        FloatWindowManager.updateUsedPercent(getApplicationContext());
                    }
                });
            }
        }
    }

    private boolean isHome(){
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTaskInfoList = activityManager.getRunningTasks(1);
        return getHomes().contains(runningTaskInfoList.get(0).topActivity.getPackageName());
    }

    private List<String> getHomes(){
        List<String> packageNameList = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo resolveInfo:resolveInfoList){
            packageNameList.add(resolveInfo.activityInfo.packageName);
        }
        return packageNameList;
    }
}
