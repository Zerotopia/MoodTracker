package com.example.moodtracker.controller;

import android.content.SharedPreferences;

import com.example.moodtracker.model.DayDate;
import com.example.moodtracker.model.Mood;

public class DataManager {


    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditPreferences;

    public DataManager(SharedPreferences preferences) {
        mPreferences = preferences;
        mEditPreferences = preferences.edit();
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }

    public void copyInList (String[] keyList, String key, int position) {
        if (0 <= position && position < keyList.length)
        mEditPreferences.putString(keyList[position],mPreferences.getString(key,"")).apply();
    }

    public boolean shiftList(String[] keyList, int shift) {
        if (shift < 0)
            return true;
        else if (0 < shift && shift <= keyList.length) {
            for (int i = 0; i < keyList.length - shift; i++) {
                copyInList(keyList,keyList[i + shift],i);
                mEditPreferences.remove(keyList[i + shift]);
            }
        }
        else if (shift > keyList.length) {
            for (String key : keyList)
                mEditPreferences.remove(key);
        }
        return false;
    }

    public void clearString (String key) {
        mEditPreferences.putString(key, "");
    }

    public void putMood (String key, Mood mood) {
        mEditPreferences.putString(key,mood.toString()).apply();
    }

    public void putDayDate (String key, DayDate dayDate) {
        mEditPreferences.putString(key,dayDate.toString()).apply();
    }

    public void putComment (String key, String comment) {
        mEditPreferences.putString(key,comment).apply();
    }

    public String getString (String key, String defvalue) {
        return mPreferences.getString(key,defvalue);
    }
}
