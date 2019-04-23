package com.example.moodtracker.model;

import android.support.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * To know when save a Mood in the history, we see if the date has change
 * since the last time that the user selected a Mood. But in Android a Date
 * has hour, minutes, ...  So in this class we set hour, minutes etc to 0 to
 * havea "Day date" i.e. a date with only relevant information for our application.
 */
public class DayDate {

    private Date mDate;

    public DayDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        mDate = calendar.getTime();
    }

    public Date getDate() {
        return mDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        return simpleDateFormat.format(mDate);
    }

    public void parseDayDate(String formatDayDate) {
        SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
        try {
            mDate = simpleDateFormat.parse(formatDayDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public boolean isEqualsTo(@NonNull DayDate dayDate) {
        return toString().equals(dayDate.toString());
    }

    public void addNumberOfDay(int numberOfDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.DATE, numberOfDay);
        mDate = calendar.getTime();
    }

    /**
     * This function compute the number of day between two DayDate.
     * Usage : date1.between(date2)
     * if date1 <= date2 return date2 - date1 (in term of number of day)
     * else return -(date2.between(date1))
     */
    public int between(DayDate date) {
        boolean sign = true;
        DayDate dateStart = this;
        DayDate dateEnd = date;
        int result = 0;

        if (mDate.after(date.getDate())) {
            dateStart = date;
            dateEnd = this;
            sign = false;
        }

        while (!dateStart.isEqualsTo(dateEnd)) {
            dateStart.addNumberOfDay(1);
            result++;
        }
        return (sign) ? result : -result;
    }
}
