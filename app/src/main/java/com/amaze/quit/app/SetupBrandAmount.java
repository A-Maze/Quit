package com.amaze.quit.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


public class SetupBrandAmount extends Fragment  {


    public static EditText etDayAmount, etPerPak;
    public static TextView tvPerPak;
    private static RadioButton rbSigaretten;
    private static RadioButton rbShag;
    private static Spinner sBrand;
    private static String[] sigarettenList;
    private static Sigaretten[] sigaretten;
    private static String[] shagList;
    private static Shag[] shagie;
    private static int selectedSigaretPos;

    public static EditText getEtPerPak() {
        return etPerPak;
    }

    public static boolean sigaret = true;
    public static boolean shag = false;

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


       // etPerPak.setVisibility(View.GONE);

        etDayAmount = (EditText) v.findViewById(R.id.etDayAmount);
        rbShag = (RadioButton) v.findViewById(R.id.rbShag);
        rbSigaretten = (RadioButton) v.findViewById(R.id.rbSigaretten);
        RadioGroup rg = (RadioGroup) v.findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {



            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rbSigaretten.isChecked()==true){
                    shag = false;
                    sigaret = true;
                    fillSpinnerSigaret();
                //    etPerPak.setVisibility(View.GONE);
               //     tvPerPak.setVisibility(View.GONE);
                }
                else if(rbShag.isChecked()==true)
                {
                   shag = true;
                   sigaret = false;
                   fillSpinnerShag();
                //    etPerPak.setVisibility(View.VISIBLE);
                //    tvPerPak.setVisibility(View.VISIBLE);
                }
            }
        });


        sBrand = (Spinner) v.findViewById(R.id.sBrand);
        getSigaretten();

        sBrand.setAdapter(null);
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

    private void fillSpinnerSigaret() {

        getSigaretten();

        sBrand.setAdapter(null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sigarettenList);
        sBrand.setAdapter(adapter);
        sBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedSigaretPos = pos;

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void fillSpinnerShag() {
        getSigaretten();
        sBrand.setAdapter(null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, shagList);
        sBrand.setAdapter(adapter);
        sBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedSigaretPos = pos;

            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public static  Sigaretten getSigarettenPosition(){
        return sigaretten[selectedSigaretPos];
    }

    public static  Shag getShagPos(){
        return shagie[selectedSigaretPos];
    }

    public static EditText getEtDayAmount(){
        return etDayAmount;
    }

    private void getSigaretten() {
        DatabaseHandler db = new DatabaseHandler(getActivity());


        if (sigaret) {

            sigarettenList = new String[db.getSigarettenAmount()];
            sigaretten = new Sigaretten[db.getSigarettenAmount()];
            for (int i = 1; i <= db.getSigarettenAmount(); i++) {
                sigarettenList[i - 1] = db.getSigaret(i).getMerk();
                sigaretten[i - 1] = db.getSigaret(i);
            }
        }
        else if (shag){
            shagList = new String[db.getShagAmount()];
            shagie = new Shag[db.getShagAmount()];
            for (int i = 1; i <= db.getShagAmount(); i++) {
                shagList[i - 1] = db.getShag(i).getMerk();
                shagie[i - 1] = db.getShag(i);
            }

        }


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
