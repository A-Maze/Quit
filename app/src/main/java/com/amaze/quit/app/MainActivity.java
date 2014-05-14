package com.amaze.quit.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.content.Intent;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.smokeBehaviours:
                startSmokingBehaviours();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startSmokingBehaviours() {
        Intent intent = new Intent(this, SetupBrandAmount.class);
        startActivity(intent);
    }

    public void runAnyActivity(View view) {
        Intent intent = new Intent(this, SetupBrandAmount.class);
        startActivity(intent);
    }





}
