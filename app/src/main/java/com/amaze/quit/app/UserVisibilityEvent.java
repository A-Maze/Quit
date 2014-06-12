package com.amaze.quit.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;

import com.viewpagerindicator.LinePageIndicator;

/**
 * Created by Robin on 30-5-2014.
 */
public class UserVisibilityEvent {

    //method that takes care of everything that needs to be done when the fragment becomes visible
    //takes context(getActivity()), the position of the fragment, the color it's actionbar should be and the link to it's titel.
    public static void viewIsVisible(Context context, int position, String colorName, String name) {

        //gets the recources
        Resources res = context.getResources();

        Activity homeActivity = (Activity) context;

        //finds the color
        int colorResource = res.getIdentifier(colorName, "color", context.getPackageName());
        //styles the actionbar
        ActionBar bar = homeActivity.getActionBar();
        ColorDrawable color = new ColorDrawable(res.getColor(colorResource));
        bar.setBackgroundDrawable(color);
        //sets the titel
        int titleResource = res.getIdentifier(name, "string", context.getPackageName());
        CharSequence title = res.getString(titleResource);
        homeActivity.setTitle(title);
        //sets viewpageindicator color
        LinePageIndicator lineIndicator = (LinePageIndicator) homeActivity.findViewById(R.id.indicator);
        lineIndicator.setSelectedColor(res.getColor(colorResource));
        //helps the nav bar realise what is up
        Home.setSelectedNav(position);


    }

}
