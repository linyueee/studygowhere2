package com.example.studygowhere;

import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;

public class Ccdatahandler implements Datahandler {
    static boolean addccObjectFlag = false;

    public void addObject(GeoJsonLayer layer) {
        for(GeoJsonFeature feature:layer.getFeatures())
        {
            StudyArea sa = new StudyArea();
            sa.setType("CC");
            if(feature.getProperty("Name") != null)
            {
                String name = feature.getProperty("Name");
                sa.setName(name);

            }
            if(feature.getProperty("Address") != null)
            {

                sa.setAddress(feature.getProperty("Address"));
            }
            sa.setImageurl("https://i.postimg.cc/cH8pDmgb/cc.png");
            ccList.add(sa);
            studyAreaList.add(sa);
        }
    }
}
