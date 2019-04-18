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

    /*
    Variables to perform the swipe
     */
    public static final int SWIPE_THRESHOLD = 100;
    private float mDownY;
    private float mUpY;
    /*
    To play music note
     */
    private MusicSound mMusicSound;
    /*
    To know from where the funtion update is launch
     */
    public static final int ONCREATE = 0;
    public static final int CLICKHISTORY = 1;
    public static final int ONTOUCH = 2;
    /*
    to manage the rotation of the screen
     */
    private static final String BUNDLE_STATE_MOOD = "BUNDLE_MOOD";
    private boolean mNotRotated;
    /*
    To manage SharedPreferences
     */
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

        if (savedInstanceState == null) {
            mCurrentMood = Mood.HAPPY;
            mNotRotated = true;
        } else {
            mCurrentMood = Mood.valueOf(savedInstanceState.getString(BUNDLE_STATE_MOOD));
            mNotRotated = false;
        }

        mMusicSound = new MusicSound();
        mMusicSound.loadNotes(this);

        mDataManager = new DataManager(PreferenceManager.getDefaultSharedPreferences(this));

        updateMood(ONCREATE);
    }

    /*
    displayMood is in onStart method because if the user comme back to the main activity from the
    historyActivity, the method onCreated is not recall, but the method onStart is recall.
     */
    @Override
    protected void onStart() {
        displayMood();
        super.onStart();
    }

    /*
    To manage the rotation of the tablet
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(BUNDLE_STATE_MOOD, mCurrentMood.toString());
        super.onSaveInstanceState(outState);
    }

    /*
    The function that allows the user to add a comment about the current mood.
     */
    public void clickAddComment(View view) {
        final EditText comentText = new EditText(this);
        AlertDialog.Builder builderComment = new AlertDialog.Builder(this);
        builderComment.setTitle(R.string.comment)
                .setView(comentText)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDataManager.putComment(CURRENTCOMMENT, comentText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setCancelable(false)
                .create()
                .show();
    }

    public void clickHistory(View view) {
        updateMood(CLICKHISTORY);
    }

    /*
    This function determine the swipe behavior of the application.
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                updateMood(ONTOUCH);
                mUpY = event.getY();
                final float deltaY = mDownY - mUpY;
                if (deltaY > SWIPE_THRESHOLD) {
                    if (!mCurrentMood.equals(Mood.SUPER_HAPPY)) {
                        mCurrentMood = Mood.valueOf(mCurrentMood.next());
                        mDataManager.putMood(CURRENTMOOD, mCurrentMood);
                        mDataManager.clearString(CURRENTCOMMENT);
                        mMusicSound.playNote(mCurrentMood);
                        displayMood();
                    }
                }
                if (-deltaY > SWIPE_THRESHOLD) {
                    if (!mCurrentMood.equals(Mood.SAD)) {
                        mCurrentMood = Mood.valueOf(mCurrentMood.prev());
                        mDataManager.putMood(CURRENTMOOD, mCurrentMood);
                        mDataManager.clearString(CURRENTCOMMENT);
                        mMusicSound.playNote(mCurrentMood);
                        displayMood();
                    }
                }
                return true;
            default:
                return false;
        }
    }

    /*
    updateMood is the function that check if the date has changed.
    if it is the case, updateMood call saveMood that "shift" the history and
    save the new mood. And do nothing (almost) if the date has'nt changed.
    updateMood should be called evry time that the user interract with the application i.e :
    -in the method onCreate when the user launch the application. (to see if a new day has began)
    -in the method onClickHistory when the user want to display the histrory (to see
            if it should be update before to be display)
    -in the method onTouch (to see if the new mood that will be selected is
            the first of a new day or not)
     So to know which method called updateMood, this method take an integer in parameter
     that determine the parent method.

     if updateMood come from onCreate and delay is not null that means this is the first time of
     the day that the application has been created, so we have two cases :
     -first cases mNotRotate is true :that mean that the application hasn't been crated by a rotation
     of the tablet, so the application has been launch for the first time of the day so we define
     by default the current mood to HAPPY.
     -second case, the application has been created by a rotation of the tablet, that means that the
     user has already selected a mood so we do nothing.

     Now if updateMood come from onCreate and the date has no change, we simply get the last mood
     that the user has selected during the journey.

     If updateMood come from clickHistory then we should to save the current mood if the day has
     changed before start the historyActivity.
     See the comment about saveMood to the explanation of the boolean starthistory.
     */
    public void updateMood(int callFrom) {
        DayDate newDate = new DayDate();
        DayDate saveDate = new DayDate();

        String stringDate = mDataManager.getString(SAVEDATE, "");
        if (!stringDate.isEmpty()) saveDate.parseDayDate(stringDate);
        mDataManager.putDayDate(SAVEDATE, newDate);

        int delay = saveDate.between(newDate);
        boolean startHistory = saveMood(delay, (callFrom == CLICKHISTORY));
        if (startHistory) StartHistoryActivity();

        if (callFrom == ONCREATE) {
            if (delay != 0) {
                if (mNotRotated) {
                    mCurrentMood = Mood.HAPPY;
                    mDataManager.putMood(CURRENTMOOD, mCurrentMood);
                }
            } else {
                String currenrMood = mDataManager.getString(CURRENTMOOD, "");
                if (!currenrMood.isEmpty()) mCurrentMood = Mood.valueOf(currenrMood);
            }
        }
    }

    /*
    saveMood is the method that "shift" the history and save the currentMood.
    this method use the method shiftList of DataManager.
    If the delay is a negative number (i.e. shiftList return true) then saveMood display a warning
    message to prevent of the risque to change manually the date of the tablet.
    if this warning message appear after a click on the buttonn clickHistory then the boolean
    historyClick will have the value true and hence, the historyActivity will start only when the
    user will click on the button of the dialog box.

    If the delay is a positive number then we return the boolean historyClick
    (i.e (callFrom == CLICKHISTORY) in updateMood. So if updateMood is call from clickHistory
    then the historyActivity will be start.

    Without this boolean trick, in the case where the AlertDialog appear, the historyActivity
    start and the user has no time to read the warning message.
     */
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
            if (delay != 0) mDataManager.clearString(CURRENTCOMMENT);
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
