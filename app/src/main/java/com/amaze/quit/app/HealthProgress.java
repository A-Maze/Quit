package com.amaze.quit.app;


import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HealthProgress extends Fragment {


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
        Activity homeActivity = getActivity();

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
        }

    }


    protected void drawProgress() {


    }






}
