package com.joany.floatwindowsample.view;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.joany.floatwindowsample.FloatWindowManager;
import com.joany.floatwindowsample.R;
import com.joany.floatwindowsample.service.FloatWindowService;

/**
 * Created by joany on 2016/7/28.
 */
public class BigView extends LinearLayout{

    public static int viewW;
    public static int viewH;

    public BigView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.big_view, this);
        View view = findViewById(R.id.bigViewLayout);
        viewW = view.getLayoutParams().width;
        viewH = view.getLayoutParams().height;
        Button closeBtn = (Button) findViewById(R.id.closeBtn);
        Button back = (Button) findViewById(R.id.backBtn);
        closeBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatWindowManager.removeBigView(context);
                FloatWindowManager.removeSmallView(context);
                Intent intent = new Intent(getContext(),FloatWindowService.class);
                context.stopService(intent);
            }
        });

        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FloatWindowManager.removeBigView(context);
                FloatWindowManager.createSmallView(context);
            }
        });
    }
}
