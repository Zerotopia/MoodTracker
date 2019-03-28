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

        List<Mood> MoodItem = moodItems();
        for (int i = 0; i < MoodItem.size(); i++) {
            Log.d("testfor","On entre dans la boucle for" + i);
            TableRow row = (TableRow) inflater.inflate(R.layout.history_row,null);
           TableLayout.LayoutParams tlp = new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                   TableLayout.LayoutParams.MATCH_PARENT,
                        1.0f);
          row.setLayoutParams(tlp);
          row.setWeightSum(5);

          LinearLayout L = row.findViewById(R.id.rowlyt);

            /*View rowLayout = inflater.inflate(R.layout.history_row, row,false); */
            TableRow.LayoutParams llp = new TableRow.LayoutParams(
                        0,
                        TableRow.LayoutParams.MATCH_PARENT,
                        MoodItem.get(i).Weight());
            L.setLayoutParams(llp);
            L.setBackgroundResource(MoodItem.get(i).Color());

            TextView date = (TextView) row.findViewById(R.id.history_row_txt);
            date.setText(getResources().getStringArray(R.array.day_sentences)[i]);

            if (MoodItem.get(i).getNote() != null) {
                ImageView note = (ImageView) row.findViewById(R.id.history_row_png);
                note.setImageResource(R.mipmap.ic_comment_black_48px);
            }

            //row.addView(rowLayout);
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
