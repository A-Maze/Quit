package com.amaze.quit.app;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ProductDetail extends ActionBarActivity {

    public final static String TAG = "ProductDetail";

    Bitmap bitmap;

    String id;
    String titel;
    Double prijs;
    String image;
    String desc;

    String priceS;
    String priceSd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        TextView tvTitle = (TextView) findViewById(R.id.tvProductDetailTitle);
        TextView tvPrice = (TextView) findViewById(R.id.tvProductDetailPrice);
        TextView tvDesc = (TextView) findViewById(R.id.tvProductDetailDescription);
        final ImageView ivProduct = (ImageView) findViewById(R.id.ivProductDetailImage);
        Button bSpaar = (Button) findViewById(R.id.bProductDetailSpaar);

        bSpaar.setOnClickListener(spaarProduct);

        Intent fromListView = getIntent();
        id = fromListView.getStringExtra("id");
        Bundle extras = getIntent().getExtras();
        final String image = extras.getString("image");
        titel = extras.getString("titel");
        prijs = extras.getDouble("prijs");
        priceS = Double.toString(prijs);
        desc = extras.getString("description");

        tvTitle.setText(titel);
        priceSd = Double.toString(prijs);
        priceSd = priceSd.replace(".", ",");
        Log.d(TAG, priceSd);
        tvPrice.setText(priceSd + "");
        tvDesc.setText(Html.fromHtml(desc).toString() + "");

        //new Thread(new Runnable() {
        // public void run() {
        bitmap = loadImageFromNetwork(image);
        //ivProduct.post(new Runnable() {
        //public void run() {
        ivProduct.setImageBitmap(bitmap);
        //LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(giveDP(250), giveDP(250));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(giveDP(100), giveDP(100));
        layoutParams.gravity = Gravity.CENTER;
        ivProduct.setLayoutParams(layoutParams);
        //}
        // });
        //}
        //}).start();

        // TextView tv = (TextView) findViewById(R.id.tvpdtest);
        // tv.setText(id);
    }

    private View.OnClickListener spaarProduct = new View.OnClickListener() {
        public void onClick(View v) {
            DatabaseHandler db = new DatabaseHandler(getApplicationContext());

            byte[] image = getBitmapAsByteArray(bitmap);

            try {
                int nrows = db.updateProduct(new Artikel(1, id, titel, Float.parseFloat(priceS), image));


                if (nrows > 0) {
                    db.updateProduct(new Artikel(1, id, titel, Float.parseFloat(priceS), image));
                    SharedPreferences settings = getSharedPreferences("QuitPrefs", 0);
                    settings.edit().putBoolean("newProduct",false).commit();
                } else {


                    db.addProduct(new Artikel(1, id, titel, Float.parseFloat(priceS), image));
                }


            } catch (Exception e) {
                db.updateProduct(new Artikel(1, id, titel, Float.parseFloat(priceS), image));
                e.printStackTrace();
            }
            finish();
        }
    };

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    /*public void saveImage() throws IOException {
        DefaultHttpClient mHttpClient = new DefaultHttpClient();
        HttpGet mHttpGet = new HttpGet(image);
        HttpResponse mHttpResponse = mHttpClient.execute(mHttpGet);
        if (mHttpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = mHttpResponse.getEntity();
            if ( entity != null) {
                // insert to database
                ContentValues values = new ContentValues();
                values.put(MyBaseColumn.MyTable.ImageField, EntityUtils.toByteArray(entity));
                getContentResolver().insert(MyBaseColumn.MyTable.CONTENT_URI, values);
            }
        }
    }*/

    private int giveDP(float dp) {
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
