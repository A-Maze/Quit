package com.amaze.quit.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Robin on 21-5-2014.
 */
//handles the swiping
public class MyPageAdapter extends FragmentPagerAdapter {
    public static final String[] CONTENT = new String[]{"Achievements", "Voortgang","Product", "Gezondheid"};
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


    @Override
    public CharSequence getPageTitle(int position) {
        return MyPageAdapter.CONTENT[position % CONTENT.length];
    }
}
