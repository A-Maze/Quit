package com.amaze.quit.app;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ProductDetail extends ActionBarActivity {
    public final static String TAG = "ProductDetail";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        TextView tvTitle = (TextView) findViewById(R.id.tvProductDetailTitle);
        TextView tvDesc = (TextView) findViewById(R.id.tvDescription);
        final ImageView ivProduct = (ImageView) findViewById(R.id.ivProductDetailImage);


        Intent fromListView = getIntent();
        String id = fromListView.getStringExtra("id");
        Bundle extras = getIntent().getExtras();
        final String image = extras.getString("image");
        String title = extras.getString("title");
        Double price = extras.getDouble("price");
        String desc = extras.getString("description");

        tvTitle.setText(title);
        tvDesc.setText(Html.fromHtml(desc).toString());
        new Thread(new Runnable() {
            public void run() {
                final Bitmap bitmap = loadImageFromNetwork(image);
                ivProduct.post(new Runnable() {
                    public void run() {
                        ivProduct.setImageBitmap(bitmap);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(giveDP(250), giveDP(250));
                        layoutParams.gravity= Gravity.CENTER;
                        ivProduct.setLayoutParams(layoutParams);

                    }
                });
            }
        }).start();

        // TextView tv = (TextView) findViewById(R.id.tvpdtest);
       // tv.setText(id);
    }

    private int giveDP(float dp){
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        return pixels;
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

    public static Bitmap loadImageFromNetwork(String src) {
        try {
            //Log.e("src", src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            //Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception", e.getMessage());
            return null;
        }
    }
}
