package com.amaze.quit.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;


public class SetupBrandAmount extends ActionBarActivity {

    private RadioButton rbSigaretten;
    private RadioButton rbShag;
    private EditText etDayAmount;
    private EditText etPackAmount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_brand_amount);

        rbSigaretten = (RadioButton) findViewById(R.id.rbSigaretten);
        rbShag = (RadioButton) findViewById(R.id.rbShag);
        etDayAmount = (EditText) findViewById(R.id.etDayAmount);
        etPackAmount = (EditText) findViewById(R.id.etPackAmount);
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
