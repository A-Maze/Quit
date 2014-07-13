package com.amaze.quit.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


public class Progress extends Fragment {
    static int position;
    private UserVisibilityEvent uservisibilityevent;
    private UpdateStats updatestats = new UpdateStats(getActivity());
    String message;

    public static final Progress newInstance(int i) {
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

    /*update als je op scherm komt*/
    @Override
    public void onResume() {
        super.onResume();
        updateVooruitgang();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //implements the main method what every fragment should do when it's visible
            uservisibilityevent.viewIsVisible(getActivity(), position, "red", "title_activity_progress");
            updateVooruitgang();
        }

    }

    /* update van progress als het scherm gemaakt wordt */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        updateVooruitgang();
    }

    /* update functie */
    private void updateVooruitgang() {
        TextView dayProgress;
        TextView moneyInTheBank;
        TextView extraDagen;
        TextView level;
        extraDagen = (TextView) getActivity().findViewById(R.id.tvExtraDagen);
        dayProgress = (TextView) getActivity().findViewById(R.id.tvDagenZonderRoken);
        moneyInTheBank = (TextView) getActivity().findViewById(R.id.tvBespaardeGeld);
        TextView nietGerookt = (TextView) getActivity().findViewById(R.id.tvNietGerookteSigaretten);
        // level = (TextView) getActivity().findViewById(R.id.tvLevel);
        TextView levelDesc = (TextView) getActivity().findViewById(R.id.tvLevel);

        long days = updatestats.getDaysQuit();
        //catches the nullpointerexception
        try {
            dayProgress.setText(days + " Dagen");

            float bespaardeMoneys = updatestats.getSavedMoney();
            String bespaardeMoneysString = String.format("%.2f",bespaardeMoneys);

            moneyInTheBank.setText("€" + bespaardeMoneysString); // bespaarde geld.
            DatabaseHandler db = new DatabaseHandler(getActivity());
            float extraDagenTeLeven = updatestats.getExtraDagenTeLeven();
            extraDagen.setText((int) extraDagenTeLeven + " extra dagen te leven");
            int userLevel = updatestats.getUserLevel();
            //level.setText("Level " +   userLevel);
            String Titel = db.getLevel(userLevel).getTitel();
            levelDesc.setText(Titel);
            int nietGerooktSig = (int) days * db.getUser(1).getPerDag();
            nietGerookt.setText("" + nietGerooktSig);
            socialMedia(days, bespaardeMoneysString,nietGerooktSig);
        } catch (NullPointerException e) {
        }






    }

    private void socialMedia(long days, String bespaardeMoney, int nietGerooktSig){
        message = "Ik heb al " +  days + " dagen niet gerookt. Dit zijn " + nietGerooktSig + " niet gerookte sigaretten en heeft me €" + bespaardeMoney + " bespaard!  &hashtags=12Quit";
        ImageButton tweet = (ImageButton) getActivity().findViewById(R.id.twitter_share);
        tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetUrl = "https://twitter.com/intent/tweet?text=" + message;
                Uri uri = Uri.parse(tweetUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

    }
}









