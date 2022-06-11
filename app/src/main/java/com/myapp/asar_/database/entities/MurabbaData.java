package com.myapp.asar_.database.entities;

import static com.myapp.asar_.constants.Constants.MURABBA_ID;

public class MurabbaData {
    public static PlaceImages[] populateMurabbaImages() {
        return new PlaceImages[]{
                new PlaceImages("https://static1.bigstockphoto.com/8/6/1/large1500/168734549.jpg", MURABBA_ID),
                new PlaceImages("https://c8.alamy.com/comp/2B8CBBF/riyadh-riyadh-saudi-arabia-march-07-2020-view-of-the-murabba-palace-qasr-al-murabba-is-historic-building-2B8CBBF.jpg",MURABBA_ID),
                new PlaceImages("https://image.shutterstock.com/shutterstock/photos/323842466/display_1500/stock-photo-saudi-arabia-riyadh-the-murabba-palace-323842466.jpg",MURABBA_ID),
                new PlaceImages("https://9968c6ef49dc043599a5-e151928c3d69a5a4a2d07a8bf3efa90a.ssl.cf2.rackcdn.com/326762-2.jpg",MURABBA_ID),
                new PlaceImages("https://hayatalriyadh.com/wp-content/uploads/2021/06/Riyadh-Murabba-Palace.jpg", MURABBA_ID)
        };
    }
}
