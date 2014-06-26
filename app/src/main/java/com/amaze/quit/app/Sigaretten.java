package com.amaze.quit.app;

/**
 * Created by Rik on 21-5-2014.
 */
public class Sigaretten extends Rookwaar {
    //private variables
    float teer;
    float nicotine;



    public Sigaretten(int sID, float nicotine, String merk, int aantal, float teer, float prijs) {
        super(sID,merk,aantal,prijs);
        this.nicotine = nicotine;
        this.teer = teer;
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
