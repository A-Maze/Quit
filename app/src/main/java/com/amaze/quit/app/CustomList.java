package com.amaze.quit.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * Created by Sander on 3-6-2014.
 */
public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] title;
    private final Double[] price;
    private final String[] imageURL;
    private Bitmap productImage;

    public CustomList(Activity context,
                      String[] title, Double[] price, String[] imageURL) {
        super(context, R.layout.listview_bol, title);
        this.context = context;
        this.title = title;
        this.price = price;
        this.imageURL = imageURL;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listview_bol, null, true);
        TextView title = (TextView) rowView.findViewById(R.id.tvProductTitle);
        TextView price = (TextView) rowView.findViewById(R.id.tvProductPrice);
        ImageView productImage = (ImageView) rowView.findViewById(R.id.ivProductImages);
        title.setText(this.title[position]);

        DecimalFormat df = new DecimalFormat("#.00");
        price.setText(df.format(this.price[position]) + "");
        productImage.setImageBitmap(getBitmapFromURL(imageURL[position]));
        return rowView;
    }

    public static Bitmap getBitmapFromURL(String src) {
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
            Log.e("Exception",e.getMessage());
            return null;
        }
    }


}