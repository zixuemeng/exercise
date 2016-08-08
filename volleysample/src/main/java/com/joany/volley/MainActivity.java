package com.joany.volley;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends Activity implements View.OnClickListener{

    private Button startBtn;
    private ImageView imageView;
    private RequestQueue requestQueue;

    private static final String TAG = "zixuemeng";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData(){
        startBtn = (Button) findViewById(R.id.startBtn);
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    private void initView(){
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sendStringRequest();
        //sendJsonRequest();
        //sendImageRequest();
        //setImageLoaderRequest();
        //setGsonRequest();
    }

    private void sendStringRequest() {
        //通过修改volley源码解决乱码问题，或者重写StringRequest中parseNetworkResponse方法
        StringRequest mStringRequest = new StringRequest("http://www.baidu.com",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG,response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, volleyError.getMessage(), volleyError);
                    }
                }
        );

        requestQueue.add(mStringRequest);
    }

    private void sendJsonRequest(){
        JsonRequest mJsonRequest = new JsonObjectRequest(
                "http://www.weather.com.cn/data/sk/101010100.html",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.i(TAG,jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.getMessage(), volleyError);
                    }
                }
        );
        requestQueue.add(mJsonRequest);
    }

    private void sendImageRequest(){
        ImageRequest mImageRequest = new ImageRequest(
                "http://developer.android.com/images/home/aw_dac.png",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                }, 0, 0, Bitmap.Config.ARGB_8888,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG, volleyError.getMessage(), volleyError);
                        imageView.setImageResource(R.mipmap.ic_error_img);
                    }
                });
        requestQueue.add(mImageRequest);
    }

    private void setImageLoaderRequest(){
        ImageLoader mImageLoader = new ImageLoader(requestQueue,new BitmapCache());
        ImageLoader.ImageListener mImageListener = ImageLoader.getImageListener(imageView,
                R.mipmap.ic_default_img,R.mipmap.ic_error_img);
        mImageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",
                mImageListener);

    }

    private class BitmapCache implements ImageLoader.ImageCache{
        private LruCache<String,Bitmap> mCache;
        public BitmapCache(){
            int maxSize = (int) (Runtime.getRuntime().maxMemory()/8);
            mCache = new LruCache<String,Bitmap>(maxSize){
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes()* value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url,bitmap);
        }
    }

    private void setGsonRequest(){
        GsonRequest<WeatherInfo> mGsonRequest = new GsonRequest<WeatherInfo>(
                "http://www.weather.com.cn/data/sk/101010100.html", WeatherInfo.class,
                new Response.Listener<WeatherInfo>() {
                    @Override
                    public void onResponse(WeatherInfo weatherInfo) {
                        Log.i(TAG,"city:"+weatherInfo.getCity()+" temp:"+weatherInfo.getTemp());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.e(TAG,volleyError.getMessage(),volleyError);
                    }
                }
        );
        requestQueue.add(mGsonRequest);
    }
}
