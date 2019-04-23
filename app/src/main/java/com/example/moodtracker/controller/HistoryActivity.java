package com.example.moodtracker.controller;

import android.graphics.Color;
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
import com.marcoscg.materialtoast.MaterialToast;

import java.util.ArrayList;
import java.util.List;

/**
 * The only things that do HistoryActivity is to collect information
 * in SharedPreferences of user and display it.
 */
public class HistoryActivity extends AppCompatActivity {

    private TableLayout mTableLayout;
    private DataManager mDataManager;

    /**
     * mMoodList is the list of the Moods of the week, and
     * mCommentList is the list of the comments of the week
     */
    private List<Integer> mMoodList = new ArrayList<>();
    private List<String> mCommentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mTableLayout = (TableLayout) findViewById(R.id.activity_history_table_layout);
        mDataManager = new DataManager(PreferenceManager.getDefaultSharedPreferences(this));

        for (int i = 0; i < MainActivity.DAYS.length; i++)
            mMoodList.add(mDataManager.getInt(MainActivity.DAYS[i], -1));
        for (int i = 0; i < MainActivity.COMMENTS.length; i++)
            mCommentList.add(mDataManager.getString(MainActivity.COMMENTS[i], ""));

        displayTableLayout();
    }

    /**
     * for (each mood in mMoodList) {
     * We inflate history_row.
     * We fixed the weight of the height of each row to 1 to have all rows with the same height.
     * We fixed the weightSum of each row to the number of Mood to have a different width
     * for each Mood that will be determined by the Mood.weight function.
     * We fixed the width of the row according to its weight
     * We fixed the background color of the row
     * We set the text of the row like "yesterday", or there is two day", etc
     * We call the displayComment fucntion that manage the different comments of the week.
     * Finally we add the row to our tablelayout
     * }
     */
    private void displayTableLayout() {
        LayoutInflater inflater = this.getLayoutInflater();

        for (int rowNum = 0; rowNum < mMoodList.size(); rowNum++) {

            TableRow row = (TableRow) inflater.inflate(R.layout.history_row, mTableLayout, false);
            TableLayout.LayoutParams rowLayoutParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1.0f);
            row.setLayoutParams(rowLayoutParams);
            row.setWeightSum(weightMax());

            LinearLayout rowLinearLayout = row.findViewById(R.id.history_row_linear_layout);
            TextView date = (TextView) row.findViewById(R.id.history_row_txt);

            TableRow.LayoutParams rowLinearLayoutParams = new TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.MATCH_PARENT,
                    rowWeight(rowNum));
            rowLinearLayout.setLayoutParams(rowLinearLayoutParams);
            rowLinearLayout.setBackgroundResource(rowBackgroundColor(rowNum));

            date.setText(rowText(rowNum));

            ImageView rowComment = (ImageView) row.findViewById(R.id.history_row_png);
            displayRowComment(rowNum, rowComment, rowLinearLayout);

            mTableLayout.addView(row);
        }
    }

    /**
     * The width of a row is determine by a weight/weightSum systèm.
     * The system is disigned to the minimum width of sad mood is at least 1/5 of the
     * screen width.
     * If  1 <= moodNumber <= 5 then  different rate of width are 1/moodNumber, 2/moodNumber, ... moodNumber/moodNumber = 1.
     * If 6 <= moodNumber <= 10 then different rate of width are 2/(moodNumber+1), 3/(moodNumber+1), ... (moodNumber+1)/(moodNumber+1) = 1
     * If 11 <= moodNumber <= 15 then different rate of width are 3/(moodNumber+2), 4/(moodNumber+2), ... (moodNumber+2)/(moodNumber+2) = 1
     *
     * So we see that for each slice of 5 number, weightMax is increment by 1 (moodNumber, moodNumber+1, moodNumber+2, ...)
     * it is exactly the behavior of the number (moodNumber - 1)/5
     */
    private float weightMax () {
        int moodNumber = Mood.values().length;
        return (moodNumber + (moodNumber - 1) / 5);
    }
    /**
     * Just for the sake of readability. If one day the user not used the application,
     * mMoodList contains the value -1, hence a value that is different of -1 means that
     * a mood has been saved and so exists.
     */
    private boolean moodExists(int moodOrdinal) {
        return moodOrdinal != -1;
    }

    /**
     * The following functions determine respectivly the backgroundcolor, the weight and the Text
     * of each row.
     */
    private int rowBackgroundColor(int rowNum) {
        int mood = mMoodList.get(rowNum);
        if (moodExists(mood)) return Mood.values()[mood].color();
        else return R.color.default_color;
    }

    private float rowWeight(int rowNum) {
        int mood = mMoodList.get(rowNum);
        if (moodExists(mood)) return Mood.values()[mood].weight();
        else return Mood.values().length;
    }

    private String rowText(int rowNum) {
        int mood = mMoodList.get(rowNum);
        if (moodExists(mood)) return getResources().getStringArray(R.array.day_sentences)[rowNum];
        else return getResources().getString(R.string.noMood);
    }

    /**
     * This function add an icon to inform the user that a comment is registered for this day.
     * Click on the row display the comment in a Toast.
     *
     */
    private void displayRowComment(int rowNum, ImageView rowComment, LinearLayout rowLinearLayout) {

        final String comment = mCommentList.get(rowNum);

        if (!comment.isEmpty()) {
            rowComment.setImageResource(R.mipmap.ic_comment_black_48px);
            rowLinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new MaterialToast(HistoryActivity.this)
                            .setMessage(comment)
                            .setDuration(Toast.LENGTH_LONG)
                            .setBackgroundColor(Color.BLACK)
                            .show();
                }
            });
        }
    }
}