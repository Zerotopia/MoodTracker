package com.example.moodtracker.controller;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

        for (int i = 0; i < moodItems().size(); i++) {

            TableRow row = new TableRow(this);
           /* TableRow.LayoutParams tlp = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        0,1.0f);
            row.setLayoutParams(tlp);
            */row.setWeightSum(5);

            LinearLayout rowLayout = (LinearLayout) inflater.inflate(R.layout.history_row, row,false);
          /*  LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,moodItems().get(i).Weight());
            //System.out.println(moodItems().get(i).Weight());
            rowLayout.setLayoutParams(llp);
            */rowLayout.setBackgroundResource(moodItems().get(i).Color());

            TextView date = (TextView) rowLayout.findViewById(R.id.history_row_txt);
            date.setText(getResources().getStringArray(R.array.day_sentences)[i]);

            if (moodItems().get(i).getNote() != null) {
                ImageView note = (ImageView) rowLayout.findViewById(R.id.history_row_png);
                note.setImageResource(R.mipmap.ic_comment_black_48px);
            }

            row.addView(rowLayout);
            mTableLayout.addView(row);
        }
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
