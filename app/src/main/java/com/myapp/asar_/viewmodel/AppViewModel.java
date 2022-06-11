package com.myapp.asar_.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.myapp.asar_.database.entities.PlaceImages;
import com.myapp.asar_.database.entities.Places;
import com.myapp.asar_.database.entities.User;
import com.myapp.asar_.repo.AppRepo;

import java.util.List;

public class AppViewModel extends AndroidViewModel {
    AppRepo repo;
    LiveData<List<Places>> allPlaces;

    public AppViewModel(@NonNull Application application) {
        super(application);
        repo = new AppRepo(application);
        allPlaces = repo.getAllPlaces();
    }

    public long createAccountForUser(User user) {
        return repo.createAccountForUser(user);
    }

    public User loginUser(String email, String password) {
        return repo.loginUser(email, password);
    }

    public Places searchPlaceUsingTitle(String title) {
        return repo.searchPlaceUsingTitle(title);
    }

    public long createPlace(Places place) {
        return repo.createPlace(place);
    }

    public long insertPlaceImages(PlaceImages images) {
        return repo.insertPlaceImages(images);
    }

    public int updateUserAccount(User user) {
        return repo.updateUserAccount(user);
    }

    public int updatePlaceImages(PlaceImages images) {
        return repo.updatePlaceImages(images);
    }

    public int deleteUserAccount(int userID) {
        return repo.deleteUserAccount(userID);
    }

    public User getUserUsingID(int userID) {
        return repo.getUserUsingID(userID);
    }

    public User getUserUsingEmail(String email) {
        return repo.getUserUsingEmail(email);
    }

    public int updatePlace(Places place){
        return repo.updatePlace(place);
    }

    public int updateUserPassword(String password, int userID){
        return repo.updateUserPassword(password, userID);
    }

    public LiveData<List<User>> getAllUsers() {
        return repo.getAllUsers();
    }

    public LiveData<List<Places>> getAllPlaces() {
        return allPlaces;
    }

    public LiveData<List<PlaceImages>> getAllImagesForPlaces(String title) {
        return repo.getAllImagesOfPlace(title);
    }

    public LiveData<List<PlaceImages>> getAllImages() {
        return repo.getAllImages();
    }

}
