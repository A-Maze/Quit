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

    private static final int UpdateProgress = 0;

    Handler handler;


    protected void drawProgress() {
        Activity a = getActivity();
        Date today = new Date();

        //teken de progress van elke individuele bar + procenten

        int p1 = getProgress(1);
        ProgressBar gezondheidBar1 = (ProgressBar) a.findViewById(R.id.progressBar_gezondheid_1);
        gezondheidBar1.setProgress(p1);
        TextView t1 = (TextView) a.findViewById(R.id.health_procent1);
        t1.setText(p1+"%");
        long tijd1 = getRemainingTime(1);
        TextView timer1 = (TextView) a.findViewById(R.id.health_timer1);
        timer1.setText(tijd1 + " minuten");

        int p2 = getProgress(2);
        ProgressBar gezondheidBar2 = (ProgressBar) a.findViewById(R.id.progressBar_gezondheid_2);
        gezondheidBar2.setProgress(p2);
        TextView t2 = (TextView) a.findViewById(R.id.health_procent2);
        t2.setText(p2+"%");
        long tijd2 = getRemainingTime(2);
        TextView timer2 = (TextView) a.findViewById(R.id.health_timer2);
        timer2.setText(tijd2 + " minuten");

        int p3 = getProgress(3);
        ProgressBar gezondheidBar3 = (ProgressBar) a.findViewById(R.id.progressBar_gezondheid_3);
        gezondheidBar3.setProgress(p3);
        TextView t3 = (TextView) a.findViewById(R.id.health_procent3);
        t3.setText(p3+"%");
        long tijd3 = getRemainingTime(3);
        TextView timer3 = (TextView) a.findViewById(R.id.health_timer3);
        timer3.setText(tijd3 + " minuten");

        int p4 = getProgress(4);
        ProgressBar gezondheidBar4 = (ProgressBar) a.findViewById(R.id.progressBar_gezondheid_4);
        gezondheidBar4.setProgress(p4);
        TextView t4 = (TextView) a.findViewById(R.id.health_procent4);
        t4.setText(p4+"%");
        long tijd4 = getRemainingTime(4);
        TextView timer4 = (TextView) a.findViewById(R.id.health_timer4);
        timer4.setText(tijd4 + " minuten");

        int p5 = getProgress(5);
        ProgressBar gezondheidBar5 = (ProgressBar) a.findViewById(R.id.progressBar_gezondheid_5);
        gezondheidBar5.setProgress(p5);
        TextView t5 = (TextView) a.findViewById(R.id.health_procent5);
        t5.setText(p5+"%");
        long tijd5 = getRemainingTime(5);
        TextView timer5 = (TextView) a.findViewById(R.id.health_timer5);
        timer5.setText(tijd5 + " minuten");

        int p6 = getProgress(6);
        ProgressBar gezondheidBar6 = (ProgressBar) a.findViewById(R.id.progressBar_gezondheid_6);
        gezondheidBar6.setProgress(p6);
        TextView t6 = (TextView) a.findViewById(R.id.health_procent6);
        t6.setText(p6+"%");
        long tijd6 = getRemainingTime(6);
        TextView timer6 = (TextView) a.findViewById(R.id.health_timer6);
        timer6.setText(tijd6 + " minuten");

        int p7 = getProgress(7);
        ProgressBar gezondheidBar7 = (ProgressBar) a.findViewById(R.id.progressBar_gezondheid_7);
        gezondheidBar7.setProgress(p7);
        TextView t7 = (TextView) a.findViewById(R.id.health_procent7);
        t7.setText(p7+"%");
        long tijd7 = getRemainingTime(7);
        TextView timer7 = (TextView) a.findViewById(R.id.health_timer7);
        timer7.setText(tijd7 + " minuten");

        int p8 = getProgress(8);
        ProgressBar gezondheidBar8 = (ProgressBar) a.findViewById(R.id.progressBar_gezondheid_8);
        gezondheidBar8.setProgress(p8);
        TextView t8 = (TextView) a.findViewById(R.id.health_procent8);
        t8.setText(p8+"%");
        long tijd8 = getRemainingTime(8);
        TextView timer8 = (TextView) a.findViewById(R.id.health_timer8);
        timer8.setText(tijd8 + " minuten");

        int p9 = getProgress(9);
        ProgressBar gezondheidBar9 = (ProgressBar) a.findViewById(R.id.progressBar_gezondheid_9);
        gezondheidBar9.setProgress(p9);
        TextView t9 = (TextView) a.findViewById(R.id.health_procent9);
        t9.setText(p9+"%");
        long tijd9 = getRemainingTime(9);
        TextView timer9 = (TextView) a.findViewById(R.id.health_timer9);
        timer9.setText(tijd9 + " minuten");

        //teken de progress van de algemene gezondheid bar (gemiddelde van alle andere)
        int pa = (p1 + p2 + p3 + p4 + p5 + p6 + p7 + p8 + p9) / 9;
        ProgressBar totaalGezondheidBar = (ProgressBar) a.findViewById(R.id.progressBar_algemeenGezondheid);
        totaalGezondheidBar.setProgress(pa);

    }

    protected int getProgress(int id) {
        double progress;
        double max;
        double current;

        Calendar stopDate = DateUtils.calendarFor(2014, 4, 23); // TODO haal op uit database
        long stoppedMinutes = DateUtils.GetMinutesSince(stopDate);


        switch (id) {
            case 1: current = stoppedMinutes; max = 1*24*60; break; // max in dagen
            case 2: current = stoppedMinutes; max = 365*24*60; break;
            case 3: current = stoppedMinutes; max = 2*24*60; break;
            case 4: current = stoppedMinutes; max = 4*24*60; break;
            case 5: current = stoppedMinutes; max = 2*24*60; break;
            case 6: current = stoppedMinutes; max = 40*24*60; break;
            case 7: current = stoppedMinutes; max = 14*24*60; break;
            case 8: current = stoppedMinutes; max = 3*365*24*60; break;
            case 9: current = stoppedMinutes; max = 10*365*24*60; break;

            default: current = 100; max = 100; break;

        }
        progress = (current / max) * 100;
        if(progress > 100) {
            progress = 100;
        }
        return (int) progress;
    }

    protected int getRemainingTime(int id) {
        long tijd = 0;
        double max;
        Calendar today = Calendar.getInstance();
        Calendar stopDate = DateUtils.calendarFor(2014, 4, 23); // TODO haal op uit database
        //long stoppedMinutes = DateUtils.GetMinutesSince(stopDate);
        Calendar maxDate;


        switch (id) {
            case 1: maxDate = DateUtils.calendarFor(2014, 4, 24); break; // STOPDATUM + HOEVEEL DAGEN HET DUURT
            case 2: maxDate = DateUtils.calendarFor(2015, 4, 23); break;
            case 3: maxDate = DateUtils.calendarFor(2014, 4, 25); break;
            case 4: maxDate = DateUtils.calendarFor(2014, 4, 27); break;
            case 5: maxDate = DateUtils.calendarFor(2014, 4, 25); break;
            case 6: maxDate = DateUtils.calendarFor(2014, 6, 2); break;
            case 7: maxDate = DateUtils.calendarFor(2014, 5, 6); break;
            case 8: maxDate = DateUtils.calendarFor(2017, 4, 23); break;
            case 9: maxDate = DateUtils.calendarFor(2024, 4, 23); break;

            default: maxDate = DateUtils.calendarFor(2014, 4, 25); break;

        }
        tijd = DateUtils.GetMinutesBetween(today, maxDate);
        if(tijd < 0){
            tijd = 0;
        }
        return (int) tijd;
    }

    public static final HealthProgress newInstance()
    {
        HealthProgress f = new HealthProgress();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.activity_health_progress, container, false);

        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {

                if (msg.what==UpdateProgress) {

                    // TODO make better check to see if this is the right home activity
                    Activity a = getActivity();

                    TextView t1 = (TextView) a.findViewById(R.id.health_procent1);

                    if (t1!=null) {
                        drawProgress();
                    }

                }

                super.handleMessage(msg);
            }
        };

        Thread updateProcess = new Thread(){
            public void run(){
                while (1==1) {
                    try {
                        sleep(1000);

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

        return v;

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //gets the recources

            Resources res = getResources();

            Activity homeActivity = getActivity();

            //styles the actionbar
            ActionBar bar = homeActivity.getActionBar();
            ColorDrawable color = new ColorDrawable(res.getColor(R.color.green));
            bar.setBackgroundDrawable(color);
            //sets the title
            CharSequence title = res.getString(R.string.title_activity_health_progress);
            homeActivity.setTitle(title);
            //sets viewpageindicator color
            LinePageIndicator lineIndicator = (LinePageIndicator)homeActivity.findViewById(R.id.indicator);
            lineIndicator.setSelectedColor(res.getColor(R.color.green));


        }

    }







}

