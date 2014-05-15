package com.amaze.quit.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Date;

/**
 * Created by Rik on 14-5-2014.
 */
public class SetupQuitDate extends Activity{

    /* de next button op het quitdate scherm */
    private Button nextButton = (Button) findViewById(R.id.bStopDateNext);
    /* de datepicker op het quitdate scherm */
    private DatePicker quitDatePicker = (DatePicker) findViewById(R.id.dpStopDatum);
    // de stopdatum variabelen
    public int quitDay;
    public int quitMonth;
    public int quitYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_quit_datee);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // wat te doen als de Next Button geklikt is.
                quitDay = quitDatePicker.getDayOfMonth();
                quitMonth = quitDatePicker.getMonth();
                quitYear = quitDatePicker.getYear();
            }
        });




    }
}
