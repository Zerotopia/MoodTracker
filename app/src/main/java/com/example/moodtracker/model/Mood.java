package com.example.moodtracker.model;

import com.example.moodtracker.R;

/*
This class contains all Mood of our application and
many funtions to link a mood to hits spécifique data,
like background color, smiley, music sound etc
 */

public enum Mood {

    SAD("SAD"),
    DISAPPOINTED("DISAPPOINTED"),
    NORMAL("NORMAL"),
    HAPPY("HAPPY"),
    SUPER_HAPPY("SUPER_HAPPY"),
    EMPTY_MOOD("");

    private String mIdMood;

    @Override
    public String toString() {
        return mIdMood;
    }

    Mood(String idMood) {
        mIdMood = idMood;
    }

    public Mood parseMood(String idMood) {
        if (idMood == null) return EMPTY_MOOD;
        else {
            switch (idMood) {
                case "SAD":
                    return SAD;
                case "DISAPPOINTED":
                    return DISAPPOINTED;
                case "NORMAL":
                    return NORMAL;
                case "HAPPY":
                    return HAPPY;
                case "SUPER_HAPPY":
                    return SUPER_HAPPY;
                default:
                    return EMPTY_MOOD;
            }
        }
    }

    public int smiley() {
        switch (mIdMood) {
            case "SAD":
                return R.mipmap.smiley_sad;
            case "DISAPPOINTED":
                return R.mipmap.smiley_disappointed;
            case "NORMAL":
                return R.mipmap.smiley_normal;
            case "HAPPY":
                return R.mipmap.smiley_happy;
            case "SUPER_HAPPY":
                return R.mipmap.smiley_super_happy;
            default:
                return 0;
        }
    }

    public int color() {
        switch (mIdMood) {
            case "SAD":
                return R.color.faded_red;
            case "DISAPPOINTED":
                return R.color.warm_grey;
            case "NORMAL":
                return R.color.cornflower_blue_65;
            case "HAPPY":
                return R.color.light_sage;
            case "SUPER_HAPPY":
                return R.color.banana_yellow;
            default:
                return R.color.default_color;
        }
    }

    /*
    weight is use to determine the larger of a "mood row" in
    the historyActivity
     */
    public float weight() {
        switch (mIdMood) {
            case "SAD":
                return 1;
            case "DISAPPOINTED":
                return 2;
            case "NORMAL":
                return 3;
            case "HAPPY":
                return 4;
            case "SUPER_HAPPY":
                return 5;
            default:
                return 5;
        }
    }

    /*
    prev and next function are used to determine
    the next or previous mood when the user swipe on the screen.
     */
    public String prev() {
        switch (mIdMood) {
            case "SAD":
                return "SAD";
            case "DISAPPOINTED":
                return "SAD";
            case "NORMAL":
                return "DISAPPOINTED";
            case "HAPPY":
                return "NORMAL";
            case "SUPER_HAPPY":
                return "HAPPY";
            default:
                return "";
        }
    }

    public String next() {
        switch (mIdMood) {
            case "SAD":
                return "DISAPPOINTED";
            case "DISAPPOINTED":
                return "NORMAL";
            case "NORMAL":
                return "HAPPY";
            case "HAPPY":
                return "SUPER_HAPPY";
            case "SUPER_HAPPY":
                return "SUPER_HAPPY";
            default:
                return "";
        }
    }

    /*
    The note of music are load in an array.
    So the sound function return the index of the
    corresponding note of the array for each mood.
     */
    public int sound() {
        switch (mIdMood) {
            case "SAD":
                return 0;
            case "DISAPPOINTED":
                return 1;
            case "NORMAL":
                return 2;
            case "HAPPY":
                return 3;
            case "SUPER_HAPPY":
                return 4;
            default:
                return 0;
        }
    }
}

