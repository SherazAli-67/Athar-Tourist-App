package com.myapp.asar_.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myapp.asar_.database.entities.PlaceImages;
import com.myapp.asar_.database.entities.Places;
import com.myapp.asar_.database.entities.User;

import java.util.List;

@Dao
public interface AppDao {

    @Insert
    void loadAllPlaces(Places[] places);

    @Insert
    void loadAllBoulevardImgs(PlaceImages[] riyadhImgs);

    @Insert
    void loadAllMasmakImgs(PlaceImages[] alMasmakImgs);

    @Insert
    void loadAllMurrabaImgs(PlaceImages[] murabbaImages);

    @Insert
    void loadAllLibImgs(PlaceImages[] nationalLibImages);

    @Insert
    void loadAllNationalMuseumImgs(PlaceImages[] nationalMuseums);

    @Insert
    void loadAllRiyadhZooImgs(PlaceImages[] riyadhZooImgs);


    @Insert
    void insertboulevardRiyadh(Places[] places);

    @Insert
    long createAccountForUser(User user);

    @Update
    int updateUserAccount(User user);

    @Query("SELECT * FROM USER WHERE Email= :email AND Password= :password")
    User loginUser(String email, String password);

    @Query("Delete FROM user WHERE user_id= :userID")
    int deleteUserAccount(int userID);

    @Insert
    long createPlace(Places place);

    @Query("SELECT * FROM Places WHERE title= :title")
    Places searchPlaceUsingTitle(String title);

    @Insert
    long insertImages(PlaceImages images);

    @Update
    int updatePlaceImages(PlaceImages images);

    @Query("SELECT * FROM USER WHERE user_id= :userID")
    User getUserUsingID(int userID);

    @Query("SELECT * FROM USER WHERE Email= :email")
    User getUserUsingEmail(String email);

    @Update
    int updatePlace(Places place);

    @Query("UPDATE USER SET Password= :password WHERE user_id= :userID")
    int updateUserPassword(String password, int userID);

    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM Places")
    LiveData<List<Places>> getAllPlaces();

    @Query("SELECT * FROM PlaceImages where placeID= :title")
    LiveData<List<PlaceImages>> getImagesUsingTitle(String title);

    @Query("SELECT * FROM PlaceImages")
    LiveData<List<PlaceImages>> getAllImages();

}
