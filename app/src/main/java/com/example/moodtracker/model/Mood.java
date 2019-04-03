package com.example.moodtracker.model;

import com.example.moodtracker.R;

public enum Mood {

    SAD("SAD"),
    DISAPPOINTED("DISAPPOINTED"),
    NORMAL("NORMAL"),
    HAPPY("HAPPY"),
    SUPER_HAPPY("SUPER_HAPPY"),
    MOOD_OBJECT("");

    private String mIdMood;

    @Override
    public String toString() {
        return mIdMood;
    }

    Mood(String idMood) {
        mIdMood = idMood;
    }

    public Mood parseMood(String idMood) {
        if (idMood == null) return MOOD_OBJECT;
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
                    return MOOD_OBJECT;
            }
        }
    }

    public int Smiley() {
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

    public int Color() {
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
                return 0;
        }
    }

    public float Weight() {
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
                return 0;
        }
    }

    public String Prev() {
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

    public String Next() {
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
}

