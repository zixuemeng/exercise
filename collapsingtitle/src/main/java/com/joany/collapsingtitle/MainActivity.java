package com.joany.collapsingtitle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbsListView.OnScrollListener{

    private ListView listView;
    private ImageView headerImg;
    private TextView headerTitle;
    private TextView titlebar;
    private float headerHeight;
    private float minHeaderHeight;
    private float titleSize;
    private float minTitleSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData(){
        listView = (ListView) findViewById(R.id.listview);
        titlebar = (TextView) findViewById(R.id.titlebar);
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.min_header_height);
        titleSize = getResources().getDimension(R.dimen.title_size);
        minTitleSize = getResources().getDimension(R.dimen.min_title_size);
    }

    private void initView(){
        View header = LayoutInflater.from(this).inflate(R.layout.header,listView,false);
        headerImg = (ImageView) header.findViewById(R.id.id_header_img);
        headerTitle = (TextView) header.findViewById(R.id.id_title_text);
        listView.addHeaderView(header);
        listView.setOnScrollListener(this);
        listView.setAdapter(new ListViewAdapter(this,getData()));
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if(firstVisibleItem == 0) {
            float scrollY = absListView.getChildAt(0)==null ? 0:-absListView.getChildAt(0).getTop();
            float headerBarOffsetY = headerHeight - minHeaderHeight;
            float offsetPercent = Math.max(Math.min(scrollY/headerBarOffsetY,1f),0f);

            headerImg.setTranslationY(scrollY / 2);
            float titleScale = minTitleSize / titleSize;
            headerTitle.setTranslationY(scrollY / 2);
            //二元一次方程求解
            headerTitle.setScaleX(1 - offsetPercent * (1 - titleScale));
            headerTitle.setScaleY(1 - offsetPercent * (1 - titleScale));

            if(offsetPercent == 1) {
                titlebar.setVisibility(View.VISIBLE);
            } else {
                titlebar.setVisibility(View.GONE);
            }
        }
    }

    private List<String> getData(){
        List<String> list = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            list.add("item"+i);
        }
        return list;
    }
}
