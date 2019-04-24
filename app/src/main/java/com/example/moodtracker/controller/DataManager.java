package com.example.moodtracker.controller;

import android.content.SharedPreferences;

import com.example.moodtracker.model.DayDate;
import com.example.moodtracker.model.Mood;

/**
 * DataManager manage the Shared Preferences of the user.
 */
public class DataManager {


    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditPreferences;

    public static final int INT = 0;
    public static final int STRING = 1;

    public DataManager(SharedPreferences preferences) {
        mPreferences = preferences;
        mEditPreferences = preferences.edit();
    }

    /**
     * The Moods and the comments that appear in history are saved in SharedPreference under the key list
     * {DAY0,DAY1,DAY2,...} and {COMMENT0,COMMENT1,COMMENT,...}.
     * copyInList and shiftList are used to update history.
     */

    /**
     * For example if we have SharedPreferences like this :
     * key   : key0 | key1 | key2 | key3 | otherkey |
     * value :  abc |   de |  fgh |  ijk |      mno |
     *
     * with keylist = {key0,key1,key2,key3 }
     *          key = otherkey
     *     position = 1
     *
     * after copyInList(keylist,key,position) we have SharedPreference like this :
     * key   : key0 | key1 | key2 | key3 | otherkey |
     * value :  abc |  mno |  fgh |  ijk |      mno |
     */
    public void copyInList(String[] keyList, String key, int position, int type) {
        if (type == INT) {
            if (0 <= position && position < keyList.length)
                mEditPreferences.putInt(keyList[position], mPreferences.getInt(key, -1)).apply();
        }
        if (type == STRING) {
            if (0 <= position && position < keyList.length)
                mEditPreferences.putString(keyList[position], mPreferences.getString(key, "")).apply();
        }
    }


    /**
     * For example if we have SharedPreferences like this :
     * key   : key0 | key1 | key2 | key3 | key4 |
     * value :  abc |   de |  fgh |  ijk |  mno |
     *
     * with keylist = {key0,key1,key2,key3,key4 }
     *        shift = 2
     *
     * after shiftList(keylist,shift) we have SharedPreference like this :
     * key   : key0 | key1 | key2 | key3 | key4 |
     * value :  fgh |  ijk |  mno |      |      |
     */
    public boolean shiftList(String[] keyList, int shift, int type) {
        if (shift < 0)
            return true;
        else if (0 < shift && shift <= keyList.length) {
            for (int i = 0; i < keyList.length - shift; i++) {
                copyInList(keyList, keyList[i + shift], i,type);
                mEditPreferences.remove(keyList[i + shift]);
            }
        } else if (shift > keyList.length) {
            for (String key : keyList)
                mEditPreferences.remove(key);
        }
        return false;
    }

    /**
     * Ths following functions are used to have a more readable code.
     * furthermore thanks to this functions, DataManager is the only class
     * that manipulate directly the Shared Preference.
     */
    public void clearString(String key) {
        mEditPreferences.putString(key, "");
    }

    public void putMood(String key, Mood mood) {
        mEditPreferences.putInt(key, mood.ordinal()).apply();
    }

    public void putDayDate(String key, DayDate dayDate) {
        mEditPreferences.putString(key, dayDate.toString()).apply();
    }

    public void putComment(String key, String comment) {
        mEditPreferences.putString(key, comment).apply();
    }

    public String getString(String key, String defvalue) {
        return mPreferences.getString(key, defvalue);
    }

    public int getInt(String key, int defvalue) {
        return mPreferences.getInt(key, defvalue);
    }
}
