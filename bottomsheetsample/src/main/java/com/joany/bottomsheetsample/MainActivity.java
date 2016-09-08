package com.joany.bottomsheetsample;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.joany.bottomsheetsample.R.id.recyclerview;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Bind(R.id.btn_bottom_sheet)
    Button bottomSheetBtn;
    @Bind(R.id.btn_bottom_dialog)
    Button bottomSheetDialogBtn;

    private BottomSheetBehavior behavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        bottomSheetBtn.setOnClickListener(this);
        bottomSheetDialogBtn.setOnClickListener(this);

        behavior = BottomSheetBehavior.from(findViewById(R.id.nestedScrollView));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bottom_sheet:
                if(behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;
            case R.id.btn_bottom_dialog:
                showBottomSheetDialog();
                break;
            default:
                break;
        }
    }

    private void showBottomSheetDialog(){
        View v =  LayoutInflater.from(this).inflate(R.layout.list,null);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(recyclerview);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(v);
        dialog.show();
        recyclerViewAdapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this,"click position:"+position,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

}
