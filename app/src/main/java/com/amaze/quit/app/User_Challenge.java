package com.amaze.quit.app;

/**
 * Created by Rik on 21-5-2014.
 */
public class User_Challenge {

    //variables
    int ucID;
    int uID;
    int cID;

    public User_Challenge(int ucID, int uID, int cID) {
        this.ucID = ucID;
        this.uID = uID;
        this.cID = cID;
    }

    public int getUcID() {
        return ucID;
    }

    public void setUcID(int ucID) {
        this.ucID = ucID;
    }

    public int getuID() {
        return uID;
    }

    public void setuID(int uID) {
        this.uID = uID;
    }

    public int getcID() {
        return cID;
    }

    public void setcID(int cID) {
        this.cID = cID;
    }
}