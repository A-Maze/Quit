package com.amaze.quit.app;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by jorik on 24-5-2014.
 */
public final class DateUtils {
    private DateUtils() {
    }

    public static Calendar calendarFor(int year, int month, int day, int hour, int minute) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        return cal;
    }

    public static long GetMinutesBetween(Calendar minDate, Calendar maxDate) {
        return (maxDate.getTimeInMillis() - minDate.getTimeInMillis()) / (60 * 1000);
    }

    public static long GetMinutesSince(Calendar date) {
        return (now().getTimeInMillis() - date.getTimeInMillis()) / (60 * 1000);
    }

    public static Calendar now() {
        return GregorianCalendar.getInstance();
    }
}

