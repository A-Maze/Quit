package com.amaze.quit.app;




import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class SplashScreen extends Activity {
    public static final String PREFS_NAME = "QuitPrefs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    firstTime();
                    finish();

                }
            }
        };
        timer.start();
    }

    protected void firstTime(){
        // loads the settings
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        Intent startMainActivity;

        // if it is the first time opening the app load Setup
        if(settings.getBoolean("first_time",true)){
            startMainActivity = new Intent(getApplicationContext(),Setup.class);
            settings.edit().putBoolean("first_time",false).commit();
        }
        else{
            //else load the main activity
            startMainActivity = new Intent(getApplicationContext(),Home.class);
        }
        startActivity(startMainActivity);
    }
}
