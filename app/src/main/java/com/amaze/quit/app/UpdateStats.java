package com.amaze.quit.app;

import android.content.Context;
import android.util.Log;

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
    public static int quitDay;
    public static int quitMonth;
    public static int quitYear;
    public static Calendar quitDate;
    private static int userLevel;
    private static Context theContext;


    public UpdateStats(Context context) {
        theContext = context;
    }


    // checks how many days the user has quit
    public void updateQuit() {

        DatabaseHandler db = new DatabaseHandler(theContext);

        quitDate = Calendar.getInstance();
        quitDate.set(db.getUser(1).getQuitYear(), db.getUser(1).getQuitMonth(), db.getUser(1).getQuitDay());

        Calendar vandaag = Calendar.getInstance();


        long diff = vandaag.getTimeInMillis() - quitDate.getTimeInMillis(); //result in millis
        long days = diff / (24 * 60 * 60 * 1000);
        daysQuit = days;
        Log.d("aantal", Integer.toString(db.getSigaret(db.getUser(1).getsID()).getAantal()));
        Log.d("sID setup", Integer.toString(db.getSigaret(db.getUser(1).getsID()).getsID()));
        bespaardeMoneys = (days / (db.getSigaret(db.getUser(1).getsID()).getAantal() / db.getUser(1).getPerDag())) * db.getSigaret(db.getUser(1).getsID()).getPrijs();
        bespaardeMoneys = Math.round(bespaardeMoneys) * 100;
        bespaardeMoneys = bespaardeMoneys / 100;

        extraDagenTeLeven = db.getUser(1).getPerDag() * days * 28 / 1440;

        bespaardePakjes = days / ((db.getSigaret(db.getUser(1).getsID()).getAantal() / db.getUser(1).getPerDag()));
        gemiddeldNietGerookt = days * db.getUser(1).getPerDag();

        int currLevel = db.getUser(1).getLevel();

        for (int i = (currLevel + 1); i <= db.getLevelAmount(); i++) {
            int nextLevelDays = db.getLevel(i).getMinDays();
            if (daysQuit >= nextLevelDays) {
                updateUserLevel(i);
            }
        }
        userLevel = db.getUser(1).getLevel();
        db.close();
    }


    //gives back the days quit
    public long getDaysQuit() {
        return daysQuit;
    }

    public Calendar getCalendar() {
        return quitDate;
    }

    public float getSavedMoney() {
        return bespaardeMoneys;
    }

    public float getExtraDagenTeLeven() {
        return extraDagenTeLeven;
    }

    public int getUserLevel() {
        return userLevel;
    }

    public void updateAchievements() {
        updateQuit();

        if (daysQuit >= 1) {
            updateChallengeDB(1, 1);
        } else {
            updateChallengeDB(1, 0);
        }

        if (bespaardeMoneys >= 100) {
            updateChallengeDB(3, 1);
            updateChallengeDB(4, 1);
        } else if (bespaardeMoneys >= 20) {
            updateChallengeDB(3, 1);
            updateChallengeDB(4, 0);
        } else {
            updateChallengeDB(3, 0);
            updateChallengeDB(4, 0);
        }

        if (extraDagenTeLeven >= 50) {
            updateChallengeDB(5, 1);
            updateChallengeDB(6, 1);
        } else if (extraDagenTeLeven >= 10) {
            updateChallengeDB(5, 1);
            updateChallengeDB(6, 0);
        } else {
            updateChallengeDB(5, 0);
            updateChallengeDB(6, 0);
        }
        if (bespaardePakjes >= 1) {
            updateChallengeDB(9, 1);
        } else {
            updateChallengeDB(9, 0);
        }
        if (gemiddeldNietGerookt >= 10) {
            updateChallengeDB(10, 1);
        } else {
            updateChallengeDB(10, 0);
        }

    }

    private void updateChallengeDB(int id, int achieved) {
        Challenges challenge;
        DatabaseHandler db = new DatabaseHandler(theContext);
        challenge = db.getChallenge(id);
        challenge.setBehaald(achieved);
        db.updateChallenge(challenge);
        db.close();
    }

    private void updateUserLevel(int id) {
        User user;
        DatabaseHandler db = new DatabaseHandler(theContext);
        user = db.getUser(1);
        user.setLevel(id);
        db.updateUser(user);
        db.close();
    }


}
