package com.amaze.quit.app;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.viewpagerindicator.LinePageIndicator;


public class Product extends Fragment {
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
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //implements the main method what every fragment should do when it's visible
            uservisibilityevent.viewIsVisible(getActivity(),position,"blue","title_activity_product");


        }

    }

    private void updateSavingProgress(){
        DatabaseHandler db = new DatabaseHandler(getActivity());
        float totalSavedAmount = updatestats.getSavedMoney();
        int spentAmount = db.getUser(1).getSpentAmount();
        float amountLeft = totalSavedAmount - spentAmount;
        float productPrice = 400f;
        int current = (int) Math.round(amountLeft / productPrice);
        ProgressBar moneyBar = (ProgressBar) getActivity().findViewById(R.id.progressBarProduct);
        TextView tvSavedAmount = (TextView) getActivity().findViewById(R.id.tvProductSavedAmount);
        TextView tvSavedPercentage = (TextView) getActivity().findViewById(R.id.tvProductSavedPercentage);
        TextView tvProductAmount = (TextView) getActivity().findViewById(R.id.tvProductPriceAmount);
        if(amountLeft < productPrice) {
            tvSavedAmount.setText("€" + amountLeft);
            tvSavedPercentage.setText("" + current + "%");
            moneyBar.setProgress(current);
        }
        else{
            tvSavedAmount.setText("€" + productPrice);
            tvSavedPercentage.setText("" + 100 + "%");
            moneyBar.setProgress(100);
        }
        tvProductAmount.setText("€" + productPrice);


    }


}