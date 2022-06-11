package com.myapp.asar_.database.entities;

import static com.myapp.asar_.constants.Constants.LIBRARY_ID;

public class LibraryData {
    public static PlaceImages[] populateNationalLib() {
        return new PlaceImages[]{
                new PlaceImages("https://img.archilovers.com/story/15d791c0-f2d0-4b34-9eb8-c79e1b462943.jpg", LIBRARY_ID),
                new PlaceImages("https://i.pinimg.com/originals/a1/1b/e4/a11be4c3f773ab0e914b4cdf989c4d53.jpg",LIBRARY_ID),
                new PlaceImages("https://www.designbuild-network.com/wp-content/uploads/sites/26/2017/10/2-king-fahad-library.jpg",LIBRARY_ID),
                new PlaceImages("https://arquitecturaviva.com/assets/uploads/obras/44227/av_imagen.jpeg",LIBRARY_ID),
                new PlaceImages("https://files.structurae.net/files/photos/5180/03_fassade-von-innen.jpg",LIBRARY_ID),
        };
    }
}
