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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.moodtracker.R;
import com.example.moodtracker.model.Mood;



public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private ImageView mImageMood;
    private RelativeLayout mRelativeLayout;
    private Mood mCurrentMood;

    public static final int SWIPE_THRESHOLD = 100;
    private float downY;
    private float upY;

    private String mComment = "";

    //We use SharedPreferences variable for history activity
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditPreferences;

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
        mEditPreferences = mPreferences.edit();


        mEditPreferences.putString(DAYS[0], Mood.SUPER_HAPPY.toString());
        mEditPreferences.putString(DAYS[1], Mood.NORMAL.toString());
        mEditPreferences.putString(DAYS[2], Mood.HAPPY.toString());
        mEditPreferences.putString(DAYS[3], Mood.SUPER_HAPPY.toString());
        mEditPreferences.putString(DAYS[4], Mood.DISAPPOINTED.toString());
        mEditPreferences.putString(DAYS[5], Mood.SAD.toString());
        mEditPreferences.putString(DAYS[6], Mood.HAPPY.toString());

        mEditPreferences.putString(NOTE[1], "Hmmm, pourquoi pas");
        mEditPreferences.putString(NOTE[3], "Hmmm, Oichi, delicieuse ramen");
        mEditPreferences.putString(NOTE[5], "Super Pizza, super pizza cette pizza à la sauce tomate qui n'a jamais peur de rien.");
        mEditPreferences.apply();
    }


    public void clickAddNote(View view) {
        final EditText comentText = new EditText(this);
        AlertDialog.Builder builderNote = new AlertDialog.Builder(this);
        builderNote.setTitle("Note")
                .setView(comentText)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mComment = comentText.getText().toString();
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
                    mComment = "";
                }
                if (-deltaY > SWIPE_THRESHOLD) {
                    mCurrentMood = Mood.valueOf(mCurrentMood.Prev());
                    displayMood();
                    mComment = "";
                }
                return true;
            default:
                return false;
        }
    }

    public void saveMood(int delay) {
        if (delay < 0) {
            AlertDialog.Builder warningDate = new AlertDialog.Builder(this);
            warningDate.setTitle("Avertissement")
                    .setMessage("Attention chagement de date manuelle")
                    .setPositiveButton("Ok",null)
                            //new DialogInterface.OnClickListener() {
                        //@Override
                        //public void onClick(DialogInterface dialog, int which) {
                       //
                       // }
                    //})
                    .create()
                    .show();
        } else if (0 < delay && delay <= DAYS.length) {
            for (int i = 0; i < DAYS.length - delay; i++) {
                mEditPreferences.putString(DAYS[i], mPreferences.getString(DAYS[i + delay], "")).apply();
                mEditPreferences.remove(DAYS[i + delay]);
                mEditPreferences.putString(NOTE[i], mPreferences.getString(NOTE[i + delay], "")).apply();
                mEditPreferences.remove(NOTE[i + delay]);
            }

            mEditPreferences.putString(DAYS[DAYS.length - delay], mCurrentMood.toString()).apply();
            mEditPreferences.putString(NOTE[DAYS.length - delay], mComment).apply();

        } else if (delay > DAYS.length) {
            for (int i = 0; i < DAYS.length; i++) {
                mEditPreferences.remove(DAYS[i]);
                mEditPreferences.remove(NOTE[i]);
            }
        }
    }

    public void displayMood() {
        mRelativeLayout.setBackgroundResource(mCurrentMood.Color());
        mImageMood.setImageResource(mCurrentMood.Smiley());
    }
}
