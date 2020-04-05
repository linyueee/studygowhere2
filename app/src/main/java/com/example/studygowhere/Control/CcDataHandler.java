package com.example.studygowhere.Control;

import com.example.studygowhere.Entity.StudyArea;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPoint;


/**
 * <h1>Community Club data controller</h1>
 * This class implements from DataHandler interface and is used to handle community club data.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class CcDataHandler implements DataHandler {

    /**
     * This is a static variable that is used to ensure the community club objects are only
     * created once.
     */
    public static boolean addCCObjectFlag = false;


    /**
     * This class is responsible for parsing the local geoJSon file and creating Study Area
     * Objects using the data as attributes.
     * The Study Area objects created are passed in to a community club list which is used for filtering
     * and a general Study Area list which contains all Study Areas.
     * @param layer
     */
    public void addObject(GeoJsonLayer layer) {
        for (GeoJsonFeature feature : layer.getFeatures()) {
            StudyArea sa = new StudyArea();
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