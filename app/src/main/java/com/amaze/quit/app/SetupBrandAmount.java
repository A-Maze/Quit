package com.amaze.quit.app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


public class SetupBrandAmount extends Fragment {

    private RadioButton rbSigaretten;
    private RadioButton rbShag;
    private static EditText etDayAmount;

    private static String[] sigarettenList;
    private static Sigaretten[] sigaretten;
    private static int selectedSigaretPos;

    public static final SetupBrandAmount newInstance() {
        SetupBrandAmount f = new SetupBrandAmount();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_setup_brand_amount, container, false);


        getSigaretten();
        etDayAmount = (EditText) v.findViewById(R.id.etDayAmount);

        Spinner sBrand = (Spinner) v.findViewById(R.id.sBrand);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sigarettenList);
        sBrand.setAdapter(adapter);
        sBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedSigaretPos = pos;

            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return v;


    }

    public static Sigaretten getSigarettenPosition() {
        return sigaretten[selectedSigaretPos];
    }

    public static EditText getEtDayAmount() {
        return etDayAmount;
    }

    private void getSigaretten() {
        DatabaseHandler db = new DatabaseHandler(getActivity());


        sigarettenList = new String[]{
                db.getSigaret(1).getMerk(),
                db.getSigaret(2).getMerk(),
                db.getSigaret(3).getMerk(),
                db.getSigaret(4).getMerk()
        };

        sigaretten = new Sigaretten[]{
                db.getSigaret(1),
                db.getSigaret(2),
                db.getSigaret(3),
                db.getSigaret(4)
        };
        db.close();
    }


    /*
    OUDE CODE MAAR MISCHIEN NOG NODIG
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
            alertDialogBuilder.setTitle("foutje");
            alertDialogBuilder
                    .setMessage("Vul een waarde in!")
                    .setCancelable(false)
                    .setPositiveButton("Okee", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }

    }*/


}
