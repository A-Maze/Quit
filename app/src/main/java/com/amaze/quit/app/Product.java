package com.amaze.quit.app;

import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;


public class Product extends Fragment {

    Artikel artikel;

    static int position;
    private UserVisibilityEvent uservisibilityevent;
    private UpdateStats updatestats = new UpdateStats(getActivity());
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
    public void onViewCreated(View view, Bundle savedInstanceState){
        updateSavingProgress();
        setListeners();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //implements the main method what every fragment should do when it's visible
            uservisibilityevent.viewIsVisible(getActivity(),position,"blue","title_activity_product");

        }

    }

    public void updateSavingProgress(){
        DatabaseHandler db = new DatabaseHandler(getActivity());
        //gets the total saved amount
        float totalSavedAmount = updatestats.getSavedMoney();
        // what is spent?
        int spentAmount = db.getUser(1).getSpentAmount();
        //what is left?
        float amountLeft = totalSavedAmount - spentAmount;
        // prijs of the product
        //float productPrice = 400f;
        float productPrice = db.getProduct(1).getPrijs();
        int current = (int) Math.round((amountLeft / productPrice) * 100);
        // Get the Drawable custom_progressbar and all the textviews
        Drawable customProgressBar = getResources().getDrawable(R.drawable.progressbar_blue);
        ProgressBar moneyBar = (ProgressBar) getActivity().findViewById(R.id.progressBarProduct);
        moneyBar.setProgressDrawable(customProgressBar);
        TextView tvSavedAmount = (TextView) getActivity().findViewById(R.id.tvProductSavedAmount);
        TextView tvSavedPercentage = (TextView) getActivity().findViewById(R.id.tvProductSavedPercentage);
        TextView tvProductAmount = (TextView) getActivity().findViewById(R.id.tvProductPriceAmount);
        if(amountLeft < productPrice) {
            //sets the percentage complete and the amount
            tvSavedAmount.setText("€" + amountLeft);
            tvSavedPercentage.setText("" + current + "%");
            moneyBar.setProgress(current);
        } else {
            //if complete turn the bar green and show a complete text
            tvSavedAmount.setText("€" + productPrice);
            tvSavedPercentage.setText("" + 100 + "%");
            moneyBar.setProgress(100);
            moneyBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_blue_light));
            LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.productLayout);
            TextView tvComplete = new TextView(getActivity());
            tvComplete.setText("Voltooid!");
            tvComplete.setTextColor(getResources().getColor(R.color.light_blue));
            LinearLayout.LayoutParams parameters= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            parameters.setMargins(0,giveDP(-30f),0,0);
            tvComplete.setGravity(Gravity.CENTER);
            tvComplete.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
            linearLayout.addView(tvComplete);
        }
        //always show the product prijs
        tvProductAmount.setText("€" + productPrice);
        //close the database
        db.close();

    }

    private int giveDP(float dp){
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        float fpixels = metrics.density * dp;
        int pixels = (int) (fpixels + 0.5f);
        return pixels;
    }

    public void setListeners(){
        Button bPay = (Button) getActivity().findViewById(R.id.bPay);
        bPay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHandler db = new DatabaseHandler(getActivity());
                String naam = db.getProduct(1).getTitel();
                naam = naam.replace(" ","-");
                naam = naam.replaceAll("[^a-zA-Z0-9]","");
                String id= db.getProduct(1).getId();
                String url = "http://www.bol.com/nl/p/"+naam+"/"+id+"/";
                Uri uri = Uri.parse(url);
                db.close();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);

            }
        });

    }


}