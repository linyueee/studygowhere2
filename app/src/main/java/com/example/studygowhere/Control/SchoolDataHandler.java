package com.example.studygowhere.Control;

import com.example.studygowhere.Entity.StudyArea;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPoint;

public class SchoolDataHandler implements DataHandler {

    public static boolean addSchoolObjectFlag = false;

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
                sa.setImageURL(feature.getProperty("Photo"));
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
            schoolList.add(sa);
            studyAreaList.add(sa);
        }
    }
}
