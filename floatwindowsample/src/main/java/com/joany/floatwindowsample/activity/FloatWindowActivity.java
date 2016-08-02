package com.joany.floatwindowsample.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.joany.floatwindowsample.R;
import com.joany.floatwindowsample.service.FloatWindowService;

public class FloatWindowActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);
        Button startBtn = (Button) findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FloatWindowActivity.this, FloatWindowService.class);
                startService(intent);
                finish();
            }
        });
    }
}
