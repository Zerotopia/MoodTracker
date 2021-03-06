package com.example.moodtracker.model;

import com.example.moodtracker.R;

/**
 * This class contains all Mood of our application and
 * many functions to link a mood to hits specific data,
 * like background color, smiley, music sound etc
 */
public enum Mood {
    /**
     * ! ! WARNING ! !
     * It is important that Moods are sorted from sadder to happier
     * moreover Data arrays (SMILEYS,COLORS, and NOTES) should be
     * "sorted" of the same manner (i.e smileys are sorted from sadder to happier,
     * corresponding colors and notes too).
     */
    SAD,
    DISAPPOINTED,
    NORMAL,
    HAPPY,
    SUPER_HAPPY;

    public static final int[] SMILEYS = {R.mipmap.smiley_sad, R.mipmap.smiley_disappointed,
            R.mipmap.smiley_normal, R.mipmap.smiley_happy, R.mipmap.smiley_super_happy};
    public static final int[] COLORS = {R.color.faded_red, R.color.warm_grey,
            R.color.cornflower_blue_65, R.color.light_sage, R.color.banana_yellow};
    public static final int[] NOTES = {R.raw.e, R.raw.f, R.raw.a, R.raw.b, R.raw.c};

    public int smiley() { return SMILEYS[ordinal()]; }

    public int color() { return COLORS[ordinal()]; }

    /**
     * weight is use to determine the larger of a "mood row" in
     * the historyActivity. weight is define such that the width of
     * the sadder mood is at least 1/5 of the screen width. And
     * such that the width of the happier mood is equals to the screen width.
     * See the weightMax function of HistoryActivity for more details
     */
    public float weight() {
        return (values().length - 2) / 4 + ordinal() + 1;
    }

    /**
     * prev and next function are used to determine
     * the next or previous mood when the user swipe on the screen.
     */
    public int prev() {
        if (ordinal() != 0) return ordinal() - 1;
        else return ordinal();
    }

    public int next() {
        if (ordinal() != (values().length - 1)) return ordinal() + 1;
        else return ordinal();
    }
}

