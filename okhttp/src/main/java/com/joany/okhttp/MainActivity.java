package com.joany.okhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startBtn;
    private OkHttpClient okHttpClient;

    private static final String TAG = "zixuemeng";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        okHttpClient = new OkHttpClient();
        startBtn = (Button) findViewById(R.id.startBtn);
    }

    private void initView() {
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        doSyncGet();
        //doAsyncGet();
        //doPostFile();
        //doPostForm();
        //doPostMultiPart();
    }

    private void doSyncGet() {
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Request mRequest = new Request.Builder()
                        .url("http://publicobject.com/helloworld.txt").build();
                try {
                    Response mResponse = okHttpClient.newCall(mRequest).execute();
                    if(!mResponse.isSuccessful())
                        throw new IOException("Unexpected code:"+mResponse);
                    Headers mResponseHeaders = mResponse.headers();
                    int n = mResponseHeaders.size();
                    for (int i = 0; i < n; i++) {
                        Log.i(TAG, mResponseHeaders.name(i) + ":" + mResponseHeaders.value(i));
                    }
                    Log.i(TAG, mResponse.body().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }

    private void doAsyncGet() {
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Request mRequest = new Request.Builder()
                        .url("http://publicobject.com/helloworld.txt").build();
                okHttpClient.newCall(mRequest).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        Log.e(TAG,"doAsyncGet failure");
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        if(!response.isSuccessful())
                            throw new IOException("Unexcepted code:"+response);
                        Headers mResponseHeaders = response.headers();
                        int n = mResponseHeaders.size();
                        for (int i = 0; i < n; i++) {
                            Log.i(TAG, mResponseHeaders.name(i) + ":" + mResponseHeaders.value(i));
                        }
                        Log.i(TAG, response.body().toString());
                    }
                });

            }
        });
        mThread.start();
    }

    private void doPostFile(){
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Request mRequest = new Request.Builder()
                        .url("https://api.github.com/markdown/raw")
                        .post(RequestBody.create(
                                MediaType.parse("text/x-markdown;charset=utf-8"),
                                new File("README.md")))
                        .build();
                try {
                    Response mResponse = okHttpClient.newCall(mRequest).execute();
                    if(!mResponse.isSuccessful())
                        throw new IOException("Unexcepted code:"+mResponse);
                    Log.i(TAG,mResponse.body().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }

    private void doPostForm(){
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody mRequestBody = new FormEncodingBuilder().add("search","joany")
                        .build();
                Request mRequest = new Request.Builder()
                        .url("https://en.wikipedia.org/w/index.php")
                        .post(mRequestBody)
                        .build();
                try {
                    Response mResponse = okHttpClient.newCall(mRequest).execute();
                    if(!mResponse.isSuccessful())
                        throw new IOException("Unexcepted code:"+mResponse);
                    Log.i(TAG,mResponse.body().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }

    private void doPostMultiPart(){
        Thread mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody mRequestBody = new MultipartBuilder()
                        .type(MultipartBuilder.FORM)
                        .addPart(
                                Headers.of("Content-Disposition","form-data;name=\"title\""),
                                RequestBody.create(null,"for test"))
                        .addPart(
                                Headers.of("Content-Disposition", "form-data;name=\"image\""),
                                RequestBody.create(MediaType.parse("image/png"),
                                        new File("D:/github/Application/app/src/main/res/mipmap-hdpi/ic_launcher.png")))
                        .build();
                Request mRequest = new Request.Builder()
                        .header("Authorization", "Client-ID " + "...")
                        .url("https://api.imgur.com/3/image")
                        .post(mRequestBody)
                        .build();
                try {
                    Response mResponse = okHttpClient.newCall(mRequest).execute();
                    if(!mResponse.isSuccessful())
                        throw new IOException("Unexcepted code:"+mResponse);
                    Log.i(TAG,mResponse.body().toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }
}
