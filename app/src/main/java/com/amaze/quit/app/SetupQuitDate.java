package com.amaze.quit.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;




public class SetupQuitDate extends Fragment {


    /* de datepicker op het quitdate scherm */
    private DatePicker quitDatePicker;

    // de stopdatum variabelen
    public static int quitDay;
    public static int quitMonth;
    public static int quitYear;

    public static final SetupQuitDate newInstance()
    {
        SetupQuitDate f = new SetupQuitDate();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        return f;


    }

    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_setup_quit_datee, container, false);

        quitDatePicker = (DatePicker) v.findViewById(R.id.dpStopDate);
        quitDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTheDay();
                getTheMonth();
                getTheYear();
            }
        });


        return v;



    }



    public void getTheDay() {
        quitDay =  quitDatePicker.getDayOfMonth();

    }
    public void getTheMonth() {

        quitMonth =  quitDatePicker.getMonth();



    }
    public void getTheYear() {

        quitYear =  quitDatePicker.getYear();


    }





}
