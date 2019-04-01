package com.example.moodtracker.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.moodtracker.R;
import com.example.moodtracker.model.Mood;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView mImageMood;
    private RelativeLayout mRelativeLayout;
    private Mood mCurrentMood;

    public static final int SWIPE_THRESHOLD = 100;
    private float downY;
    private float upY;

    //We use SharedPreferences variable for history activity
    private SharedPreferences mPreferences;


    public static final String[] DAYS = {"DAY0", "DAY1", "DAY2", "DAY3", "DAY4", "DAY5", "DAY6"};
    public static final String[] NOTE = {"NOTE0", "NOTE1", "NOTE2", "NOTE3", "NOTE4", "NOTE5", "NOTE6"};

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


        mPreferences.edit().putString(DAYS[0], Mood.SUPER_HAPPY.toString()).apply();
        mPreferences.edit().putString(DAYS[1], Mood.NORMAL.toString()).apply();
        mPreferences.edit().putString(DAYS[2], Mood.HAPPY.toString()).apply();
        mPreferences.edit().putString(DAYS[3], Mood.SUPER_HAPPY.toString()).apply();
        mPreferences.edit().putString(DAYS[4], Mood.DISAPPOINTED.toString()).apply();
        mPreferences.edit().putString(DAYS[5], Mood.SAD.toString()).apply();
        mPreferences.edit().putString(DAYS[6], Mood.HAPPY.toString()).apply();

        mPreferences.edit().putString(NOTE[1], "Hmmm, pourquoi pas").apply();
        mPreferences.edit().putString(NOTE[3], "Hmmm, Oichi, delicieuse ramen").apply();
        mPreferences.edit().putString(NOTE[5], "Super Pizza, super pizza cette pizza à la sauce tomate qui n'a jamais peur de rien.").apply();
    }


    public void clickAddNote(View view) {
        final EditText comentText = new EditText(this);
        AlertDialog.Builder builderNote = new AlertDialog.Builder(this);
        builderNote.setTitle("Note")
                .setView(comentText)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //System.out.println(comentText.getText());
                        // saved comment.
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void clickHistory(View view) {
        Intent historyActivity = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(historyActivity);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                upY = event.getY();
                final float deltaY = downY - upY;
                if (deltaY > SWIPE_THRESHOLD) {
                    mCurrentMood = Mood.valueOf(mCurrentMood.Next());
                    displayMood();
                }
                if (-deltaY > SWIPE_THRESHOLD) {
                    mCurrentMood = Mood.valueOf(mCurrentMood.Prev());
                    displayMood();
                }
                return true;
            default:
                return true;
        }
    }

    public void displayMood() {
        mRelativeLayout.setBackgroundResource(mCurrentMood.Color());
        mImageMood.setImageResource(mCurrentMood.Smiley());
    }
}
