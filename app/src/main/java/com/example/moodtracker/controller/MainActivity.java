package com.example.moodtracker.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
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




public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView mImageMood;
    private RelativeLayout mRelativeLayout;
    private Mood mCurrentMood;

    public static final int SWIPE_THRESHOLD = 100;
    private float mDownY;
    private float mUpY;

    private String mComment = "";

    private SoundPool mSoubPool;
    private int[] sound = {0, 0, 0, 0, 0};

    //We use SharedPreferences variable for history activity
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditPreferences;

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

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditPreferences = mPreferences.edit();



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attrs = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            mSoubPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(attrs)
                    .build();
        }

        else {
            mSoubPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }

        sound[0] = mSoubPool.load(this, R.raw.e,1);
        sound[1] = mSoubPool.load(this, R.raw.f,1);
        sound[2] = mSoubPool.load(this, R.raw.b,1);
        sound[3] = mSoubPool.load(this, R.raw.a,1);
        sound[4] = mSoubPool.load(this, R.raw.c,1);

        //sound =

            if (mPreferences.getString(CURRENTMOOD, "").isEmpty())
            mEditPreferences.putString(CURRENTMOOD, mCurrentMood.toString()).apply();


        updateMood(false);
    }

    public void clickAddComment(View view) {
        final EditText comentText = new EditText(this);
        AlertDialog.Builder builderComment = new AlertDialog.Builder(this);
        builderComment.setTitle(R.string.comment)
                .setView(comentText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mComment = comentText.getText().toString();
                        mEditPreferences.putString(CURRENTCOMMENT, mComment).apply();
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
                    mCurrentMood = Mood.valueOf(mCurrentMood.Next());
                    mEditPreferences.putString(CURRENTMOOD, mCurrentMood.toString()).apply();



                    mSoubPool.play(sound[mCurrentMood.Sound()],1,1,0,0,1);

                    displayMood();
                    mComment = "";
                }
                if (-deltaY > SWIPE_THRESHOLD) {
                    mCurrentMood = Mood.valueOf(mCurrentMood.Prev());
                    mEditPreferences.putString(CURRENTMOOD, mCurrentMood.toString()).apply();





                    mSoubPool.play(sound[mCurrentMood.Sound()],1,1,0,0,1);
                    displayMood();
                    mComment = "";
                }
                return true;
            default:
                return false;
        }
    }

    public boolean saveMood(int delay, final boolean historyClick) {
        if (delay < 0) {
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
        } else if (0 < delay && delay <= DAYS.length) {
            for (int i = 0; i < DAYS.length - delay; i++) {
                mEditPreferences.putString(DAYS[i], mPreferences.getString(DAYS[i + delay], "")).apply();
                mEditPreferences.remove(DAYS[i + delay]);
                mEditPreferences.putString(COMMENTS[i], mPreferences.getString(COMMENTS[i + delay], "")).apply();
                mEditPreferences.remove(COMMENTS[i + delay]);
            }

            mEditPreferences.putString(DAYS[DAYS.length - delay], mPreferences.getString(CURRENTMOOD, "")).apply();
            mEditPreferences.putString(COMMENTS[DAYS.length - delay], mPreferences.getString(CURRENTCOMMENT, "")).apply();

        } else if (delay > DAYS.length) {
            for (int i = 0; i < DAYS.length; i++) {
                mEditPreferences.remove(DAYS[i]);
                mEditPreferences.remove(COMMENTS[i]);
            }
        }
        return historyClick;
    }

    private void StartHistoryActivity() {
        Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(historyActivity);
    }

    public void updateMood(boolean historyClick) {
        DayDate newDate = new DayDate();
        DayDate saveDate = new DayDate();

        String stringDate = mPreferences.getString(SAVEDATE, "");
        if (!stringDate.isEmpty()) saveDate.parseDayDate(stringDate);
        mEditPreferences.putString(SAVEDATE, newDate.toString()).apply();

        boolean startHistory = saveMood(saveDate.Between(newDate), historyClick);
        if (startHistory) StartHistoryActivity();
    }

    public void displayMood() {
        mRelativeLayout.setBackgroundResource(mCurrentMood.Color());
        mImageMood.setImageResource(mCurrentMood.Smiley());
    }
}
