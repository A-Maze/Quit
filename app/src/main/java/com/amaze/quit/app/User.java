package com.amaze.quit.app;

/**
 * Created by Rik on 20-5-2014.
 */
public class User {
// user table

    //private variables
    int uID;
    int sID;
    int perDag;
    // int productID
    int level;
    int quitDay;
    int quitMonth;
    int quitYear;

    // constructor
    public User(int uID, int sID, int perDag, int quitDay, int quitMonth , int quitYear){

        this.sID = sID;
        this.perDag = perDag;
        this.level = 1;
        this.quitDay = quitDay;
    }

    // Empty constructor
    public User(int uID, int sID, int perDag, int level){

   }


    // get userID
    public int getuID() {
        return uID;
    }
    //set userID
    public void setuID(int uID) {
        this.uID = uID;
    }
    //get sigaretteID
    public int getsID() {
        return sID;
    }
    //set sigaretteID
    public void setsID(int sID) {
        this.sID = sID;
    }

    //get sigarettes a Dag
    public int getPerDag() {
        return perDag;
    }
    //set sigarettes a Dag
    public void setPerDag(int perDag) {
        this.perDag = perDag;
    }
    // get users level
    public int getLevel() {
        return level;
    }
    // set users level
    public void setLevel(int level) {
        this.level = level;
    }



}
