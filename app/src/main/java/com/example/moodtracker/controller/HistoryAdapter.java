package com.example.moodtracker.controller;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moodtracker.R;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class  HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        ImageView note;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.history_row_txt);
            note = (ImageView) itemView.findViewById(R.id.history_row_png);
        }
    }
}
