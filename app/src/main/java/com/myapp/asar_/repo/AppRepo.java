package com.myapp.asar_.repo;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.myapp.asar_.database.AppDao;
import com.myapp.asar_.database.AppDatabase;
import com.myapp.asar_.database.entities.PlaceImages;
import com.myapp.asar_.database.entities.Places;
import com.myapp.asar_.database.entities.User;

import java.util.List;

public class AppRepo {
    Context context;
    AppDatabase appDatabase;
    AppDao dao;

    LiveData<List<Places>> allPlaces;

    public AppRepo(Context context) {
        this.context = context;
        appDatabase = AppDatabase.getInstance(context);
        dao = appDatabase.appDAO();
        allPlaces = dao.getAllPlaces();
    }

    public long createAccountForUser(User user) {
        return dao.createAccountForUser(user);
    }

    public User loginUser(String email, String password) {
        return dao.loginUser(email, password);
    }

    public Places searchPlaceUsingTitle(String title) {
        return dao.searchPlaceUsingTitle(title);
    }

    public long createPlace(Places place) {
        return dao.createPlace(place);
    }

    public long insertPlaceImages(PlaceImages images) {
        return dao.insertImages(images);
    }

    public int updateUserAccount(User user) {
        return dao.updateUserAccount(user);
    }

    public int updatePlaceImages(PlaceImages images) {
        return dao.updatePlaceImages(images);
    }

    public int deleteUserAccount(int userID) {
        return dao.deleteUserAccount(userID);
    }

    public User getUserUsingID(int userID) {
        return dao.getUserUsingID(userID);
    }

    public User getUserUsingEmail(String email) {
        return dao.getUserUsingEmail(email);
    }

    public int updatePlace(Places place){
        return dao.updatePlace(place);
    }
    public int updateUserPassword(String password, int userID){
        return dao.updateUserPassword(password, userID);
    }
    public LiveData<List<User>> getAllUsers() {
        return dao.getAllUsers();
    }

    public LiveData<List<Places>> getAllPlaces() {
        return allPlaces;
    }

    public LiveData<List<PlaceImages>> getAllImagesOfPlace(String title) {
        return dao.getImagesUsingTitle(title);
    }

    public LiveData<List<PlaceImages>> getAllImages() {
        return dao.getAllImages();
    }

}
