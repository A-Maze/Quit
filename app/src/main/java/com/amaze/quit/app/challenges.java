package com.amaze.quit.app;

/**
 * Created by Rik on 21-5-2014.
 */
public class challenges {

    //private variables
    int cID;
    String titel;
    String beschrijving;

    public challenges(int cID, String titel, String beschrijving) {
        this.cID = cID;
        this.titel = titel;
        this.beschrijving = beschrijving;
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
}
