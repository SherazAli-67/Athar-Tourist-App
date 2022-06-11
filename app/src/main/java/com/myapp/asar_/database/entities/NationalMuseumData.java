package com.myapp.asar_.database.entities;

import static com.myapp.asar_.constants.Constants.MUESUM_ID;

public class NationalMuseumData {

    public static PlaceImages[] populateNationalMuseum() {
        return new PlaceImages[]{
                new PlaceImages("https://www.bautrip.com/images/what-to-visit/national-museum-saudi-arabia.jpg", MUESUM_ID),
                new PlaceImages("https://welcomesaudi.com/uploads/0000/1/2021/07/23/14-king-abdulaziz-historical-center-national-museum-riyadh-riyadh-province.jpg", MUESUM_ID),
                new PlaceImages("https://c8.alamy.com/comp/KW7CFD/riyadh-national-museum-saudi-arabia-KW7CFD.jpg", MUESUM_ID),
                new PlaceImages("https://saudiarabiaofw.com/wp-content/uploads/2014/12/riyadh-national-museum-saudi-arabia.jpg", MUESUM_ID),
                new PlaceImages("https://upload.wikimedia.org/wikipedia/commons/1/18/King_Abdulaziz_National_Park%2C_Historical_Center_%26_Museum_riyadh-saudi-arabia-water-parks.jpg", MUESUM_ID),
        };
    }
}
