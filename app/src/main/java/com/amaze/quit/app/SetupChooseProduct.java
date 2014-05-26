package com.amaze.quit.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SetupChooseProduct extends Fragment {



    public static final SetupChooseProduct newInstance()
    {
        SetupChooseProduct f = new SetupChooseProduct();
        Bundle bdl = new Bundle(1);
        f.setArguments(bdl);
        return f;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_choose_product, container, false);



        return v;


    }






}

