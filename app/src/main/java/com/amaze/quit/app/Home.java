package com.amaze.quit.app;


import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.CirclePageIndicator;
import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.UnderlinePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class Home extends FragmentActivity {
    MyPageAdapter pageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<Fragment> fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);

        //Bind the title indicator to the adapter
        LinePageIndicator lineIndicator = (LinePageIndicator)findViewById(R.id.indicator);
        final float density = getResources().getDisplayMetrics().density;
        lineIndicator.setViewPager(pager);
        lineIndicator.setSelectedColor(0x88FF0000);
        lineIndicator.setUnselectedColor(0xFF888888);
        lineIndicator.setStrokeWidth(4 * density);
        lineIndicator.setLineWidth(30 * density);

     };









    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(Achievements.newInstance());
        fList.add(Progress.newInstance());
        fList.add(HealthProgress.newInstance());



        return fList;
    }




}
