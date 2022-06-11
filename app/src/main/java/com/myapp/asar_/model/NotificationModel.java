package com.myapp.asar_.model;

import java.io.Serializable;

public class NotificationModel implements Serializable {

    double placeLat;
    double placeLng;
    double userLat;
    double userLng;
    String title;


    public NotificationModel(double placeLat, double placeLng, double userLat, double userLng, String title) {
        this.placeLat = placeLat;
        this.placeLng = placeLng;
        this.userLat = userLat;
        this.userLng = userLng;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public double getPlaceLat() {
        return placeLat;
    }

    public double getPlaceLng() {
        return placeLng;
    }

    public double getUserLat() {
        return userLat;
    }

    public double getUserLng() {
        return userLng;
    }
}
