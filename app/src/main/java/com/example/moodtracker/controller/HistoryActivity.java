package com.example.moodtracker.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.example.moodtracker.R;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        historyRecyclerView = (RecyclerView) findViewById(R.id.activity_history_recycler_view);

    }
}
