package com.amaze.quit.app;

/**
 * Created by Sander on 9-6-2014.
 */
public class Artikel {

    int uId;
    String id;
    String titel;
    float prijs;
    String image;

    public Artikel (int uId, String id, String titel, float prijs, String image) {
        this.uId = uId;
        this.id = id;
        this.titel = titel;
        this.prijs = prijs;
        this.image = image;
    }

    public int getuId() {
        return uId;
    }

    public String getId() {
        return id;
    }

    public String getTitel() {
        return titel;
    }

    public float getPrijs() {
        return prijs;
    }

    public String getImage() {
        return image;
    }

}
