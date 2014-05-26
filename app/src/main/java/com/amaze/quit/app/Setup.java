package com.amaze.quit.app;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class Setup extends FragmentActivity {

    MyPageAdapter pageAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        List<Fragment> fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);

        ViewPager pager = (ViewPager)findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);

        //Bind the title indicator to the adapter
        LinePageIndicator lineIndicator = (LinePageIndicator)findViewById(R.id.indicator);
        final float density = getResources().getDisplayMetrics().density;
        lineIndicator.setViewPager(pager);
        lineIndicator.setSelectedColor(getResources().getColor(R.color.blue));
        lineIndicator.setUnselectedColor(0xFF888888);
        lineIndicator.setStrokeWidth(4 * density);
        lineIndicator.setLineWidth(30 * density);




    }


    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(SetupQuitDate.newInstance());
        fList.add(SetupBrandAmount.newInstance());


        return fList;
    }

    private class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }
        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }
    }






}
