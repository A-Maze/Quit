package com.amaze.quit.app;

import android.util.Log;

/**
 * Created by Rik on 21-5-2014.
 */
public class Challenges {

    //private variables11
    int cID;
    String titel;
    String beschrijving;
    Integer behaald;

    public Challenges( int cID, String titel, String beschrijving, Integer behaald) {
        this.cID = cID;
        this.titel = titel;
        this.beschrijving = beschrijving;
        this.behaald = behaald;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public Integer getBehaald(){ return behaald;}

    public void setBehaald(Integer behaald){this.behaald = behaald;}
}
