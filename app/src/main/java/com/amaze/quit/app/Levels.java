package com.amaze.quit.app;

/**
 * Created by Rik on 21-5-2014.
 */
public class Levels {

    //variables
    int lID;
    String titel;
    String beschrijving;

    public int getMinDays() {
        return minDays;
    }

    public void setMinDays(int minDays) {
        this.minDays = minDays;
    }

    int minDays;


    public Levels(int lID, String titel, String beschrijving, int minDays) {
        this.lID = lID;
        this.titel = titel;
        this.beschrijving = beschrijving;
        this.minDays = minDays;
    }

    public int getlID() {
        return lID;
    }

    public void setlID(int lID) {
        this.lID = lID;
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
