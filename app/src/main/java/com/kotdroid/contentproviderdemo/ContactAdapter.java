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
 * Created by user12 on 26/3/18.
 */

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    List<Contact> contactList;
    LayoutInflater mLayoutInflater;

    public ContactAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        contactList = new ArrayList<>();
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ContactHolder(mLayoutInflater.inflate(R.layout.contact_row, parent, false));
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ContactHolder contactHolder = (ContactHolder) holder;
        contactHolder.tvName.setText(contactList.get(position).name);
        contactHolder.tvNumber.setText(contactList.get(position).number);
        contactHolder.tvId.setText(contactList.get(position).id);
    }

    @Override public int getItemCount() {
        return contactList.size();
    }

    public void updateList(List<Contact> list) {
        if (0 < list.size()) {
            contactList.clear();
            contactList.addAll(list);
        }
    }

    class ContactHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvNumber, tvId;

        public ContactHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvId = itemView.findViewById(R.id.tvID);
        }
    }
}
