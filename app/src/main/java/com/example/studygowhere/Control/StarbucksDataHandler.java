package com.example.studygowhere.Control;

import com.example.studygowhere.Entity.StudyArea;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPoint;

public class StarbucksDataHandler implements DataHandler {
    public static boolean addSBObjectFlag = false;

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
            if(feature.getGeometry()!= null)
            {
                GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
                LatLng latLng = point.getCoordinates();
                sa.setLatLng(latLng);
            }

            sa.setImageURL("https://i.postimg.cc/SKT3Q84G/starbucks-logo.png");
            cafeList.add(sa);
            studyAreaList.add(sa);
        }
    }
}