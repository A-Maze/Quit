package com.amaze.quit.app;

/**
 * Created by Rik on 11-6-2014.
 */
public class Shag {
    //private variables
    int sID;
    String merk;
    int aantal;
    float prijs;

    public Shag(int sID, String merk, int aantal, float prijs) {
        this.sID = sID;
        this.merk = merk;
        this.aantal = aantal;
        this.prijs = prijs;
    }

    public int getsID() {
        return sID;
    }

    public void setsID(int sID) {
        this.sID = sID;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public int getAantal() {
        return aantal;
    }

    public void setAantal(int aantal) {
        this.aantal = aantal;
    }

    public float getPrijs() {
        return prijs;
    }

    public void setPrijs(float prijs) {
        this.prijs = prijs;
    }
}
