package com.amaze.quit.app;

/**
 * Created by Rik on 21-5-2014.
 */
public class Gezondheid {

    int uID;
    float teer;
    float nicotine;
    float co2;
    float langerTeLeven;


    public Gezondheid(int uID, float teer, float nicotine, float co2, float langerTeLeven) {
        this.uID = uID;
        this.teer = teer;
        this.nicotine = nicotine;
        this.co2 = co2;
        this.langerTeLeven = langerTeLeven;
    }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
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

    public float getCo2() {
        return co2;
    }

    public void setCo2(float co2) {
        this.co2 = co2;
    }

    public float getLangerTeLeven() {
        return langerTeLeven;
    }

    public void setLangerTeLeven(float langerTeLeven) {
        this.langerTeLeven = langerTeLeven;
    }
}
