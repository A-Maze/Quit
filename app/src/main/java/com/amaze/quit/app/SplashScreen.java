package com.amaze.quit.app;




import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import java.util.Calendar;


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
            if(!settings.getBoolean("dataFilled",false)) {
                fillDataBase();
                settings.edit().putBoolean("dataFilled",true).commit();
            }

            // Alarmmanager first time
            PendingIntent pendingIntent;
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, 1);

            Intent myIntent = new Intent(SplashScreen.this, MyReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(SplashScreen.this, 0, myIntent,0);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

            // Start main activity
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
        new Thread(new Runnable() {
            public void run() {
                DatabaseHandler db = new DatabaseHandler(getBaseContext());
        //Sigaretten
        db.addSigarette(new Sigaretten(1,2f,"Camel 19",19,4,5.5f));
        db.addSigarette(new Sigaretten(2,2f,"Camel 21",21,4,6f));
        db.addSigarette(new Sigaretten(3,2f,"Camel 22",22,4,6.6f));
        db.addSigarette(new Sigaretten(4,2f,"Camel 25",25,4,7.6f));
        db.addSigarette(new Sigaretten(5,2f,"Camel 27",27,4,7.8f));

        db.addSigarette(new Sigaretten(6,2f,"Chesterfield 19",19,4,5.8f));
        db.addSigarette(new Sigaretten(7,2f,"Chesterfield 22",22,4,6.5f));

        db.addSigarette(new Sigaretten(8,2f,"Kent 19",19,4,6f));
        db.addSigarette(new Sigaretten(9,2f,"Kent 22",22,4,7f));

        db.addSigarette(new Sigaretten(10,2f,"L&M",19,4,5.4f));
        db.addSigarette(new Sigaretten(11,2f,"L&M",22,4,5.9f));
        db.addSigarette(new Sigaretten(12,2f,"L&M",27,4,7.5f));

        db.addSigarette(new Sigaretten(13,2f,"Lucky Strike 19",19,4,5.8f));
        db.addSigarette(new Sigaretten(14,2f,"Lucky Strike 22",27,4,6.5f));

        db.addSigarette(new Sigaretten(15,2f,"Marlboro 19",19,4,6f));
        db.addSigarette(new Sigaretten(16,2f,"Marlboro 23",23,4,7f));
        db.addSigarette(new Sigaretten(17,2f,"Marlboro 27",27,4,8f));

        db.addSigarette(new Sigaretten(18,2f,"Pall Mall 19",19,4,5.4f));
        db.addSigarette(new Sigaretten(19,2f,"Pall Mall 22",22,4,6.3f));
        db.addSigarette(new Sigaretten(20,2f,"Pall Mall 27",27,4,7.5f));

        db.addSigarette(new Sigaretten(21,2f,"Winston 19",19,4,5.4f));
        db.addSigarette(new Sigaretten(22,2f,"Winston 21",21,4,6f));
        db.addSigarette(new Sigaretten(23,2f,"Winston 26",26,4,7.1f));

        //Shag

                String[] merk = {
                        "Amberleaf JTI 50 GR",
                        "Brandaris Zwaar IMPERIAL 40 GR",
                        "Camel MYO 110 JTI 110 GR",
                        "Camel RYO original/zwaar JTI 40 GR",
                        "Drum blauw/menthol IMPERIAL 40 GR",
                        "Drum Classic 50 gr IMPERIAL 50 GR",
                        "Drum Original Flavour IMPERIAL 130 GR",
                        "Drum Original IMPERIAL 30 GR",
                        "Drum Select IMPERIAL 40 GR",
                        "Gauloises vol/zwaar IMPERIAL 40 GR",
                        "Golden Virginia IMPERIAL 50 GR",
                        "Gruno Privilege 150 BAT 150 GR",
                        "Javaanse Jongens BAT 37,5 GR",
                        "JPS red/silver IMPERIAL 110 GR",
                        "JPS vol/zwaar IMPERIAL 50 GR",
                        "L&M Original PM 35 GR",
                        "L&M original/zwaar 50 PM 50 GR",
                        "L&M Premium cut 110 PM 110 GR",
                        "L&M Premium cut 25 PM 25 GR",
                        "L&M premium cut tin PM 55 GR",
                        "L&M Volume Tabak 50 PM 50 GR",
                        "Lucky Strike Additieve Free BAT 35 GR",
                        "Lucky Strike Original 110 BAT 110 GR",
                        "Lucky Strike vol/zwaar BAT 37,5 GR",
                        "Van Nelle Stevige shag IMPERIAL 40 GR",
                        "Marlboro Red 60 PM 60 GR",
                        "Marlboro Red PM 25 GR",
                        "Marlboro Red PM 30 GR",
                        "Pall Mall 110 gram BAT 110 GR",
                        "Pall Mall 60 gram BAT 60 GR",
                        "Pall Mall Full/Heavy BAT 35 GR",
                        "Pall Mall full/heavy BAT 52,5 GR",
                        "Samson BAT 40 GR",
                        "Samson Original Taste BAT 55 GR",
                        "Van Nelle Black Fire IMPERIAL 40 GR",
                        "Van Nelle Export IMPERIAL 40 GR",
                        "Van Nelle Zwaar IMPERIAL 30 GR",
                        "Van Nelle zwaar- 50 IMPERIAL 50 GR",
                        "West red/silver IMPERIAL 110 GR",
                        "West red/silver IMPERIAL 65 GR",
                        "Winston MYO 100 JTI 100 GR",
                        "Winston MYO 125 JTI 125 GR",
                        "Winston MYO 70 JTI 70 GR",
                        "Zilver 140 IMPERIAL 140 GR",
                        "Zilver IMPERIAL 40 GR",
                        "Zilver vol/zwaar IMPERIAL 30 GR",
                        "Zilver XL Vol/Zwaar IMPERIAL 50 GR"

                };
                float[] prijs = {
                        7.4f, 6.95f, 15.5f, 6f, 6.7f, 8.2f, 19.9f,5f, 6.8f, 6.1f, 7.9f, 23f, 6.5f, 16.5f, 7.2f, 5f, 7.2f, 16.3f, 3.7f, 8.3f, 8f, 5.5f, 18f, 6f, 6.8f, 10f, 4f, 5f, 17.5f, 9.4f, 5f, 7.5f, 6.7f, 9.2f, 6.95f, 7.1f, 5.3f, 8.7f, 16.3f, 9.9f, 13.5f, 17f, 10f, 19.3f, 5.9f, 4.4f, 7.2f
                };

                for(int i = 0; i <= 46; i++){



                    db.addShag(new Shag(i+1,merk[i],40,prijs[i]));
                }



        //Achievements.
        Challenges c1,c2,c3,c4,c5,c6,c7,c8,c9,c10;
        c1 = new Challenges(1,"Eerste stapje","Doorbreng een dag zonder te roken", 0, 0);
        c2 = new Challenges(2,"Even diep ademhalen","Verbeter de gezondheid van je longen met 10%", 0, 0);
        c3 = new Challenges(3,"Ka-ching!","Bespaar 20 euro door niet te roken.", 0, 0);
        c4 = new Challenges(4,"Geld op de bank","Bespaar 100 euro door niet te roken.", 0, 0);
        c5 = new Challenges(5,"Extra leven","Verdien 10 dagen extra te leven.", 0, 0);
        c6 = new Challenges(6,"Onsterfelijk","Verdien 50 dagen extra te leven.", 0, 0);
        c7 = new Challenges(7,"Voor wat hoort wat", "Koop een product ter waarde van €50.", 0, 0);
        c8 = new Challenges(8,"Dat was het waard","Koop een product ter waarde van €200.", 0, 0);
        c9 = new Challenges(9,"Dat is één","Bespaar 1 pakje sigaretten/shag", 0, 0);
        c10 = new Challenges(10,"Is dit nou zo moeilijk?","Rook 10 sigaretten niet.", 0, 0);

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
        }).start();
    }
}
