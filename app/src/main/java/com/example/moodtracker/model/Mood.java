package com.example.moodtracker.model;

import android.support.annotation.IntDef;

import com.example.moodtracker.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Mood {

    public final int mIdMood;
    private String mNote = null;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SAD, DISAPPOINTED, NORMAL, HAPPY, SUPER_HAPPY})
    public @interface moodDef {}

    public static final int SAD = 1;
    public static final int DISAPPOINTED = 2;
    public static final int NORMAL = 3;
    public static final int HAPPY = 4;
    public static final int SUPER_HAPPY = 5;

    public Mood (@moodDef int idMood) {
        mIdMood = idMood;
    }

    public int Smiley () {
        switch (mIdMood) {
            case SAD:
                return R.mipmap.smiley_sad;
            case DISAPPOINTED:
                return R.mipmap.smiley_disappointed;
            case NORMAL:
                return R.mipmap.smiley_normal;
            case HAPPY:
                return R.mipmap.smiley_happy;
            case SUPER_HAPPY:
                return R.mipmap.smiley_super_happy;
            default:
                return 0;

        }
    }

    public int Color () {
        switch (mIdMood) {
            case SAD:
                return R.color.faded_red;
            case DISAPPOINTED:
                return R.color.warm_grey;
            case NORMAL:
                return R.color.cornflower_blue_65;
            case HAPPY:
                return R.color.light_sage;
            case SUPER_HAPPY:
                return R.color.banana_yellow;
            default:
                return 0;
        }
    }

    public double Scale () {
        return mIdMood / 5.0;
    }
}
