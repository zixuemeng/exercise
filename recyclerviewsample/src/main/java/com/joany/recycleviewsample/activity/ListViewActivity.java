package com.joany.recycleviewsample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.joany.recycleviewsample.divider.ListDividerItemDecoration;
import com.joany.recycleviewsample.R;
import com.joany.recycleviewsample.adapter.ListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> dataList;
    private ListViewAdapter listViewAdapter;

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
        listViewAdapter = new ListViewAdapter(ListViewActivity.this,dataList);
    }

    private void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listViewAdapter);
        recyclerView.addItemDecoration(new ListDividerItemDecoration(
                this, ListDividerItemDecoration.VERTICAL_LIST));
        listViewAdapter.setOnItemClickListener(new ListViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ListViewActivity.this,
                        "click position:"+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(ListViewActivity.this,
                        "longclick position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<String> getData(){
        List<String> data = new ArrayList<>();
        for(int i = 'A';i<'z';i++){
            data.add(""+(char)i);
        }
        return data;
    }

}
