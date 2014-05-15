package com.amaze.quit.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class HealthProgress extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_progress);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.health_progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.iSmokeBehaviours:
                startAnActivity(SetupBrandAmount.class);
            case R.id.iQuitDate:
                startAnActivity(SetupQuitDate.class);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startAnActivity(Class activiteit) {
        Intent intent = new Intent(this, activiteit);
        startActivity(intent);
    }

}
