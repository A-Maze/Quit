package com.amaze.quit.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;


public class SetupQuitDate extends Fragment {


    /* de datepicker op het quitdate scherm */
    private static DatePicker quitDatePicker;
    private static TimePicker quitTimePicker;

    // de stopdatum variabelen
    public static int quitMinute;
    public static int quitHour;
    public static int quitDay;
    public static int quitMonth;
    public static int quitYear;

    public static final SetupQuitDate newInstance() {
        SetupQuitDate f = new SetupQuitDate();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        return f;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_setup_quit_datee, container, false);

        quitDatePicker = (DatePicker) v.findViewById(R.id.dpStopDate);
        final Calendar c = Calendar.getInstance();
        quitYear = c.get(Calendar.YEAR);
        quitMonth = c.get(Calendar.MONTH);
        quitDay = c.get(Calendar.DAY_OF_MONTH);
        quitDatePicker.init(quitYear, quitMonth, quitDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
                quitYear = i;
                quitMonth = i2;
                quitDay = i3;
            }
        });

        quitTimePicker = (TimePicker) v.findViewById(R.id.tpStopTime);
        quitTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i2) {
                quitHour = i;
                quitMinute = i2;
            }

        });
        quitTimePicker.setIs24HourView(true);


        return v;


    }


    public static int getTheMinute() {
        quitMinute = quitTimePicker.getCurrentMinute();
        return quitMinute;
    }

    public static int getTheHour() {
        quitHour = quitTimePicker.getCurrentHour();
        return quitHour;
    }

    public static int getTheDay() {
        quitDay = quitDatePicker.getDayOfMonth();
        return quitDay;
    }

    public static int getTheMonth() {

        quitMonth = quitDatePicker.getMonth();
        return quitMonth;

    }

    public static int getTheYear() {

        quitYear = quitDatePicker.getYear();
        return quitYear;
    }


}
