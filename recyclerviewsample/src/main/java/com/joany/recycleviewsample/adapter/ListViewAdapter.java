package com.joany.recycleviewsample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joany.recycleviewsample.R;

import java.util.List;

/**
 * Created by joany on 2016/8/10.
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ListViewHolder>{

    private List<String> dataList;
    private Context context;

    public ListViewAdapter(Context context,List<String> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListViewHolder holder = new ListViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ListViewHolder holder, int position) {
        holder.listItemTv.setText(dataList.get(position));

        if(onItemClickListener != null) {
            holder.listItemTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.listItemTv,pos);
                }
            });
            holder.listItemTv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.listItemTv,pos);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder{
        TextView listItemTv;

        public ListViewHolder(View itemView) {
            super(itemView);
            listItemTv = (TextView) itemView.findViewById(R.id.listItemTv);
        }
    }
}