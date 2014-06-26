package com.amaze.quit.app;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

/**
 * Created by Sander on 23-6-2014.
 */
public class MyAlarmService extends Service {

    private NotificationManager mManager;
    public static final String PREFS_NAME = "QuitPrefs";

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        // Check if new challenge achieved
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        // Look for new achieved challanges
        for (int i = 1; i <= db.getChallengesAmount(); i++) {
            int notificationGivin = db.getChallenge(i).getNotificationGivin();
            int behaald = db.getChallenge(i).getBehaald();
            String titel = db.getChallenge(i).getTitel();

            // If behaald and first time shown
            if (notificationGivin == 0 && behaald > 0) {
                // Give notification
                mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);

                NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                        .setContentTitle("Achievement behaald!")
                        .setContentText(titel)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.ic_launcher);

                // On notification tap
                Intent resultIntent = new Intent(this, SplashScreen.class);
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mNotifyBuilder.setContentIntent(resultPendingIntent);

                mManager.notify(0, mNotifyBuilder.build());

                updateChallengeDB(i, 1);
            }
        }

        UpdateStats updatestats = new UpdateStats(getApplicationContext());

        //gets the total saved amount
        float totalSavedAmount = updatestats.getSavedMoney();
        // what is spent?
        int spentAmount = updatestats.getSpentAmount();
        //what is left?
        float amountLeft = totalSavedAmount - spentAmount;

        // Product price
        float productPrice = db.getProduct(1).getPrijs();

        // If enough money for product
        if (amountLeft >= productPrice) {

            SharedPreferences completeNotification = getSharedPreferences(PREFS_NAME, 0);

            // First time
            if (!completeNotification.getBoolean("alreadyGivin", false)) {
                completeNotification.edit().putBoolean("alreadyGivin", true).commit();
                // Give notification
                mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);

                NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                        .setContentTitle("Gefeliciteerd!")
                        .setContentText("U heeft genoeg geld bespaard voor uw product")
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.ic_launcher);

                // On notification tap
                Intent resultIntent = new Intent(this, SplashScreen.class);
                resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mNotifyBuilder.setContentIntent(resultPendingIntent);

                mManager.notify(0, mNotifyBuilder.build());
            }
        }

        // Alarmmanager repeater
        PendingIntent pendingIntent;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);

        Intent myIntent = new Intent(MyAlarmService.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MyAlarmService.this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    // update notification row
    private void updateChallengeDB(int id, int notificationGivin) {
        Challenges challenge;
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        challenge = db.getChallenge(id);
        challenge.setNotificationGivin(notificationGivin);
        db.updateChallenge(challenge);
        db.close();
    }
}