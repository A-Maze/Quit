package com.amaze.quit.app;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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

public class Home extends FragmentActivity  {
    ViewPager pager;
    public static final String PREFS_NAME = "QuitPrefs";
    MyPageAdapter pageAdapter;
    SharedPreferences settings = null;
    int preferedFragment;
    private String[] mNavTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        List<Fragment> fragments = getFragments();

        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        //bind the adapter to the viewpager
        pager = (ViewPager)findViewById(R.id.viewpager);
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
        settings = getSharedPreferences(PREFS_NAME, 0);
        preferedFragment = settings.getInt("pref_frag", 2);
        pager.setCurrentItem(preferedFragment);

        //initialises the navigation drawer
        navDrawer();


    };

    public void setPreferedFragment(int i){
        settings.edit().putInt("pref_frag",i).commit();
    }

    //gets the 4 fragments
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
            case R.id.setPreferedFragment:
                setFragment();
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //launches specified activity
    private void launchActivity(Class activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }

    private void setFragment(){
        Resources resources = getResources();
        final CharSequence[] items = {resources.getString(R.string.title_fragment_achievements), resources.getString(R.string.title_activity_progress), resources.getString(R.string.title_activity_product), resources.getString(R.string.title_activity_health_progress)};
        final int[] numbers = {0,1,2,3};
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle("kies uw gewenste startscherm")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        setPreferedFragment(numbers[which]);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void navDrawer(){
        mNavTitles = getResources().getStringArray(R.array.nav_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mNavTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new ListView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id){
                pager.setCurrentItem(position);
                mDrawerLayout.closeDrawers();
            }
        });
    }




}
