package com.amaze.quit.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddSmoke extends ActionBarActivity {
    EditText etName, etPrice, etAmount;
    TextView tvAmount;
    String type,name;
    int amount;
    Float price;
    Intent setupIntent;
    Button bAddSmoke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_smoke);
        setupIntent = getIntent();
        type = setupIntent.getStringExtra("type");
        etName = (EditText) findViewById(R.id.etBrandName);
        etAmount = (EditText) findViewById(R.id.etBrandAantal);
        etPrice = (EditText) findViewById(R.id.etBrandPrice);
        tvAmount = (TextView) findViewById(R.id.tvBrandAmount);
        bAddSmoke = (Button) findViewById(R.id.bAddSmoke);


        if(type.equals("sigaretten")){
            Log.d("test","I'm being called!");
            tvAmount.setText(R.string.setupPackAmount);
        }
        else if(type.equals("shag")){
            tvAmount.setText(R.string.gram);
        }
        addSmoke();
    }

    protected void addSmoke(){
        bAddSmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(getBaseContext());
                name = etName.getText().toString();
                price = Float.parseFloat(etPrice.getText().toString());
                amount = Integer.parseInt(etAmount.getText().toString());
                if(type.equals("sigaretten")){
                    int id = db.getSigarettenAmount() + 1;
                    db.addSigarette(new Sigaretten(id,2f,name,amount,4,price));
                }
                else if(type.equals("shag")){
                    int id = db.getShagAmount() + 1;
                    db.addShag(new Shag(id,name,amount,price));
                }
                db.close();
                finish();
            }
        });

    }



}
