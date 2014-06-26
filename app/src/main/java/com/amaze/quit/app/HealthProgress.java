package com.amaze.quit.app;


import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class HealthProgress extends Fragment {
    static int position;
    private static final int UpdateProgress = 0;
    private UserVisibilityEvent uservisibilityevent;

    Handler handler;

    //teken vooruitgang progressbars met cijfers
    protected void drawElements(int id) {
        Activity a = getActivity();

        int progress = getProgress(id);
        int barId = getResources().getIdentifier("progressBar_gezondheid_" + id, "id", a.getPackageName());
        ProgressBar gezondheidBar = (ProgressBar) a.findViewById(barId);
        gezondheidBar.setProgress(progress);

        int tId = getResources().getIdentifier("health_procent" + id, "id", a.getPackageName());
        TextView t = (TextView) a.findViewById(tId);
        t.setText(progress + "%");

        long tijd = getRemainingTime(id);

        //draw days, hours, minutes
        long dagen = tijd / 1440;
        long uren = (tijd - dagen * 1440) / 60;
        long minuten = tijd - dagen * 1440 - uren * 60;

        int timerId = getResources().getIdentifier("health_timer" + id, "id", a.getPackageName());
        TextView timer = (TextView) a.findViewById(timerId);

        String timerText = "";
        if (dagen > 0) {
            if (dagen == 1) {
                timerText += dagen + " dag ";
            } else {
                timerText += dagen + " dagen ";
            }
        }
        if (uren > 0) {
            timerText += uren + " uur ";

        }
        timerText += minuten;
        if(minuten == 1) {
            timerText += " minuut";
        }
        else {
            timerText += " minuten";
        }
        timer.setText(timerText);


    }

    //tekent algemene gezonheid
    protected void drawAverage() {
        //teken de progress van de algemene gezondheid bar (gemiddelde van alle andere)
        int totalProgress = 0;
        for (int i = 1; i <= 9; i++) {
            totalProgress += getProgress(i);
        }
        int average = totalProgress / 9;
        ProgressBar totaalGezondheidBar = (ProgressBar) getActivity().findViewById(R.id.progressBar_algemeenGezondheid);
        totaalGezondheidBar.setProgress(average);
        //totaalGezondheidBar.getProgressDrawable().

    }

    //tekent vooruitgang
    protected void drawProgress() {
        Activity a = getActivity();
        Date today = new Date();

        //teken de progress van elke individuele bar + procenten
        for (int i = 1; i <= 9; i++) {
            drawElements(i);
        }

        drawAverage();
    }

    //geeft progress terug
    public int getProgress(int id) {
        double progress;
        double max;
        double current;
        DatabaseHandler db = new DatabaseHandler(getActivity());

        Calendar stopDate = DateUtils.calendarFor(db.getUser(1).getQuitYear(), db.getUser(1).getQuitMonth(), db.getUser(1).getQuitDay(), db.getUser(1).getQuitHour(), db.getUser(1).getQuitMinute());
        long stoppedMinutes = DateUtils.GetMinutesSince(stopDate);

        //close the database
        db.close();

        switch (id) {
            case 1:
                current = stoppedMinutes;
                max = 1 * 24 * 60;
                break; // max in days
            case 2:
                current = stoppedMinutes;
                max = 365 * 24 * 60;
                break;
            case 3:
                current = stoppedMinutes;
                max = 2 * 24 * 60;
                break;
            case 4:
                current = stoppedMinutes;
                max = 4 * 24 * 60;
                break;
            case 5:
                current = stoppedMinutes;
                max = 2 * 24 * 60;
                break;
            case 6:
                current = stoppedMinutes;
                max = 40 * 24 * 60;
                break;
            case 7:
                current = stoppedMinutes;
                max = 14 * 24 * 60;
                break;
            case 8:
                current = stoppedMinutes;
                max = 3 * 365 * 24 * 60;
                break;
            case 9:
                current = stoppedMinutes;
                max = 10 * 365 * 24 * 60;
                break;

            default:
                current = 100;
                max = 100;
                break;

        }
        progress = (current / max) * 100;
        if (progress > 100) {
            progress = 100;
        }
        return (int) progress;
    }

    //geeft tijd over terug
    protected int getRemainingTime(int id) {
        long tijd = 0;
        DatabaseHandler db = new DatabaseHandler(getActivity());

        Calendar today = Calendar.getInstance();
        Calendar stopDate = DateUtils.calendarFor(db.getUser(1).getQuitYear(), db.getUser(1).getQuitMonth(), db.getUser(1).getQuitDay(), db.getUser(1).getQuitHour(), db.getUser(1).getQuitMinute());
        Calendar maxDate = stopDate;

        //close the database
        db.close();

        switch (id) {
            case 1:
                maxDate.add(Calendar.DAY_OF_MONTH, 1);
                break; // STOPDATUM + HOEVEEL DAGEN HET DUURT
            case 2:
                maxDate.add(Calendar.YEAR, 1);
                break;
            case 3:
                maxDate.add(Calendar.DAY_OF_MONTH, 2);
                break;
            case 4:
                maxDate.add(Calendar.DAY_OF_MONTH, 4);
                break;
            case 5:
                maxDate.add(Calendar.DAY_OF_MONTH, 2);
                break;
            case 6:
                maxDate.add(Calendar.DAY_OF_MONTH, 40);
                break;
            case 7:
                maxDate.add(Calendar.DAY_OF_MONTH, 14);
                break;
            case 8:
                maxDate.add(Calendar.YEAR, 3);
                break;
            case 9:
                maxDate.add(Calendar.YEAR, 10);
                break;

            default:
                break;

        }
        tijd = DateUtils.GetMinutesBetween(today, maxDate);
        if (tijd < 0) {
            tijd = 0;
        }
        return (int) tijd;
    }

    public static final HealthProgress newInstance(int i) {
        HealthProgress f = new HealthProgress();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        position = i;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_health_progress, container, false);

        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                if (msg.what == UpdateProgress) {


                    if (getActivity() != null) {
                        Activity a = getActivity();

                        TextView t1 = (TextView) a.findViewById(R.id.health_procent1);

                        if (t1 != null) {
                            drawProgress();
                        }
                    }


                }

                super.handleMessage(msg);
            }
        };

        //timer
        Thread updateProcess = new Thread() {
            public void run() {


                while (1 == 1) {
                    try {
                        sleep(10000);

                        Message msg = new Message();
                        msg.what = UpdateProgress;
                        handler.sendMessage(msg);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                    }
                }
            }
        };
        updateProcess.start();

        //update on startup
        Message msg = new Message();
        msg.what = UpdateProgress;
        handler.sendMessage(msg);

        return v;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //implements the main method what every fragment should do when it's visible
            uservisibilityevent.viewIsVisible(getActivity(), position, "green", "title_activity_health_progress");


        }

    }


}

