package com.example.moodtracker.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.moodtracker.R;
import com.example.moodtracker.model.DayDate;
import com.example.moodtracker.model.Mood;
import com.example.moodtracker.model.MusicSound;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView mImageMood;
    private RelativeLayout mRelativeLayout;
    private Mood mCurrentMood;

    public static final int SWIPE_THRESHOLD = 100;
    private float mDownY;
    private float mUpY;

    private MusicSound mMusicSound;
   // private int[] sound = {0, 0, 0, 0, 0};

    //We use SharedPreferences variable for history activity
 //   private SharedPreferences mPreferences;
    //private SharedPreferences.Editor mEditPreferences;

    private DataManager mDataManager;

    public static final String CURRENTMOOD = "MOOD";
    public static final String CURRENTCOMMENT = "COMMENT";
    public static final String[] DAYS = {"DAY0", "DAY1", "DAY2", "DAY3", "DAY4", "DAY5", "DAY6"};
    public static final String[] COMMENTS = {"COMMENT0", "COMMENT1", "COMMENT2", "COMMENT3", "COMMENT4", "COMMENT5", "COMMENT6"};
    public static final String SAVEDATE = "DATE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageMood = (ImageView) findViewById(R.id.activity_main_mood_imgw);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.activity_main_layout);
        mRelativeLayout.setOnTouchListener(this);
        mCurrentMood = Mood.HAPPY;
        displayMood();

        mDataManager = new DataManager(PreferenceManager.getDefaultSharedPreferences(this));
        //mPreferences = mDataManager.getPreferences();

        mMusicSound = new MusicSound();
        mMusicSound.loadNotes(this);

        updateMood(false);

        mDataManager.putMood(CURRENTMOOD,mCurrentMood);


    }

    public void clickAddComment(View view) {
        final EditText comentText = new EditText(this);
        AlertDialog.Builder builderComment = new AlertDialog.Builder(this);
        builderComment.setTitle(R.string.comment)
                .setView(comentText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDataManager.putComment(CURRENTCOMMENT,comentText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .create()
                .show();
    }

    public void clickHistory(View view) {
        updateMood(true);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                updateMood(false);
                mUpY = event.getY();
                final float deltaY = mDownY - mUpY;
                if (deltaY > SWIPE_THRESHOLD) {
                    mCurrentMood = Mood.valueOf(mCurrentMood.next());
                    mDataManager.putMood(CURRENTMOOD,mCurrentMood);
                    mDataManager.putComment(CURRENTCOMMENT,"");
                    mMusicSound.playNote(mCurrentMood);
                    displayMood();
                }
                if (-deltaY > SWIPE_THRESHOLD) {
                    mCurrentMood = Mood.valueOf(mCurrentMood.prev());
                    mDataManager.putMood(CURRENTMOOD,mCurrentMood);
                    mDataManager.putComment(CURRENTCOMMENT,"");
                    mMusicSound.playNote(mCurrentMood);
                    displayMood();
                }
                return true;
            default:
                return false;
        }
    }

    public void updateMood(boolean historyClick) {
        DayDate newDate = new DayDate();
        DayDate saveDate = new DayDate();

        String stringDate = mDataManager.getString(SAVEDATE, "");
        if (!stringDate.isEmpty()) saveDate.parseDayDate(stringDate);
        mDataManager.putDayDate(SAVEDATE,newDate);

        boolean startHistory = saveMood(saveDate.Between(newDate), historyClick);
        if (startHistory) StartHistoryActivity();
    }

    public boolean saveMood(int delay, final boolean historyClick) {
        if (mDataManager.shiftList(DAYS, delay) || mDataManager.shiftList(COMMENTS, delay)) {
            AlertDialog.Builder warningDate = new AlertDialog.Builder(this);
            warningDate.setTitle(R.string.warning)
                    .setMessage(R.string.warningDate)
                    .setNegativeButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (historyClick) StartHistoryActivity();
                                }
                            })
                    .create()
                    .show();
            return false;
        } else {
            int position = DAYS.length - delay;
            mDataManager.copyInList(DAYS, CURRENTMOOD, position);
            mDataManager.copyInList(COMMENTS, CURRENTCOMMENT, position);
            mDataManager.putComment(CURRENTCOMMENT,"");
        }
        return historyClick;
    }

    private void StartHistoryActivity() {
        Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(historyActivity);
    }

    public void displayMood() {
        mRelativeLayout.setBackgroundResource(mCurrentMood.color());
        mImageMood.setImageResource(mCurrentMood.smiley());
    }
}
