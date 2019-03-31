package com.example.moodtracker.controller;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moodtracker.R;
import com.example.moodtracker.model.Mood;

import org.w3c.dom.Text;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private TableLayout mTableLayout;
    private SharedPreferences mPreferences;

    private List<Mood> mMoodList = new ArrayList<>();
    private List<String> mCommentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mTableLayout = (TableLayout) findViewById(R.id.activity_history_table_layout);
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        for (int i = 0; i < MainActivity.DAYS.length; i++)
            mMoodList.add(Mood.MOOD_OBJECT.parseMood(mPreferences.getString(MainActivity.DAYS[i],
                                                     "")));
        for (int i = 0; i < MainActivity.NOTE.length; i++)
            mCommentList.add(mPreferences.getString(MainActivity.NOTE[i], ""));

        displayTableLayout();
    }

    private void displayTableLayout() {

        LayoutInflater inflater = this.getLayoutInflater();

        for (int i = 0; i < mMoodList.size(); i++) {
            Mood mood = mMoodList.get(i);

            TableRow row = (TableRow) inflater.inflate(R.layout.history_row, mTableLayout, false);
            TableLayout.LayoutParams rowLayoutParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1.0f);
            row.setLayoutParams(rowLayoutParams);
            row.setWeightSum(5);

            LinearLayout rowLinearLayout = row.findViewById(R.id.history_row_linear_layout);
            TableRow.LayoutParams rowLinearLayoutParams = new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    mood.Weight());
            rowLinearLayout.setLayoutParams(rowLinearLayoutParams);
            rowLinearLayout.setBackgroundResource(mood.Color());

            TextView date = (TextView) row.findViewById(R.id.history_row_txt);
            date.setText(getResources().getStringArray(R.array.day_sentences)[i]);

            final String comment = mCommentList.get(i);

            if (!comment.isEmpty()) {
                ImageView note = (ImageView) row.findViewById(R.id.history_row_png);
                note.setImageResource(R.mipmap.ic_comment_black_48px);
                rowLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HistoryActivity.this, comment, Toast.LENGTH_LONG).show();
                    }
                });
            }
            mTableLayout.addView(row);
        }
    }
}