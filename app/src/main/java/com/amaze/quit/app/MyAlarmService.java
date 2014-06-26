package com.amaze.quit.app;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

public class MyAlarmService extends Service {

    private NotificationManager mManager;

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
        for (int i = 1; i <= db.getChallengesAmount(); i++) {
            int notificationGivin = db.getChallenge(i).getNotificationGivin();
            int behaald = db.getChallenge(i).getBehaald();
            String titel = db.getChallenge(i).getTitel();

            if (notificationGivin == 0 && behaald > 0) {
                // Give notification
                mManager = (NotificationManager) this.getApplicationContext().getSystemService(this.getApplicationContext().NOTIFICATION_SERVICE);
                Intent intentt = new Intent(this.getApplicationContext(),SplashScreen.class);
                intentt.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

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

                int numMessages = 0;
                mNotifyBuilder.setNumber(++numMessages);

                mManager.notify(0, mNotifyBuilder.build());


                /*
                Notification notification = new Notification(R.drawable.ic_launcher,"Achievement behaald!", System.currentTimeMillis());
                intentt.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);

                PendingIntent pendingNotificationIntent = PendingIntent.getActivity( this.getApplicationContext(),0, intentt,PendingIntent.FLAG_UPDATE_CURRENT);
                notification.flags |= Notification.FLAG_AUTO_CANCEL;
                notification.defaults |= Notification.DEFAULT_SOUND;
                notification.defaults |= Notification.DEFAULT_VIBRATE;
                notification.flags |= Notification.FLAG_SHOW_LIGHTS;
                notification.ledARGB = 0xff00ff00;
                notification.ledOnMS = 300;
                notification.ledOffMS = 1000;
                notification.setLatestEventInfo(this.getApplicationContext(), "Achievement behaald!", titel, pendingNotificationIntent);

                mManager.notify(0, notification);
                */

                // setNotficationGivin on 1
                updateChallengeDB(i, 1);
            }
        }

        // Alarmmanager repeating
        PendingIntent pendingIntent;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 5);

        Intent myIntent = new Intent(MyAlarmService.this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MyAlarmService.this, 0, myIntent,0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    private void updateChallengeDB(int id, int notificationGivin) {
        Challenges challenge;
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        challenge = db.getChallenge(id);
        challenge.setNotificationGivin(notificationGivin);
        db.updateChallenge(challenge);
        db.close();
    }private void fdsa(int id, int achieved) {
        Challenges challenge;
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        challenge = db.getChallenge(id);
        challenge.setBehaald(achieved);
        db.updateChallenge(challenge);
        db.close();
    }
}