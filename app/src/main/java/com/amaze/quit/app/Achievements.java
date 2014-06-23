package com.amaze.quit.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.DatabaseUtils;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;


public class Achievements extends Fragment {
    static int position;
    private UserVisibilityEvent uservisibilityevent;
    static  String achievementContextString;
    static  String achievementTitleString;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        updateCompleted();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateCompleted();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //implements the main method what every fragment should do when it's visible
            uservisibilityevent.viewIsVisible(getActivity(), position, "orange", "title_fragment_achievements");
            //helps the nav bar realise what is up
            Home.setSelectedNav(position);


        }

    }

    private void updateCompleted() {
        DatabaseHandler db = new DatabaseHandler(getActivity());
        Drawable checkmark = getResources().getDrawable(R.drawable.check_mark_green);
        Drawable checkmarkGrey = getResources().getDrawable(R.drawable.check_mark_grey);
        String[] imageArray = {"first_steps", "take_a_deep_breath", "kaching", "money_in_the_bank", "extra_life", "invincible", "return_on_investment", "worth_it", "one_down", "not_hard"};
        LinearLayout theLayout = (LinearLayout) getActivity().findViewById(R.id.llAchievements);
        for (int i = 1; i <= db.getChallengesAmount(); i++) {
            int behaald = db.getChallenge(i).getBehaald();
            if (behaald > 0) {
                int titleID = getResources().getIdentifier("tvC" + i, "id", getActivity().getPackageName());
                int contextID = getResources().getIdentifier("tvCC" + i, "id", getActivity().getPackageName());


                TextView achievementContext = (TextView) getActivity().findViewById(contextID);

                int leftImage = getResources().getIdentifier(imageArray[i - 1] + "_achievement_complete", "drawable", getActivity().getPackageName());
                Drawable completedDrawable = getResources().getDrawable(leftImage);
                achievementContext.setCompoundDrawablesWithIntrinsicBounds(completedDrawable, null, checkmark, null);
                TextView achievementTitle = (TextView) getActivity().findViewById(titleID);
                achievementTitle.setTextColor(getResources().getColor(R.color.green));
                //context and title for the share button
                achievementTitleString = achievementTitle.getText().toString();
                achievementContextString = achievementContext.getText().toString();
                if(getActivity().findViewById(i) == null) {
                    //gives every completed achievement a share button
                    int index = ((ViewGroup) achievementContext.getParent()).indexOfChild(achievementContext);
                    theLayout.addView(shareTwitter(i), index + 1);
                }



            } else {
                int titleID = getResources().getIdentifier("tvC" + i, "id", getActivity().getPackageName());
                int contextID = getResources().getIdentifier("tvCC" + i, "id", getActivity().getPackageName());
                TextView achievementContext = (TextView) getActivity().findViewById(contextID);
                int leftImage = getResources().getIdentifier(imageArray[i - 1] + "_achievement", "drawable", getActivity().getPackageName());
                Drawable notCompletedDrawable = getResources().getDrawable(leftImage);
                achievementContext.setCompoundDrawablesWithIntrinsicBounds(notCompletedDrawable, null, checkmarkGrey, null);
                TextView achievementTitle = (TextView) getActivity().findViewById(titleID);
                achievementTitle.setTextColor(getResources().getColor(R.color.black));
            }
        }
        db.close();
    }

    private ImageButton shareTwitter(int i){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity= Gravity.RIGHT;
        lp.rightMargin = giveDP(32f);
        ImageButton shareButton = new ImageButton(getActivity());
        shareButton.setImageResource(R.drawable.share_twitter);
        shareButton.setLayoutParams(lp);
        shareButton.setBackgroundColor(Color.TRANSPARENT);
        shareButton.setId(i);
        shareButton.setOnClickListener(onShare);
        return shareButton;
    }

    private  View.OnClickListener onShare = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String message = "Ik heb het achievement '" + achievementTitleString + "': '" + achievementContextString + "' behaald met de app!&hashtags=12Quit";
            String tweetUrl = "https://twitter.com/intent/tweet?text="+ message;
            Uri uri = Uri.parse(tweetUrl);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

    };

    //gives back the value in DP
    private int giveDP(float dp) {
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        return pixels;
    }
}