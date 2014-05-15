package com.amaze.quit.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;


public class SetupQuitDate extends ActionBarActivity {


    /* de datepicker op het quitdate scherm */
    private DatePicker quitDatePicker;

    // de stopdatum variabelen
    public int quitDay;
    public int quitMonth;
    public int quitYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_quit_datee);
        quitDatePicker = (DatePicker) findViewById(R.id.dpStopDate);
    }

    private void getTheDate() {
        quitDay = quitDatePicker.getDayOfMonth();
        quitMonth = quitDatePicker.getMonth();
        quitYear = quitDatePicker.getYear();
    }

    public void runSetupBrand(View view) {
        getTheDate();

        Intent intent = new Intent(this, SetupBrandAmount.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setup_quit_date, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.iSmokeBehaviours:
                startAnActivity(SetupBrandAmount.class);
            case R.id.iQuitDate:
                startAnActivity(SetupQuitDate.class);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startAnActivity(Class activiteit) {
        Intent intent = new Intent(this, activiteit);
        startActivity(intent);
    }

}
