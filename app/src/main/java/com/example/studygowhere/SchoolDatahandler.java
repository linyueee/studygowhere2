package com.example.studygowhere;

import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;

public class SchoolDatahandler implements Datahandler {

    static boolean addschoolObjectFlag = false;

    public void addObject(GeoJsonLayer layer) {
        for(GeoJsonFeature feature:layer.getFeatures())
        {
            StudyArea sa = new StudyArea();
            sa.setType("School");
            if(feature.getProperty("Name") != null)
            {
                String name = feature.getProperty("Name");
                sa.setName(name);

            }
            if(feature.getProperty("Photo") != null)
            {

                sa.setImageurl(feature.getProperty("Photo"));
            }
            if(feature.getProperty("Address") != null)
            {

                sa.setAddress(feature.getProperty("Address"));
            }

            studyAreaList.add(sa);
        }
    }
}
