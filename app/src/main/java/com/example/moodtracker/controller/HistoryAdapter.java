package com.example.moodtracker.controller;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moodtracker.R;
import com.example.moodtracker.model.Mood;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<Mood> moodItems;

    public HistoryAdapter(List<Mood> moodItems) {
        this.moodItems = moodItems;
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder {

        TextView date;
        ImageView note;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.history_row_txt);
            note = (ImageView) itemView.findViewById(R.id.history_row_png);
        }
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_row, viewGroup, false);
        view.setBackgroundResource(moodItems.get(i).Color());
        view.getLayoutParams().width = (int) (Resources.getSystem().getDisplayMetrics().widthPixels * moodItems.get(i).Scale());
        return new HistoryViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        Resources res = historyViewHolder.itemView.getResources();
        historyViewHolder.date.setText(res.getStringArray(R.array.day_sentences)[i]);
        if (moodItems.get(i).getNote() != null)
            historyViewHolder.note.setImageResource(R.mipmap.ic_comment_black_48px);

    }

    @Override
    public int getItemCount() {
        return moodItems.size();
    }


}
