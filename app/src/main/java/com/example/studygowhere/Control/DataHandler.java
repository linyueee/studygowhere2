package com.example.studygowhere.Control;

import com.example.studygowhere.Entity.StudyArea;
import com.google.maps.android.geojson.GeoJsonLayer;

import java.util.ArrayList;
import java.util.List;


/**
 * <h1>Data controller</h1>
 * This is a interface which all data handler will implement from.
 *
 * @author ILOVESSADMORE
 * @version 1.0
 */
public interface DataHandler {
    /**
     * ArrayList that contains all Study Area Objects.
     */
    List<StudyArea> studyAreaList = new ArrayList<>();

    /**
     * ArrayList that contains all School Objects.
     */
    List<StudyArea> schoolList = new ArrayList<>();

    /**
     * ArrayList that contains all Community Club Objects.
     */
    List<StudyArea> ccList = new ArrayList<>();

    /**
     * ArrayList that contains all Mcdonald's and Starbucks Objects.
     */
    List<StudyArea> cafeList = new ArrayList<>();

    /**
     * ArrayList that contains all Library Objects.
     */
    List<StudyArea> libList = new ArrayList<>();


    /**
     * Abstract method
     * @param layer GeoJsonLayer
     */
    void addObject(GeoJsonLayer layer);

}
