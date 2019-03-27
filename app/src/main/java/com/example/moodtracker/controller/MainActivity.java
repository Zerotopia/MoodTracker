package com.example.moodtracker.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.moodtracker.R;


public class MainActivity extends AppCompatActivity {

    private ImageView mImageMood;

    //We use SharedPreferences variable for history activity
    private SharedPreferences mPreferences;

    public static final String[] DAYS = {"DAY0","DAY1","DAY2","DAY3","DAY4","DAY5","DAY6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         mImageMood = (ImageView) findViewById(R.id.activity_main_mood_imgw);


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
        Intent historyActivity = new Intent(MainActivity.this,HistoryActivity.class);
        startActivity(historyActivity);

    }

}
