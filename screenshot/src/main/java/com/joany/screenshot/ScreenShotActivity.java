package com.joany.screenshot;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class ScreenShotActivity extends AppCompatActivity {

    private ScrollView sv;
    private Button screenBtn;
    private Bitmap bitmap;

    private SimpleDateFormat dateFormat = null;
    private String strDate = null;
    private String path = null;
    private String imageName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_shot);

        sv = (ScrollView) findViewById(R.id.sv);
        screenBtn = (Button) findViewById(R.id.screenBtn);

        screenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bitmap = getViewBitmap();
                bitmap = getScrollViewBitmap(getApplicationContext(),sv);
                savePic(bitmap);

               /* String mSavedPath = "/sdcard"+ File.separator+"screenshot.png";
                try {
                    Runtime.getRuntime().exec("screencap -p " + mSavedPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
            }
        });

        // 获取状态栏高度
//        Rect frame = new Rect();
//        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;
//        System.out.println(statusBarHeight);

        // 获取屏幕长和高
//        int width = getWindowManager().getDefaultDisplay().getWidth();
//        int height = getWindowManager().getDefaultDisplay().getHeight();
        // 去掉标题栏
//        Bitmap bitmap = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
//                - statusBarHeight);

//        Bitmap bitmap = Bitmap.createBitmap(width, height
//                - statusBarHeight, Bitmap.Config.ARGB_8888);


        //screenImg.draw(new Canvas(bitmap));

        //screenImg.setImageDrawable(new BitmapDrawable(getResources(),bitmap));

    }

    private Bitmap getViewBitmap() {
        View v = getWindow().getDecorView();
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        Bitmap mBitmap = Bitmap.createBitmap(v.getDrawingCache(),0,0,
                getScreenW(getApplicationContext()),getScreenH(getApplicationContext()));
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return mBitmap;
    }

    private void savePic(Bitmap bitmap){
        if(bitmap == null) {
        } else {
            try {
                path = Environment.getExternalStorageDirectory().getPath()+"/Pictures/";
                dateFormat = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
                strDate = dateFormat.format(new java.util.Date());
                imageName = path+strDate+".png";
                File file = new File(imageName);
                if(!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                fos.flush();
                fos.close();
                Intent media = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
                media.setData(contentUri);
                sendBroadcast(media);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap getViewBp(View v) {
        if(null == v) {
            return null;
        }
        /*if(Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int)v.getX(),(int)v.getY(),
                    (int)v.getX()+v.getMeasuredWidth(),
                    (int)v.getY()+v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0,0,v.getMeasuredWidth(),v.getMeasuredHeight());
        }*/
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache(),0,0,
                v.getMeasuredWidth(),v.getMeasuredHeight());
        return b;
    }

    private Bitmap getViewBpWithoutBottom(View v){
        if(null == v) {
            return null;
        }
        /*if(Build.VERSION.SDK_INT >= 11) {
            v.measure(View.MeasureSpec.makeMeasureSpec(v.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(v.getHeight(), View.MeasureSpec.EXACTLY));
            v.layout((int) v.getX(), (int) v.getY(),
                    (int) v.getX() + v.getMeasuredWidth(),
                    (int) v.getY() + v.getMeasuredHeight());
        } else {
            v.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            v.layout(0,0,v.getMeasuredWidth(),v.getMeasuredHeight());
        }*/
        Bitmap bp = Bitmap.createBitmap(v.getDrawingCache(),0,0,
                v.getMeasuredWidth(),v.getMeasuredHeight() - v.getPaddingBottom());
        return bp;
    }

    private Bitmap getScrollViewBitmap(Context context,ScrollView sv) {
        if(null == sv) {
            return null;
        }
        sv.setVerticalScrollBarEnabled(false);
        sv.setVerticalFadingEdgeEnabled(false);
        sv.scrollTo(0, 0);
        sv.setDrawingCacheEnabled(true);
        sv.buildDrawingCache(true);
        Bitmap b = getViewBpWithoutBottom(sv);
        int visibleHeight = sv.getHeight();
        int totalHeight = sv.getChildAt(0).getHeight();

        if(totalHeight > visibleHeight) {
            int w = getScreenW(context);
            int absVisibleHeight = visibleHeight - sv.getPaddingTop() - sv.getPaddingBottom();
            do {
                int resHeight = totalHeight - visibleHeight;
                Bitmap temp = null;
                if(resHeight < absVisibleHeight) {
                    sv.scrollBy(0,resHeight);
                    visibleHeight  += resHeight;
                    temp = getViewBp(sv);
                } else {
                    sv.scrollBy(0,absVisibleHeight);
                    visibleHeight += absVisibleHeight;
                    temp = getViewBpWithoutBottom(sv);
                }
                b = mergeBitmap( w, visibleHeight,temp, 0, sv.getScrollY(),b,0,0);
            } while (visibleHeight < totalHeight);
        }

        sv.scrollTo(0,0);
        sv.setVerticalFadingEdgeEnabled(true);
        sv.setVerticalScrollBarEnabled(true);
        sv.setDrawingCacheEnabled(false);
        sv.destroyDrawingCache();
        return b;
    }

    private int getScreenW(Context context) {
        int w = 0;
        if(Build.VERSION.SDK_INT > 13) {
            Point p = new Point();
            ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getSize(p);
            w = p.x;
        } else {
            w = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getWidth();
        }
        return w;
        //return sv.getWidth();
    }

    private int getScreenH(Context context) {
        int h = 0;
        if(Build.VERSION.SDK_INT > 13) {
            Point p = new Point();
            ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getSize(p);
            h = p.y;
        } else {
            h = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getHeight();
        }
        return h;
    }

    private Bitmap mergeBitmap(int newImageW,int newImageH,
                               Bitmap background,float backX,float backY,
                               Bitmap foreground,float foreX,float foreY) {
        if(null == background || null == foreground){
            return null;
        }
        Bitmap newBp = Bitmap.createBitmap(newImageW,newImageH, Bitmap.Config.ARGB_8888);
        Canvas cv = new Canvas(newBp);
        cv.drawBitmap(background,backX,backY,null);
        cv.drawBitmap(foreground, foreX,foreY,null);
        cv.save(Canvas.ALL_SAVE_FLAG);
        cv.restore();
        return newBp;
    }

    private Bitmap getTitleBarBp(){
        Bitmap bp = null;
        View v = getWindow().getDecorView().findViewById(android.R.id.title);
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache(true);
        bp = v.getDrawingCache();
        v.setDrawingCacheEnabled(false);
        v.destroyDrawingCache();
        return bp;
    }
}