package com.amaze.quit.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.LinePageIndicator;


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
            ColorDrawable color = new ColorDrawable(res.getColor(R.color.blue));
            bar.setBackgroundDrawable(color);
            //sets the title
            CharSequence title = res.getString(R.string.title_activity_progress);
            homeActivity.setTitle(title);
            //sets viewpageindicator color
            LinePageIndicator lineIndicator = (LinePageIndicator)homeActivity.findViewById(R.id.indicator);
            lineIndicator.setSelectedColor(res.getColor(R.color.blue));
        }

    }







}
