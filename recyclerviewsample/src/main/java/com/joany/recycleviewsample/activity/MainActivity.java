package com.joany.recycleviewsample.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.joany.recycleviewsample.R;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    private Button listBtn;
    private Button gridBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData(){
        listBtn = (Button) findViewById(R.id.listBtn);
        gridBtn = (Button) findViewById(R.id.gridBtn);
    }

    private void initView(){
       listBtn.setOnClickListener(this);
        gridBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.listBtn:
                startActivity(new Intent(this,ListViewActivity.class));
                break;
            case R.id.gridBtn:
                startActivity(new Intent(this,GridViewActivity.class));
                break;
            default:
                break;
        }
    }
}
