package com.example.hackernewsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RVadapter extends RecyclerView.Adapter<RVadapter.MyViewHolder> {

    private ArrayList<String> data;

    public RVadapter(ArrayList<String> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v = layoutInflater.inflate(R.layout.rv_list_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String curTitle = this.data.get(position);
        holder.titleText.setText(curTitle);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public MyViewHolder(View v) {
            super(v);
            this.titleText = v.findViewById(R.id.title);
        }
    }
}
