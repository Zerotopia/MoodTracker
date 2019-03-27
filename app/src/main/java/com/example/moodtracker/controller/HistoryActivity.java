package com.example.moodtracker.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.moodtracker.R;
import com.example.moodtracker.model.Mood;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyRecyclerView = (RecyclerView) findViewById(R.id.activity_history_recycler_view);

        mAdapter = new HistoryAdapter(moodItems());
        historyRecyclerView.setAdapter(mAdapter);


    }

    private List<Mood> moodItems () {
        List<Mood> l = new ArrayList<Mood>();
        l.add(new Mood(Mood.SUPER_HAPPY));
        l.add(new Mood(Mood.NORMAL,"comment1"));
        l.add(new Mood(Mood.HAPPY));
        l.add(new Mood(Mood.SUPER_HAPPY, "comment2"));
        l.add(new Mood(Mood.DISAPPOINTED));
        l.add(new Mood(Mood.SAD, "comment3"));
        l.add(new Mood(Mood.HAPPY));
        return l;
    }
}
