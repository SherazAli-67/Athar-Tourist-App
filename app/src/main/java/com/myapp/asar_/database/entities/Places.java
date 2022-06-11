package com.myapp.asar_.database.entities;

import static com.myapp.asar_.constants.Constants.PLACES;
import static com.myapp.asar_.constants.Constants.PLACE_DESCRIPTION;
import static com.myapp.asar_.constants.Constants.PLACE_ID;
import static com.myapp.asar_.constants.Constants.PLACE_IMG;
import static com.myapp.asar_.constants.Constants.PLACE_LAT;
import static com.myapp.asar_.constants.Constants.PLACE_LNG;
import static com.myapp.asar_.constants.Constants.PLACE_NUMBER;
import static com.myapp.asar_.constants.Constants.PLACE_TITLE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = PLACES)
public class Places {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = PLACE_ID)
    private int placeID;

    @ColumnInfo(name = PLACE_TITLE)
    private String title;

    @ColumnInfo(name = PLACE_DESCRIPTION)
    private String description;

    @ColumnInfo(name = PLACE_LAT)
    private double latitude;

    @ColumnInfo(name = PLACE_LNG)
    private double longitude;

    @ColumnInfo(name = PLACE_IMG)
    private String imgURL;

    @ColumnInfo(name = PLACE_NUMBER)
    String contactNum;

    boolean addedToGoList;
    int userID;

    public Places() {
    }

    public Places(String title, String description, double latitude, double longitude, String imgURL, String contactNum) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imgURL = imgURL;
        this.contactNum = contactNum;
    }


    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getUserID() {
        return userID;
    }

    public void setAddedToGoList(boolean addedToGoList) {
        this.addedToGoList = addedToGoList;
    }

    public boolean isAddedToGoList() {
        return addedToGoList;
    }

    public void setPlaceID(int placeID) {
        this.placeID = placeID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }

    public int getPlaceID() {
        return placeID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getContactNum() {
        return contactNum;
    }
}
