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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        updateCompleted();
    }

    @Override
    public void onResume() {
        super.onResume();
        //makes sure it handles the completed achievements
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
        //new database handler
        DatabaseHandler db = new DatabaseHandler(getActivity());

        //gets the drawables and the layout
        Drawable checkmark = getResources().getDrawable(R.drawable.check_mark_green);
        Drawable checkmarkGrey = getResources().getDrawable(R.drawable.check_mark_grey);
        String[] imageArray = {"first_steps", "take_a_deep_breath", "kaching", "money_in_the_bank", "extra_life", "invincible", "return_on_investment", "worth_it", "one_down", "not_hard"};
        LinearLayout theLayout = (LinearLayout) getActivity().findViewById(R.id.llAchievements);

        //loops through the achievements to see if they're completed
        for (int i = 1; i <= db.getChallengesAmount(); i++) {
            int behaald = db.getChallenge(i).getBehaald();
            int titleID = getResources().getIdentifier("tvC" + i, "id", getActivity().getPackageName());
            int contextID = getResources().getIdentifier("tvCC" + i, "id", getActivity().getPackageName());
            TextView achievementContext = (TextView) getActivity().findViewById(contextID);
            TextView achievementTitle = (TextView) getActivity().findViewById(titleID);
            String achievementTitleString = "" + achievementTitle.getText();
            String achievementContextString = "" + achievementContext.getText();

            //if they're completed it'll handle them properly
            if (behaald > 0) {


                int leftImage = getResources().getIdentifier(imageArray[i - 1] + "_achievement_complete", "drawable", getActivity().getPackageName());
                Drawable completedDrawable = getResources().getDrawable(leftImage);
                achievementContext.setCompoundDrawablesWithIntrinsicBounds(completedDrawable, null, checkmark, null);
                achievementTitle.setTextColor(getResources().getColor(R.color.green));

                //if the view has no share button but is completed -> add one
                if (getActivity().findViewById(i) == null) {
                    //gives every completed achievement a share button
                    int index = ((ViewGroup) achievementContext.getParent()).indexOfChild(achievementContext);
                    theLayout.addView(shareTwitter(i, achievementTitleString, achievementContextString), index + 1);
                }


            } else {
                //handles the achievements that aren't completed. this is necesarry if they were completed earlier but the user changed their quit date.
                int leftImage = getResources().getIdentifier(imageArray[i - 1] + "_achievement", "drawable", getActivity().getPackageName());
                Drawable notCompletedDrawable = getResources().getDrawable(leftImage);
                achievementContext.setCompoundDrawablesWithIntrinsicBounds(notCompletedDrawable, null, checkmarkGrey, null);
                achievementTitle.setTextColor(getResources().getColor(R.color.black));
            }
        }
        //closes the database
        db.close();
    }

    //makes the twitter button
    private ImageButton shareTwitter(int i, String title, String context) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.RIGHT;
        lp.rightMargin = giveDP(32f);
        ImageButton shareButton = new ImageButton(getActivity());
        shareButton.setImageResource(R.drawable.share_twitter);
        shareButton.setLayoutParams(lp);
        shareButton.setBackgroundColor(Color.TRANSPARENT);
        shareButton.setId(i);
        shareButton.setOnClickListener(new onShare(title, context));
        return shareButton;
    }

    //adds the onclick listener to the image button. link is given in the parameters
    public class onShare implements View.OnClickListener {

        String title;
        String context;

        public onShare(String title, String context) {
            this.title = title;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            String message = "Ik heb het achievement '" + title + "': '" + context + "' behaald met de app!&hashtags=12Quit";
            String tweetUrl = "https://twitter.com/intent/tweet?text=" + message;
            Uri uri = Uri.parse(tweetUrl);
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }

    }

    ;

    //gives back the value in DP
    private int giveDP(float dp) {
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        return pixels;
    }
}