package com.amaze.quit.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;


public class SetupBrandAmount extends Fragment  {

    private RadioButton rbSigaretten;
    private RadioButton rbShag;
    private EditText etDayAmount;
    private EditText etPackAmount;
    Spinner sBrand;
    String[] sigaretten;
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

        //The complete button. This button should be moved to the final setup fragment which is this one at the moment.
        Button complete = (Button) v.findViewById(R.id.bSetupComplete);
        //sets the onclicklistener for the complete button
        complete.setOnClickListener(attachButton);


        rbSigaretten = (RadioButton) v.findViewById(R.id.rbSigaretten);
        rbShag = (RadioButton) v.findViewById(R.id.rbShag);
        etDayAmount = (EditText) v.findViewById(R.id.etDayAmount);
        etPackAmount = (EditText) v.findViewById(R.id.etPackAmount);
        sBrand = (Spinner) v.findViewById(R.id.sBrand);



        sigaretten = new String[] {
             "test 1"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, sigaretten);
        sBrand.setAdapter(adapter);



        return v;


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

    //The onClickListener for the complete button
    private OnClickListener attachButton = new OnClickListener(){
        public void onClick(View v){
            Intent myIntent = new Intent(getActivity(), Home.class);
            DatabaseHandler db = new DatabaseHandler(getActivity());
            //this makes sure the activity resumes rather than creating a new one.
            myIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            try {
                db.addSigarette(new Sigaretten(1, 1, "test", 1, 1, 1f));
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(myIntent);
            getActivity().finish();
        }
    };




}
