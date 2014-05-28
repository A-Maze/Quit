package com.amaze.quit.app;




import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteException;
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
                    sleep(2000);
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
        if(settings.getBoolean("first_time",true) || isDatabaseThere()){
            startMainActivity = new Intent(getApplicationContext(),Setup.class);
            settings.edit().putBoolean("first_time",false).commit();
        }
        else{


            //else load the main activity
            startMainActivity = new Intent(getApplicationContext(),Home.class);
        }
        startActivity(startMainActivity);
    }

    //makes sure the user filled in setup
    private boolean isDatabaseThere() {
        DatabaseHandler checkDB = null;

        try {
            checkDB = new DatabaseHandler(this);
        } catch (SQLiteException e) {

        }

        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null;
    }

    private void fillDataBase() {
        DatabaseHandler db = new DatabaseHandler(this);
        //Sigaretten

        //Achievements.
        Challenges c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
        c1 = new Challenges("Eerste stapje","Doorbreng een dag zonder te roken");
        c2 = new Challenges("Even diep ademhalen","Verbeter de gezondheid van je longen met 10%");
        c3 = new Challenges("Ka-ching!","Bespaar 20 euro door niet te roken.");
        c4 = new Challenges("Geld op de bank","Bespaar 100 euro door niet te roken.");
        c5 = new Challenges("Extra leven","Verdien 10 dagen extra te leven.");
        c6 = new Challenges("Onsterfelijk","Verdien 50 dagen extra te leven.");
        c7 = new Challenges("Voor wat hoort wat", "Koop een product ter waarde van €50.");
        c8 = new Challenges("Dat was het waard","Koop een product ter waarde van €200.");
        c9 = new Challenges("Dat is één","Bespaar 1 pakje sigaretten/shag");
        c10 = new Challenges("Is dit nou zo moeilijk?","Rook 10 sigaretten niet.");

        int aantalChallanges = 10;

        Challenges[] challengesArray = new Challenges[]{c1,c2,c3,c4,c5,c6,c7,c8,c9,c10};
        for (int i = 0; i < aantalChallanges; i++) {
            db.addChallenge(challengesArray[i]); 
        }


    }
}
