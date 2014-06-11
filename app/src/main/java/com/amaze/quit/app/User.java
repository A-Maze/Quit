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
    int quitMinute;
    int quitHour;
    int quitDay;
    int quitMonth;
    int quitYear;
    int spentAmount;

    public int getQuitMinute() {
        return quitMinute;
    }

    public void setQuitMinute(int quitMinute) {
        this.quitMinute = quitMinute;
    }

    public int getQuitHour() {
        return quitHour;
    }

    public void setQuitHour(int quitHour) {
        this.quitHour = quitHour;
    }

    public int getQuitDay() {
        return quitDay;
    }

    public void setQuitDay(int quitDay) {
        this.quitDay = quitDay;
    }

    public int getQuitMonth() {
        return quitMonth;
    }

    public void setQuitMonth(int quitMonth) {
        this.quitMonth = quitMonth;
    }

    public int getQuitYear() {
        return quitYear;
    }

    public void setQuitYear(int quitYear) {
        this.quitYear = quitYear;
    }

    public int getSpentAmount() {
        return spentAmount;
    }

    public void setSpentAmount(int spentAmount) {
        this.spentAmount = spentAmount;
    }


    // constructor
    public User(int uID, int sID, int perDag, int level, int quitYear, int quitMonth, int quitDay, int quitHour, int quitMinute, int spentAmount) {
        this.uID = uID;
        this.sID = sID;
        this.perDag = perDag;
        this.level = level;
        this.quitMinute = quitMinute;
        this.quitHour = quitHour;
        this.quitDay = quitDay;
        this.quitMonth = quitMonth;
        this.quitYear = quitYear;
        this.spentAmount = spentAmount;
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
