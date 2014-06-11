package com.amaze.quit.app;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class Home extends FragmentActivity {
    ViewPager pager;
    public static final String PREFS_NAME = "QuitPrefs";
    MyPageAdapter pageAdapter;
    SharedPreferences settings = null;
    int preferedFragment;
    private DrawerLayout mDrawerLayout;
    private static ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<Fragment> fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        //bind the adapter to the viewpager
        pager = (ViewPager) findViewById(R.id.viewpager);
        pager.setAdapter(pageAdapter);

        //Bind the titel indicator to the adapter
        LinePageIndicator lineIndicator = (LinePageIndicator) findViewById(R.id.indicator);
        final float density = getResources().getDisplayMetrics().density;
        lineIndicator.setViewPager(pager);
        lineIndicator.setSelectedColor(0x88FF0000);
        lineIndicator.setUnselectedColor(0xFF888888);
        lineIndicator.setStrokeWidth(4 * density);
        lineIndicator.setLineWidth(30 * density);

        //makes sure the middle fragment is shown when the activity gets first created. This is in this case the 2nd item
        settings = getSharedPreferences(PREFS_NAME, 0);
        preferedFragment = settings.getInt("pref_frag", 2);
        pager.setCurrentItem(preferedFragment);


        //initialises the navigation drawer
        navDrawer();


        //makes sure all the stats are updated
        UpdateStats updater = new UpdateStats(this);
        updater.updateAchievements();


    }


    //gets the 4 fragments
    private List<Fragment> getFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(Achievements.newInstance(0));
        fList.add(Progress.newInstance(1));
        fList.add(Product.newInstance(2));
        fList.add(HealthProgress.newInstance(3));


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
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

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
    private void launchActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    private void navDrawer() {
        String[] mNavTitles = getResources().getStringArray(R.array.nav_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // nav drawer icons from resources
        TypedArray navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        //makes the drawer items
        ArrayList<NavDrawerItem> navDrawerItems = new ArrayList<NavDrawerItem>();
        // adding nav drawer items to array
        //Achievement
        navDrawerItems.add(new NavDrawerItem(mNavTitles[0], navMenuIcons.getResourceId(0, -1)));
        //Vooruitgang
        navDrawerItems.add(new NavDrawerItem(mNavTitles[1], navMenuIcons.getResourceId(1, -1)));
        //Product
        navDrawerItems.add(new NavDrawerItem(mNavTitles[2], navMenuIcons.getResourceId(2, -1)));
        //Gezondheid
        navDrawerItems.add(new NavDrawerItem(mNavTitles[3], navMenuIcons.getResourceId(3, -1)));
        //Instellingen
        navDrawerItems.add(new NavDrawerItem(mNavTitles[4], navMenuIcons.getResourceId(4, -1)));

        navMenuIcons.recycle();

        // Set the adapter for the list view
        mDrawerList.setAdapter(new NavDrawerListAdapter(getApplicationContext(), navDrawerItems));
        // Set the list's click listener
        mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                //de positie van settings
                if (position == 4) {
                    Intent intent = new Intent(getApplicationContext(), Settings.class);
                    startActivity(intent);

                } else {
                    pager.setCurrentItem(position);
                }
                mDrawerLayout.closeDrawers();

            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        );
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        getActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelectedNav(pager.getCurrentItem());
        UpdateStats updater = new UpdateStats(this);
        updater.updateQuit();
        updater.updateAchievements();


    }


    public static void setSelectedNav(int position) {
        mDrawerList.setItemChecked(position, true);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }


}
