package com.example.moodtracker.model;

import java.util.Calendar;
import java.util.Date;

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

    public DayDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.setTime(date);
        mDate = calendar.getTime();
    }

    public Date getDate() {
        return mDate;
    }

    public DayDate addNumberOfDay(int numberOfDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        calendar.add(Calendar.DATE, numberOfDay);
        return new DayDate(calendar.getTime());
    }

    public int Between(DayDate date) {
        boolean sign = true;
        DayDate dateStart = this;
        DayDate dateEnd = date;
        int result = 0;

        if (mDate.after(date.getDate())) {
            dateStart = date;
            dateEnd = this;
            sign = false;
        }

        while (!dateStart.equals(dateEnd)) {
            dateStart = dateStart.addNumberOfDay(1);
            result++;
        }

        if (sign)
            return result;
        else
            return -result;
    }
}
