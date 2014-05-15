package com.amaze.quit.app;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends ListActivity {

    String classes[] = {"SetupBrandAmount","SetupQuitDate", "HealthProgress", "example3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
        setListAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,classes));
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
        Intent intent = new Intent(this, SetupQuitDate.class);
        startActivity(intent);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String classNaam = classes[position];
        Class ourClass = null;
        try {
            ourClass = Class.forName("com.amaze.quit.app." + classNaam);
            Intent ourIntent = new Intent(this,ourClass);
            startActivity(ourIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }



}
