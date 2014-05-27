package com.amaze.quit.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.LinePageIndicator;


public class Product extends Fragment {
    static int position;

    public static final Product newInstance(int i) {
        Product f = new Product();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        position = i;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product, container, false);
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
            CharSequence title = res.getString(R.string.title_activity_product);
            homeActivity.setTitle(title);
            //sets viewpageindicator color
            LinePageIndicator lineIndicator = (LinePageIndicator)homeActivity.findViewById(R.id.indicator);
            lineIndicator.setSelectedColor(res.getColor(R.color.blue));
            //helps the nav bar realise what is up
            Home.setSelectedNav(position);


        }

    }
}