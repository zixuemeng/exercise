package com.joany.recycleviewsample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.joany.recycleviewsample.R;
import com.joany.recycleviewsample.adapter.GridViewAdapter;
import com.joany.recycleviewsample.divider.GridDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class GridViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> dataList;
    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        initData();
        initView();
    }

    private void initData(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        dataList = getData();
        gridViewAdapter = new GridViewAdapter(GridViewActivity.this,dataList);
    }

    private void initView(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(gridViewAdapter);
        recyclerView.addItemDecoration(new GridDividerItemDecoration(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        gridViewAdapter.setOnItemClickListener(new GridViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(GridViewActivity.this,
                        "click position:"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(GridViewActivity.this,
                        "longclick position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> getData(){
        List<String> data = new ArrayList<>();
        for(int i = 'A';i<'Z';i++){
            data.add(""+(char)i);
        }
        return data;
    }

}
