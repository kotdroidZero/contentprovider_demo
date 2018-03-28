package com.kotdroid.contentproviderdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user12 on 28/3/18.
 */

public class DataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Data> dataList;
    LayoutInflater mLayoutInflater;

    public DataAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        dataList = new ArrayList<>();
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DataHolder(mLayoutInflater.inflate(R.layout.contact_row, parent, false));
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DataHolder dataHolder = (DataHolder) holder;
        dataHolder.tvName.setText(dataList.get(position).getName());
        dataHolder.tvAge.setText(dataList.get(position).getAge());
        dataHolder.tvId.setText(dataList.get(position).getId());
    }

    @Override public int getItemCount() {
        return dataList.size();
    }

    class DataHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAge, tvId;

        public DataHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvAge = itemView.findViewById(R.id.tvNumber);
            tvId = itemView.findViewById(R.id.tvID);
        }
    }

    public void updateList(List<Data> list) {
        if (0 < list.size()) {
            dataList.clear();
            dataList.addAll(list);
        }
    }
}
