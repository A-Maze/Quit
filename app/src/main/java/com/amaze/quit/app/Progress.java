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
    private UserVisibilityEvent uservisibilityevent;
    private UpdateStats updatestats = new UpdateStats(getActivity());
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
            //implements the main method what every fragment should do when it's visible
            uservisibilityevent.viewIsVisible(getActivity(),position,"red","title_activity_progress");
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
        TextView level;
        extraDagen = (TextView) getActivity().findViewById(R.id.tvSparedDays);
        dayProgress = (TextView) getActivity().findViewById(R.id.tvDayProgress);
        moneyInTheBank = (TextView) getActivity().findViewById(R.id.tvSparedMoney);
        level = (TextView) getActivity().findViewById(R.id.tvLevel);
        long days = updatestats.getDaysQuit();
        //catches the nullpointerexception
        try {
            dayProgress.setText(days + " Dagen");

            float bespaardeMoneys = updatestats.getSavedMoney();
            bespaardeMoneys = Math.round(bespaardeMoneys) * 100;
            bespaardeMoneys = bespaardeMoneys / 100;

            moneyInTheBank.setText("€" + bespaardeMoneys); // bespaarde geld.
            DatabaseHandler db = new DatabaseHandler(getActivity());
            float extraDagenTeLeven = db.getUser(1).getPerDag() * days * 28 / 1440;
            extraDagen.setText((int) extraDagenTeLeven + " extra dagen te leven");
            level.setText("Level " +   db.getUser(1).getLevel());
        }
        catch(NullPointerException e){
            return;
        }



     }
    }



