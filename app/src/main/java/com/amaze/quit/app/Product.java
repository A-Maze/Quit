package com.amaze.quit.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

import org.w3c.dom.Text;


public class Product extends Fragment {

    Artikel artikel;

    static int position;
    private UserVisibilityEvent uservisibilityevent;
    private UpdateStats updatestats = new UpdateStats(getActivity());
    private static float amountLeft;
    public static final Product newInstance(int i) {
        Product f = new Product();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        position = i;
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product, container, false);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSavingProgress();
        setListeners();

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        updateSavingProgress();
        setListeners();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //implements the main method what every fragment should do when it's visible
            uservisibilityevent.viewIsVisible(getActivity(), position, "blue", "title_activity_product");

        }

    }

    public void updateSavingProgress() {
        DatabaseHandler db = new DatabaseHandler(getActivity());
        //gets the total saved amount
        float totalSavedAmount = updatestats.getSavedMoney();
        // what is spent?
        int spentAmount = updatestats.getSpentAmount();
        //what is left?
        amountLeft = totalSavedAmount - spentAmount;
        // prijs of the product
        //float productPrice = 400f;
        if(amountLeft < 0){
            amountLeft = 0f;
        }
        float productPrice = db.getProduct(1).getPrijs();
        //what is left of the price?
        float priceLeft = productPrice - amountLeft;
        String amountLeftString = String.format("%.2f",amountLeft);
        int daysLeft = (int) Math.round((priceLeft/updatestats.getPrice()) *updatestats.getRefreshStockRate());

        // Set titel
        TextView tvTitel = (TextView) getActivity().findViewById(R.id.tvProgressProductTitle);
        String titel = db.getProduct(1).getTitel();
        tvTitel.setText(titel);

        // Set image
        ImageView ivProduct = (ImageView) getActivity().findViewById(R.id.ivProductImage);
        Bitmap product = BitmapFactory.decodeByteArray(db.getProduct(1).getImage(), 0, db.getProduct(1).getImage().length);
        ivProduct.setImageBitmap(product);

        int current = (int) Math.round((amountLeft / productPrice) * 100);
        // Get the Drawable custom_progressbar and all the textviews
        Drawable customProgressBar = getResources().getDrawable(R.drawable.progressbar_blue);
        ProgressBar moneyBar = (ProgressBar) getActivity().findViewById(R.id.progressBarProduct);
        moneyBar.setProgressDrawable(customProgressBar);
        TextView tvSavedAmount = (TextView) getActivity().findViewById(R.id.tvProductSavedAmount);
        TextView tvSavedPercentage = (TextView) getActivity().findViewById(R.id.tvProductSavedPercentage);
        TextView tvProductAmount = (TextView) getActivity().findViewById(R.id.tvProductPriceAmount);
        if (amountLeft < productPrice) {
            //sets the percentage complete and the amount
            tvSavedAmount.setText("€" + amountLeftString);
            tvSavedPercentage.setText("" + current + "%");
            moneyBar.setProgress(current);
            TextView tvComplete = (TextView) getActivity().findViewById(R.id.tvProductComplete);
            TextView tvDaysLeft = (TextView) getActivity().findViewById(R.id.tvProductDaysLeft);
            tvComplete.setVisibility(View.GONE);
            tvDaysLeft.setText("Nog maar " + daysLeft + " dagen sparen!");
            tvDaysLeft.setVisibility(View.VISIBLE);


        } else {
            //if complete turn the bar green and show a complete text
            amountLeft = productPrice;
            tvSavedAmount.setText("€" + amountLeftString);
            tvSavedPercentage.setText("" + 100 + "%");
            moneyBar.setProgress(100);
            moneyBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_blue_light));
            TextView tvComplete = (TextView) getActivity().findViewById(R.id.tvProductComplete);
            TextView tvDaysLeft = (TextView) getActivity().findViewById(R.id.tvProductDaysLeft);
            tvComplete.setVisibility(View.VISIBLE);
            tvDaysLeft.setVisibility(View.GONE);
        }
        //always show the product prijs
        tvProductAmount.setText("€" + productPrice);
        //close the database
        db.close();

    }

    private int giveDP(float dp) {
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        return pixels;
    }

    public float getAmountLeft(){
        return amountLeft;
    }

    public void setListeners() {
        ImageButton bPay = (ImageButton) getActivity().findViewById(R.id.bPay);
        bPay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(getActivity());
                String naam = db.getProduct(1).getTitel();
                naam = naam.replace(" ", "-");
                naam = naam.replaceAll("[^a-zA-Z0-9]", "");
                String id = db.getProduct(1).getId();
                String url = "http://www.bol.com/nl/p/" + naam + "/" + id + "/";
                Uri uri = Uri.parse(url);

                db.close();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                SharedPreferences settings = getActivity().getSharedPreferences("QuitPrefs", 0);
                settings.edit().putBoolean("boughtProduct",true).commit();
                startActivity(intent);

            }
        });

    }


}