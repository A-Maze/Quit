package com.amaze.quit.app;


import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.viewpagerindicator.LinePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class Home extends FragmentActivity {
    ViewPager pager;
    public static final String PREFS_NAME = "QuitPrefs";
    MyPageAdapter pageAdapter;
    private static SharedPreferences settings = null;
    int preferedFragment;
    private DrawerLayout mDrawerLayout;
    private static ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
     //the home activity, which is the main activity of the app
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
        customActionBar();



        //makes sure all the stats are updated
        UpdateStats updater = new UpdateStats(this);
        updater.updateAchievements();
        boughtProduct();
        saveNewProduct();


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
                launchActivity(Settings.class);
                return true;
            case R.id.action_facebook:
                facebook();
                return true;
            case R.id.action_twitter:
                twitter();
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        setSelectedNav(pager.getCurrentItem());
        UpdateStats updater = new UpdateStats(this);
        updater.updateQuit();
        updater.updateAchievements();
        boughtProduct();
        saveNewProduct();



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

    //creates the dialog if the user wants to save up for another product
    private void saveNewProduct(){
        if(settings.getBoolean("newProduct",false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setMessage(R.string.new_product_dialog)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                           Intent intent = new Intent(getApplicationContext(),ChooseProductHost.class);
                           startActivity(intent);
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            settings.edit().putBoolean("newProduct", false).commit();
                            dialog.dismiss();
                        }
                    });
            // Create the AlertDialog object and show it
            builder.create();
            AlertDialog newProductDialog = builder.create();
            newProductDialog.show();
        }


    }

    //asks the user if he or she has bought the product, if yes it'll show the new product dialog
    private void boughtProduct(){
        if(settings.getBoolean("boughtProduct",false)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
            builder.setMessage(R.string.bought_product)
                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //adds the saved amount up to the spent amount of the user since the user just bought the product
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            User user = db.getUser(1);
                            Product product = new Product();
                            user.setSpentAmount(user.getSpentAmount() + Math.round(product.getAmountLeft()));
                            db.updateUser(user);


                            db.close();

                            settings.edit().putBoolean("newProduct", true).commit();
                            settings.edit().putBoolean("boughtProduct", false).commit();
                            dialog.dismiss();
                            saveNewProduct();
                        }
                    })
                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            settings.edit().putBoolean("newProduct", false).commit();
                            settings.edit().putBoolean("boughtProduct", false).commit();
                            dialog.dismiss();
                        }
                    });
            // Create the AlertDialog object and show it
            builder.create();
            AlertDialog newProductDialog = builder.create();
            newProductDialog.show();
        }


    }

    //social media methods
    private void facebook(){
        Intent facebook;
        try {
            getBaseContext().getPackageManager()
                    .getPackageInfo("com.facebook.katana", 0); //checks if facebook is installed
            facebook = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://profile/1458260817748999")); //if this is the case open the url in the facebook app
        } catch (Exception e) {
            facebook = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://touch.facebook.com/profile.php?id=1458260817748999")); //if this goes wrong just do it in browser
        }
        startActivity(facebook);
    }

    private void twitter(){
        Intent twitter;
        if(isAppInstalled("com.twitter.android")) {
            twitter = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?screen_name=12Quit"));


        }else{
            twitter = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/12Quit"));
        }
        startActivity(twitter);

    }

    //creates a custom action bar with social media icons
    private void customActionBar(){

        ActionBar mActionBar = getActionBar();

        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_action_bar, null);
        ImageButton twitterButton = (ImageButton) mCustomView.findViewById(R.id.bTwitter);
        ImageButton facebookButton = (ImageButton) mCustomView.findViewById(R.id.bFacebook);
        twitterButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                twitter();
            }
        });
        facebookButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                facebook();
            }
        });
        mActionBar.setDisplayShowHomeEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setDisplayShowTitleEnabled(true);
        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }


    //to check if twitter is installed
    private boolean isAppInstalled(String packageName) {
        PackageManager pm = getPackageManager();
        boolean installed = false;
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }


}
