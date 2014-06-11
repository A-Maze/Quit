package com.amaze.quit.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.database.DatabaseUtils;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;


public class Achievements extends Fragment {
    static int position;
    private UserVisibilityEvent uservisibilityevent;

    public static final Achievements newInstance(int i) {
        Achievements f = new Achievements();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        position = i;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_achievements, container, false);
        return v;
    }

   @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        updateCompleted();
  }

    @Override
    public void onResume(){
        super.onResume();
        updateCompleted();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //implements the main method what every fragment should do when it's visible
            uservisibilityevent.viewIsVisible(getActivity(),position,"orange","title_fragment_achievements");
            //helps the nav bar realise what is up
            Home.setSelectedNav(position);


        }

    }

    private void updateCompleted(){
        DatabaseHandler db = new DatabaseHandler(getActivity());
        Drawable checkmark = getResources().getDrawable(R.drawable.check_mark_green);
        String[] imageArray = {"first_steps","take_a_deep_breath","kaching","money_in_the_bank","extra_life","invincible","return_on_investment","worth_it","one_down","not_hard"};
        for(int i = 1; i <= db.getChallengesAmount(); i++){
            int behaald = db.getChallenge(i).getBehaald();
            if(behaald > 0){
                int titleID = getResources().getIdentifier("tvC" + i,"id",getActivity().getPackageName());
                int contextID = getResources().getIdentifier("tvCC" + i,"id",getActivity().getPackageName());
                TextView achievementContext = (TextView) getActivity().findViewById(contextID);
                int leftImage = getResources().getIdentifier(imageArray[i-1] + "_achievement_complete", "drawable",getActivity().getPackageName());
                Drawable completedDrawable = getResources().getDrawable(leftImage);
                achievementContext.setCompoundDrawablesWithIntrinsicBounds(completedDrawable,null,checkmark,null);
                TextView achievementTitle = (TextView) getActivity().findViewById(titleID);
                achievementTitle.setTextColor(getResources().getColor(R.color.green));
            }
            else{
                int titleID = getResources().getIdentifier("tvC" + i,"id",getActivity().getPackageName());
                int contextID = getResources().getIdentifier("tvCC" + i,"id",getActivity().getPackageName());
                TextView achievementContext = (TextView) getActivity().findViewById(contextID);
                int leftImage = getResources().getIdentifier(imageArray[i-1] + "_achievement", "drawable",getActivity().getPackageName());
                Drawable notCompletedDrawable = getResources().getDrawable(leftImage);
                achievementContext.setCompoundDrawablesWithIntrinsicBounds(notCompletedDrawable,null,null,null);
                TextView achievementTitle = (TextView) getActivity().findViewById(titleID);
                achievementTitle.setTextColor(getResources().getColor(R.color.black));
            }
        }
       db.close();
    }
}