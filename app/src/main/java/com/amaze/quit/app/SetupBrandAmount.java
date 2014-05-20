package com.amaze.quit.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;


public class SetupBrandAmount extends Fragment {

    private RadioButton rbSigaretten;
    private RadioButton rbShag;
    private EditText etDayAmount;
    private EditText etPackAmount;

    private Integer dayAmount;
    private Integer packAmount;

    public static final SetupBrandAmount newInstance()
    {
        SetupBrandAmount f = new SetupBrandAmount();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_setup_brand_amount, container, false);

        rbSigaretten = (RadioButton) v.findViewById(R.id.rbSigaretten);
        rbShag = (RadioButton) v.findViewById(R.id.rbShag);
        etDayAmount = (EditText) v.findViewById(R.id.etDayAmount);
        etPackAmount = (EditText) v.findViewById(R.id.etPackAmount);

        return v;


    }

    public void checkForInput(View view) {
        // wat te doen als de next button is geklikt.

        // variabelen vastzetten
        try {
            dayAmount = Integer.parseInt(etDayAmount.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            dayAmount = null;
        }
        try {
            packAmount = Integer.parseInt(etPackAmount.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            packAmount = null;
        }


        if (dayAmount == null || packAmount == null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
            alertDialogBuilder.setTitle("Hoer");
            alertDialogBuilder
                    .setMessage("Vul een waarde in Bitch!")
                    .setCancelable(false)
                    .setPositiveButton("Okee slet", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            //runChooseProduct();
        }

    }

    // naar choose product. de activity dus nog even veranderen als die bestaat.
    private void runChooseProduct() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }



}
