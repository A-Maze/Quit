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
    /*het vullen van de spinner met sigaretten / shag */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_setup_brand_amount, container, false);
        etDayAmount = (EditText) v.findViewById(R.id.etDayAmount);
        rbShag = (RadioButton) v.findViewById(R.id.rbShag);
        rbSigaretten = (RadioButton) v.findViewById(R.id.rbSigaretten);
        RadioGroup rg = (RadioGroup) v.findViewById(R.id.radioGroup);
        rg.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {


            /* als de radiobutton van sigaretten naar shag veranderd of andersom de spinner opnieuw vullen*/
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(rbSigaretten.isChecked()){
                    shag = false;
                    sigaret = true;
                    fillSpinnerSigaret();
                }
                else if(rbShag.isChecked())
                {
                    shag = true;
                    sigaret = false;
                    fillSpinnerShag();
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

    /* het vullen van de spinner  met sigaretten uit database*/
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

    /* het vullen van de spinner met shag uit de database */
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
}
