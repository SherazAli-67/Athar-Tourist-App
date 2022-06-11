package com.myapp.asar_.database.entities;

import static com.myapp.asar_.constants.Constants.ZOO_ID;

public class RiyadhZooData {
    public static PlaceImages[] RiyadhZooImages() {
        return new PlaceImages[]{
                new PlaceImages("https://lovin.co/wp-content/uploads/sites/9/2019/12/header-image-template.001-17.jpeg", ZOO_ID),
                new PlaceImages("https://media-cdn.tripadvisor.com/media/photo-s/01/6a/ed/57/riyadh.jpg", ZOO_ID),
                new PlaceImages("https://3tsll33cscvk11pae33oze51-wpengine.netdna-ssl.com/wp-content/uploads/2017/11/riyadh-zoo.png",ZOO_ID),
                new PlaceImages("https://image.shutterstock.com/image-photo/animals-riyadh-zoo-260nw-794189194.jpg", ZOO_ID),
                new PlaceImages("https://ksaexpats.com/wp-content/uploads/2022/02/Elephants-in-Riyadh-zoo-768x1024.jpeg", ZOO_ID),
        };
    }
}
