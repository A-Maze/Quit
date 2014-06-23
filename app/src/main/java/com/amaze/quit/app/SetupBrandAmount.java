package com.amaze.quit.app;

import android.content.Intent;
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


    private static EditText etDayAmount, etPerPak, etPrice;
    private static TextView tvPerPak;
    private static RadioButton rbSigaretten;
    private static RadioButton rbShag;
    private static Spinner sBrand;
    private static String[] sigarettenList;
    private static Sigaretten[] sigaretten;
    private static String[] shagList;
    private static Shag[] shagie;
    private static RadioGroup rg;
    private static int selectedSigaretPos;
    private static TextView addSmoke;
    private boolean updated = false;
    private static int selectedRadio;
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
        return v;


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        // etPerPak.setVisibility(View.GONE);
        etPerPak = (EditText) getActivity().findViewById(R.id.etPerPak);
        tvPerPak = (TextView) getActivity().findViewById(R.id.tvPerPak);
        etPrice = (EditText) getActivity().findViewById(R.id.etPrijs);
        etDayAmount = (EditText) getActivity().findViewById(R.id.etDayAmount);
        rbShag = (RadioButton) getActivity().findViewById(R.id.rbShag);
        rbSigaretten = (RadioButton) getActivity().findViewById(R.id.rbSigaretten);
        rg = (RadioGroup) getActivity().findViewById(R.id.radioGroup);
        sBrand = (Spinner) getActivity().findViewById(R.id.sBrand);
        addSmoke = (TextView) getActivity().findViewById(R.id.tvSetupAddSmoke);
        selectedSigaretPos = 0;
        rg.setOnCheckedChangeListener(new android.widget.RadioGroup.OnCheckedChangeListener() {



            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                selectedRadio = i;
                if(i == rbSigaretten.getId()){
                    shag = false;
                    sigaret = true;
                    fillSpinnerSigaret();
                    etPerPak.setText("");
                    etPrice.setText("");
                    configAddSmoke("sigaretten");
                }
                else if(i == rbShag.getId())
                {
                    shag = true;
                    sigaret = false;
                    fillSpinnerShag();
                    etPerPak.setText("");
                    etPrice.setText("");
                    configAddSmoke("shag");

                }
            }


        });
        rg.check(rbSigaretten.getId());
        shag = false;
        sigaret = true;
        fillSpinnerSigaret();
        etPerPak.setText("");
        etPrice.setText("");
        configAddSmoke("Sigaretten");




    }

    private void fillSpinnerSigaret() {

        getSigaretten();

        sBrand.setAdapter(null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sigarettenList);
        sBrand.setAdapter(adapter);
        sBrand.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                selectedSigaretPos = pos;
                DatabaseHandler db = new DatabaseHandler(getActivity());

                etPerPak.setText("" + db.getSigaret(pos+1).getAantal());
                etPrice.setText(String.format("%.2f",db.getSigaret(pos+1).getPrijs()));
                db.close();
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
                DatabaseHandler db = new DatabaseHandler(getActivity());
                etPrice.setText(String.format("%.2f",db.getShag(pos+1).getPrijs()));
                db.close();
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
            try {
                for (int i = 1; i <= db.getSigarettenAmount(); i++) {
                    sigarettenList[i - 1] = db.getSigaret(i).getMerk();
                    sigaretten[i - 1] = db.getSigaret(i);

                }
            }
            catch(Exception e){
                for (int i = 1; i <= db.getSigarettenAmount(); i++) {
                    sigarettenList[i - 1] = db.getSigaret(i).getMerk();
                    sigaretten[i - 1] = db.getSigaret(i);

                }
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

    public int getPerPak(){
        return Integer.parseInt(etPerPak.getText().toString());
    }

    public float getPrijs() { return Float.valueOf(etPrice.getText().toString());}

    private void configAddSmoke(String type){
        final String finalType = type;
        addSmoke.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(),AddSmoke.class);
                Bundle extras = new Bundle();
                extras.putString("type",finalType);
                intent.putExtras(extras);
                //makes sure that on return the added smoke will be selected
                updated = true;
                startActivity(intent);
            }
        });

    }



    @Override
    public void onResume(){
        super.onResume();
        if(updated){


            if(selectedRadio == rbSigaretten.getId()){
                shag = false;
                sigaret = true;
                fillSpinnerSigaret();
                etPerPak.setText("");
                etPrice.setText("");
                configAddSmoke("Sigaretten");
            }
            else if(selectedRadio == rbShag.getId())
            {
                shag = true;
                sigaret = false;
                fillSpinnerShag();
                etPerPak.setText("");
                etPrice.setText("");
                configAddSmoke("Shag");

            }

            sBrand.setSelection(sBrand.getCount()-1);
            updated = false;
        }
    }





}
