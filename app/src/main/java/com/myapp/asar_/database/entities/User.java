package com.myapp.asar_.database.entities;

import static com.myapp.asar_.constants.Constants.EMAIL;
import static com.myapp.asar_.constants.Constants.FNAME;
import static com.myapp.asar_.constants.Constants.LNAME;
import static com.myapp.asar_.constants.Constants.NAME;
import static com.myapp.asar_.constants.Constants.PASSWORD;
import static com.myapp.asar_.constants.Constants.USER;
import static com.myapp.asar_.constants.Constants.USER_ID;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = USER)
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = USER_ID)
    private int userID;

    @ColumnInfo(name = FNAME)
    private String fname;

    @ColumnInfo(name = LNAME)
    private String lname;


    @ColumnInfo(name = EMAIL)
    private String email;

    @ColumnInfo(name = PASSWORD)
    private String password;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] profileImage;

    private String contactNum;

    public User() {
    }

    public User(String fname, String lname, String email, String password, String contactNum) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.contactNum = contactNum;
    }

    public int getUserID() {
        return userID;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public String getContactNum() {
        return contactNum;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public void setContactNum(String contactNum) {
        this.contactNum = contactNum;
    }
}
