package com.example.studygowhere.Control;

import com.example.studygowhere.Entity.StudyArea;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPoint;

public class CcDataHandler implements DataHandler {
    public static boolean addCCObjectFlag = false;

    public void addObject(GeoJsonLayer layer) {
        for (GeoJsonFeature feature : layer.getFeatures()) {
            StudyArea sa = new StudyArea();
            sa.setType("CC");
            if (feature.getProperty("Name") != null) {
                String name = feature.getProperty("Name");
                sa.setName(name);

            }
            if (feature.getProperty("Address") != null) {

                sa.setAddress(feature.getProperty("Address"));
            }
            if (feature.getGeometry() != null) {
                GeoJsonPoint point = (GeoJsonPoint) feature.getGeometry();
                LatLng latLng = point.getCoordinates();
                sa.setLatLng(latLng);
            }

            sa.setImageURL("https://i.postimg.cc/cH8pDmgb/cc.png");
            ccList.add(sa);
            studyAreaList.add(sa);
        }
    }
}