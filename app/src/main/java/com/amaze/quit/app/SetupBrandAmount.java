package com.amaze.quit.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


public class SetupBrandAmount extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_brand_amount);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.setup_brand_amount, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            //menu item van rook gewoontes
            case R.id.smokeBehaviours:
                startSmokingBehaviours();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Starts the SetupBrandAmount activity.
    public void startSmokingBehaviours() {
        Intent intent = new Intent(this, SetupBrandAmount.class);
        startActivity(intent);
    }
}
