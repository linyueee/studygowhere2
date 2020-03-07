package com.example.studygowhere;

import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;

public class Starbucksdatahandler implements Datahandler {
    static boolean addsbObjectFlag = false;

    public void addObject(GeoJsonLayer layer) {
        for(GeoJsonFeature feature:layer.getFeatures())
        {
            StudyArea sa = new StudyArea();
            sa.setType("Starbucks");
            if(feature.getProperty("Name") != null)
            {
                String name = feature.getProperty("Name");
                sa.setName(name);

            }
            if(feature.getProperty("Address") != null)
            {

                sa.setAddress(feature.getProperty("Address"));
            }
            sa.setImageurl("https://i.postimg.cc/SKT3Q84G/starbucks-logo.png");
            studyAreaList.add(sa);
        }
    }
}
