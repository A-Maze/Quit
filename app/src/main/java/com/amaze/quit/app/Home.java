package com.amaze.quit.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class Home extends FragmentActivity  {
    MyPageAdapter pageAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<Fragment> fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        //bind the adapter to the viewpager
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

        //makes sure the middle fragment is shown when the activity gets first created. This is in this case the 2nd item
        pager.setCurrentItem(2, false);

     };

    //gets the 3 fragments
    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();

        fList.add(Achievements.newInstance());
        fList.add(Progress.newInstance());
        fList.add(Product.newInstance());
        fList.add(HealthProgress.newInstance());



        return fList;
    }

    // makes a menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    // fills the menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                launchActivity(Setup.class);
                return true;
            case R.id.developer_settings:
                launchActivity(MainActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //launches specified activity
    private void launchActivity(Class activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }




}
