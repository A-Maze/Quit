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
        if(settings.getBoolean("first_time",true) || !isDatabaseThere()){
            fillDataBase();
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
        DatabaseHandler checkDB = new DatabaseHandler(this);

        try {
            checkDB.getUser(1);
            checkDB.getProduct(1);
            checkDB.close();
        } catch (Exception e) {
            return false;
        }
    return true;


    }

    private void fillDataBase() {
        DatabaseHandler db = new DatabaseHandler(this);
        //Sigaretten
        db.addSigarette(new Sigaretten(1,2f,"Chesterfield",19,4,5f));
        db.addSigarette(new Sigaretten(2,2f,"Marlboro",19,4,5f));
        db.addSigarette(new Sigaretten(3,2f,"Lucky Strike",19,4,5f));
        db.addSigarette(new Sigaretten(4,2f,"Camel",19,4,5f));

        //Achievements.
        Challenges c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
        c1 = new Challenges(1,"Eerste stapje","Doorbreng een dag zonder te roken", 0);
        c2 = new Challenges(2,"Even diep ademhalen","Verbeter de gezondheid van je longen met 10%", 0);
        c3 = new Challenges(3,"Ka-ching!","Bespaar 20 euro door niet te roken.", 0);
        c4 = new Challenges(4,"Geld op de bank","Bespaar 100 euro door niet te roken.", 0);
        c5 = new Challenges(5,"Extra leven","Verdien 10 dagen extra te leven.", 0);
        c6 = new Challenges(6,"Onsterfelijk","Verdien 50 dagen extra te leven.", 0);
        c7 = new Challenges(7,"Voor wat hoort wat", "Koop een product ter waarde van €50.", 0);
        c8 = new Challenges(8,"Dat was het waard","Koop een product ter waarde van €200.", 0);
        c9 = new Challenges(9,"Dat is één","Bespaar 1 pakje sigaretten/shag", 0);
        c10 = new Challenges(10,"Is dit nou zo moeilijk?","Rook 10 sigaretten niet.", 0);

        int aantalChallanges = 10;

        Challenges[] challengesArray = new Challenges[]{c1,c2,c3,c4,c5,c6,c7,c8,c9,c10};
        for (int i = 0; i < aantalChallanges; i++) {

            db.addChallenge(challengesArray[i]); 
        }

        //levels
        Levels l1,l2,l3,l4,l5;
        l1 = new Levels(1,"stoomboot","", 0);
        l2 = new Levels(2,"kettingroker","", 5);
        l3 = new Levels(3,"oud omaatje conditie","", 10);
        l4 = new Levels(4,"normaal mens","", 20);
        l5 = new Levels(5,"super fit","",1000000000);

        int aantalLevels = 5;

        Levels[] levelArray = new Levels[]{l1,l2,l3,l4,l5};
        for (int i = 0; i < aantalLevels; i++) {
            db.addLevel(levelArray[i]);
        }

        db.close();
    }
}
