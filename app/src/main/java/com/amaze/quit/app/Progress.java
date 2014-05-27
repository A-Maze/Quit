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
    static int position;


    public static final Progress newInstance(int i)
    {
        Progress f = new Progress();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        position = i;
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
            Home.setSelectedNav(position);
            updateVooruitgang();
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        updateVooruitgang();
    }

    private void updateVooruitgang() {
        TextView dayProgress;
        TextView moneyInTheBank;
        TextView extraDagen;
        extraDagen = (TextView) getActivity().findViewById(R.id.tvSparedDays);
        dayProgress = (TextView) getActivity().findViewById(R.id.tvDayProgress);
        moneyInTheBank = (TextView) getActivity().findViewById(R.id.tvSparedMoney);
        DatabaseHandler db = new DatabaseHandler(getActivity());

        Calendar quitDate = Calendar.getInstance();
        quitDate.set(db.getUser(1).getQuitYear(), db.getUser(1).getQuitMonth(), db.getUser(1).getQuitDay());

        Calendar vandaag = Calendar.getInstance();


        long diff = vandaag.getTimeInMillis() - quitDate.getTimeInMillis(); //result in millis
        long days = diff / (24 * 60 * 60 * 1000);
        //fixed the nullpointer exception but this needs to be fixed properly
        if(dayProgress != null) {
            dayProgress.setText(days + " Dagen");
        }
        float bespaardeMoneys =  (db.getSigaret(db.getUser(1).getsID()).getAantal() / db.getSigaret(db.getUser(1).getsID()).getPrijs()) * db.getUser(1).getPerDag() * days;
        bespaardeMoneys = Math.round(bespaardeMoneys) * 100;
        bespaardeMoneys = bespaardeMoneys /100;
        //fixed the nullpointer exception but this needs to be fixed properly
        if(moneyInTheBank != null) {
            moneyInTheBank.setText("€" + bespaardeMoneys); // bespaarde geld.
        }
        float extraDagenTeLeven = db.getUser(1).getPerDag() * days * 28 / 1440;
        //fixed the nullpointer exception but this needs to be fixed properly
        if(extraDagen != null) {
            extraDagen.setText((int) extraDagenTeLeven + " extra dagen te leven");
        }



     }
    }



