package com.amaze.quit.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ListActivity {

    String classes[] = {"Home", "Setup"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, classes));
    }


    public void startAnActivity(Class activiteit) {
        Intent intent = new Intent(this, activiteit);
        startActivity(intent);
    }

    public void runAnyActivity(View view) {
        Intent intent = new Intent(this, SetupQuitDate.class);
        startActivity(intent);
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String classNaam = classes[position];
        Class ourClass;
        try {
            ourClass = Class.forName("com.amaze.quit.app." + classNaam);
            Intent ourIntent = new Intent(this, ourClass);
            startActivity(ourIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
