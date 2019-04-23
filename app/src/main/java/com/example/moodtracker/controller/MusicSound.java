package com.example.moodtracker.controller;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import com.example.moodtracker.model.Mood;

/**
 * SoundPool allows android to play short music file.
 * It is use here to play the note for each mood when the user swipe.
 */
public class MusicSound {

    private SoundPool mSoundPool;
    private int[] sound = {0, 0, 0, 0, 0};

    /**
     * new SoundPool(...) is deprecated since API 21.
     * The application should be work for API19 and 20 so
     * the constructor MusicSound test the version to determined the good way
     * to construct the SoundPool.
     */
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

    public void loadNotes(Context context) {
        for (int i = 0; i < sound.length; i++) {
            sound[i] = mSoundPool.load(context, Mood.NOTES[i], 1);
        }
    }

    public void playNote(Mood mood) {
        mSoundPool.play(sound[mood.ordinal()], 1, 1, 0, 0, 1);
    }
}
