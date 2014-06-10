package com.amaze.quit.app;

/**
 * Created by Rik on 21-5-2014.
 */
public class Sigaretten {
    //private variables
    int sID;
    String merk;
    int aantal;
    float teer;
    float nicotine;
    float prijs;





    public Sigaretten( int sID, float nicotine, String merk, int aantal, float teer, float prijs) {
        this.nicotine = nicotine;
        this.sID = sID;
        this.merk = merk;
        this.aantal = aantal;
        this.teer = teer;
        this.prijs = prijs;
    }

    public float getPrijs() {
        return prijs;
    }

    public void setPrijs(float prijs) {
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

    public float getTeer() {
        return teer;
    }

    public void setTeer(float teer) {
        this.teer = teer;
    }

    public float getNicotine() {
        return nicotine;
    }

    public void setNicotine(float nicotine) {
        this.nicotine = nicotine;
    }
}
