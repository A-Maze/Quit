package com.amaze.quit.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class ProductDetail extends ActionBarActivity {
    public final static String TAG = "ProductDetail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        TextView tvTitle = (TextView) findViewById(R.id.tvProductDetailTitle);
        TextView tvDesc = (TextView) findViewById(R.id.tvDescription);
        ImageView ivProduct = (ImageView) findViewById(R.id.ivProductDetailImage);


        Intent fromListView = getIntent();
        String id = fromListView.getStringExtra("id");
        Bundle extras = getIntent().getExtras();
        String image = extras.getString("image");
        String title = extras.getString("title");
        Double price = extras.getDouble("price");
        String desc = extras.getString("description");

        tvTitle.setText(title);
        tvDesc.setText(desc);
       // TextView tv = (TextView) findViewById(R.id.tvpdtest);
       // tv.setText(id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.product_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
