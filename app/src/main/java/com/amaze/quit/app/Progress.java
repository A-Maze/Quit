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
        //gets the recources
        Resources res = getResources();

        Activity homeActivity = getActivity();

        //styles the actionbar
        ActionBar bar = homeActivity.getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable("#55acee"));

        //sets the title
        CharSequence title = res.getString(R.string.title_activity_health_progress);
        homeActivity.setTitle(title);

        View v = inflater.inflate(R.layout.activity_progress, container, false);
        return v;
    }




}
