package com.example.moodtracker.controller;

import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private TableLayout mTableLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mTableLayout = (TableLayout) findViewById(R.id.activity_history_table_layout);
        displayTableLayout();

    }

    private void displayTableLayout() {

        LayoutInflater inflater = this.getLayoutInflater();
        final List<Mood> moodList = moodItems();

        for (int i = 0; i < moodList.size(); i++) {

            Mood mood = moodList.get(i);

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

            final String comment = mood.getNote();

            if (comment != null) {
                ImageView note = (ImageView) row.findViewById(R.id.history_row_png);
                note.setImageResource(R.mipmap.ic_comment_black_48px);
                rowLinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HistoryActivity.this, comment, Toast.LENGTH_LONG).show();
                    }
                });
            }

            //row.addView(rowLayout);
            mTableLayout.addView(row);
        }
    }

    private List<Mood> moodItems() {
        List<Mood> l = new ArrayList<Mood>();
        l.add(new Mood(Mood.SUPER_HAPPY));
        l.add(new Mood(Mood.NORMAL, "comment1"));
        l.add(new Mood(Mood.HAPPY));
        l.add(new Mood(Mood.SUPER_HAPPY, "comment2"));
        l.add(new Mood(Mood.DISAPPOINTED));
        l.add(new Mood(Mood.SAD, "Il était une fois dans un cimetière lointain un grand chateau fort."));
        l.add(new Mood(Mood.HAPPY));
        return l;
    }
}
