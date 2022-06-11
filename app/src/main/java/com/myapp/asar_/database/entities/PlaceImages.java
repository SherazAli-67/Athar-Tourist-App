package com.myapp.asar_.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PlaceImages {

    @PrimaryKey(autoGenerate = true)
    private int id;

    String url;

    String placeID;

    public PlaceImages(String url, String placeID) {
        this.url = url;
        this.placeID = placeID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getPlaceID() {
        return placeID;
    }
}
