package com.example.moodtracker.controller;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moodtracker.R;
import com.example.moodtracker.model.Mood;

import java.util.ArrayList;
import java.util.List;

/*
The only things that do HistoryActivity is to collect information
in SharedPreferences of user and display it.
 */

public class HistoryActivity extends AppCompatActivity {

    private TableLayout mTableLayout;
    private DataManager mDataManager;

    /*
    mMoodList is the list of the Moods of the week, and
    mCommentList is the list of the comments of the week
     */
    private List<Mood> mMoodList = new ArrayList<>();
    private List<String> mCommentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mTableLayout = (TableLayout) findViewById(R.id.activity_history_table_layout);
        mDataManager = new DataManager(PreferenceManager.getDefaultSharedPreferences(this));

        for (int i = 0; i < MainActivity.DAYS.length; i++)
            mMoodList.add(Mood.EMPTY_MOOD.parseMood(mDataManager.getString(MainActivity.DAYS[i],
                    "")));
        for (int i = 0; i < MainActivity.COMMENTS.length; i++)
            mCommentList.add(mDataManager.getString(MainActivity.COMMENTS[i], ""));

        displayTableLayout();
    }

    /*
    for (each mood in mMoodList) {
      We inflate history_row.
      We fixed the weight of the height of each row to 1 to have all rows with the same height.
      We fixed the weightSum of each row to 5 to have a different width for each Mood that will be
      determined by the Mood.weight function.
      We fixed background color of the row (determined by the Mood.color function)
      if we have a non empty mood, we set text like "yesterday", or there is two day", etc
          furthermore if we have acomment, we add the comment icon, and display a toast for the comment
          (if user click on the row).
      else we set text that there is no mood saved for thi day.
      finally we add the row to our tablelayout
      }
     */
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
            TextView date = (TextView) row.findViewById(R.id.history_row_txt);

            TableRow.LayoutParams rowLinearLayoutParams = new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    mood.weight());
            rowLinearLayout.setLayoutParams(rowLinearLayoutParams);
            rowLinearLayout.setBackgroundResource(mood.color());

            if (!mood.toString().isEmpty()) {
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
            } else date.setText(R.string.noMood);
            mTableLayout.addView(row);
        }
    }
}