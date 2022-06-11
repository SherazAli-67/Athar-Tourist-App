package com.myapp.asar_.database;

import static com.myapp.asar_.constants.Constants.DATABASE_NAME;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.myapp.asar_.database.entities.AlMasmakData;
import com.myapp.asar_.database.entities.BoulevardRiyadData;
import com.myapp.asar_.database.entities.LibraryData;
import com.myapp.asar_.database.entities.MurabbaData;
import com.myapp.asar_.database.entities.NationalMuseumData;
import com.myapp.asar_.database.entities.PlaceImages;
import com.myapp.asar_.database.entities.Places;
import com.myapp.asar_.database.entities.PlacesData;
import com.myapp.asar_.database.entities.RiyadhZooData;
import com.myapp.asar_.database.entities.User;

import java.util.concurrent.Executors;

@Database(entities = {User.class, Places.class, PlaceImages.class,}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    getInstance(context).appDAO().loadAllPlaces(PlacesData.populatePlacesData());
                                    getInstance(context).
                                            appDAO().
                                            loadAllBoulevardImgs(BoulevardRiyadData.populateBoulevardRiyadh());
                                    getInstance(context).appDAO().loadAllMurrabaImgs(MurabbaData.populateMurabbaImages());
                                    getInstance(context).appDAO().loadAllNationalMuseumImgs(NationalMuseumData.populateNationalMuseum());
                                    getInstance(context).appDAO().loadAllMasmakImgs(AlMasmakData.populateMasmakPalace());
                                    getInstance(context).appDAO().loadAllRiyadhZooImgs(RiyadhZooData.RiyadhZooImages());
                                    getInstance(context).appDAO().loadAllLibImgs(LibraryData.populateNationalLib());
                                }
                            });
                        }
                    })
                    .build();
        }
        return instance;
    }

    public abstract AppDao appDAO();
}
