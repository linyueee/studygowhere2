package com.example.studygowhere.Control;

import com.example.studygowhere.Entity.StudyArea;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.geojson.GeoJsonFeature;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.geojson.GeoJsonPoint;



/**
 * <h1>Library data controller</h1>
 * This class implements from DataHandler interface and is used to handle library data.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class LibraryDataHandler implements DataHandler {
    /**
     * This is a static variable that is used to ensure the library objects are only
     * created once.
     */
    public static boolean addLibObjectFlag = false;

    /**
     * This class is responsible for parsing the local geoJSon file and creating Study Area
     * Objects using the data as attributes.
     * The Study Area objects created are passed in to a library list which is used for filtering
     * and a general Study Area list which contains all Study Areas.
     * @param layer
     */
    public void addObject(GeoJsonLayer layer) {
        for(GeoJsonFeature feature:layer.getFeatures())
        {
            StudyArea sa = new StudyArea();
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
            libList.add(sa);
            studyAreaList.add(sa);
        }
    }
}
