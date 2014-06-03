package com.amaze.quit.app;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by Robin on 30-5-2014.
 */
public class UpdateStats {
    public static long daysQuit = 0;
    public static float bespaardeMoneys;
    public static float extraDagenTeLeven;
    public static long bespaardePakjes;
    public static long gemiddeldNietGerookt;
    private static Context theContext;


    public UpdateStats(Context context){
        theContext = context;
    }


    // checks how many days the user has quit
    public void updateQuit(){

        DatabaseHandler db = new DatabaseHandler(theContext);
        Calendar quitDate = Calendar.getInstance();
        quitDate.set(db.getUser(1).getQuitYear(), db.getUser(1).getQuitMonth(), db.getUser(1).getQuitDay());

        Calendar vandaag = Calendar.getInstance();


        long diff = vandaag.getTimeInMillis() - quitDate.getTimeInMillis(); //result in millis
        long days = diff / (24 * 60 * 60 * 1000);
        daysQuit = days;

        bespaardeMoneys = (db.getSigaret(db.getUser(1).getsID()).getAantal() / db.getSigaret(db.getUser(1).getsID()).getPrijs()) * db.getUser(1).getPerDag() * days;
        bespaardeMoneys = Math.round(bespaardeMoneys) * 100;
        bespaardeMoneys = bespaardeMoneys / 100;

        extraDagenTeLeven = db.getUser(1).getPerDag() * days * 28 / 1440;

        bespaardePakjes = days / ((db.getSigaret(db.getUser(1).getsID()).getAantal() / db.getUser(1).getPerDag()));
        gemiddeldNietGerookt = days * db.getUser(1).getPerDag();

        db.close();
    }

    //gives back the days quit
    public long getDaysQuit(){
        return daysQuit;
    }

    public float getSavedMoney(){
        return bespaardeMoneys;
    }

    public void updateAchievements(){
        updateQuit();

        if(daysQuit >= 1){
            updateChallengeDB(1);
        }

        if(bespaardeMoneys >= 100){
            updateChallengeDB(3);
            updateChallengeDB(4);
        }
        else if(bespaardeMoneys >= 20){
            updateChallengeDB(3);
        }

        if(extraDagenTeLeven >= 50){
            updateChallengeDB(5);
            updateChallengeDB(6);
        }
        else if(extraDagenTeLeven >= 10){
            updateChallengeDB(5);
        }
        if(bespaardePakjes >= 1){
            updateChallengeDB(9);
        }
        if(gemiddeldNietGerookt >= 10){
            updateChallengeDB(10);
        }

    }

    private void updateChallengeDB(int id){
        Challenges challenge;
        DatabaseHandler db = new DatabaseHandler(theContext);
        challenge = db.getChallenge(id);
        challenge.setBehaald(1);
        db.updateChallenge(challenge);
        db.close();
    }


}
