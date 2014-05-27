package com.amaze.quit.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;

import java.util.Calendar;


public class Progress extends Fragment {



    public static final Progress newInstance()
    {
        Progress f = new Progress();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        return f;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_progress, container, false);

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
            ColorDrawable color = new ColorDrawable(res.getColor(R.color.red));
            bar.setBackgroundDrawable(color);
            //sets the title
            CharSequence title = res.getString(R.string.title_activity_progress);
            homeActivity.setTitle(title);
            //sets viewpageindicator color
            LinePageIndicator lineIndicator = (LinePageIndicator)homeActivity.findViewById(R.id.indicator);
            lineIndicator.setSelectedColor(res.getColor(R.color.red));

            updateVooruitgang();
        }

    }

    private void updateVooruitgang() {
        TextView dayProgress;
        dayProgress = (TextView) getActivity().findViewById(R.id.tvDayProgress);
        DatabaseHandler db = new DatabaseHandler(getActivity());

        Calendar quitDate = Calendar.getInstance();
        quitDate.set(db.getUser(1).getQuitYear(), db.getUser(1).getQuitMonth(), db.getUser(1).getQuitDay());

        Calendar vandaag = Calendar.getInstance();


        long diff = vandaag.getTimeInMillis() - quitDate.getTimeInMillis(); //result in millis
        long days = diff / (24 * 60 * 60 * 1000);
        dayProgress.setText(days + " Dagen");
     }
    }



