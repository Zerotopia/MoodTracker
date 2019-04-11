package com.example.moodtracker.model;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.moodtracker.R;

public class MusicSound {

    private SoundPool mSoundPool;
    public static final int[] NOTES = {R.raw.e, R.raw.f, R.raw.a, R.raw.b, R.raw.c};
    private int[] sound = {0, 0, 0, 0, 0};

    public MusicSound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes attrs = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();

            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(attrs)
                    .build();
        } else {
            mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
    }

    public void loadNotes (Context context) {
        for (int i = 0; i < sound.length; i++) {
            sound[i] = mSoundPool.load(context, NOTES[i], 1);
        }
    }

    public void playNote(Mood mood) {
        mSoundPool.play(sound[mood.sound()], 1, 1, 0, 0, 1);
    }
}
