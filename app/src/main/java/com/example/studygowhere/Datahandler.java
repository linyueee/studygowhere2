package com.example.studygowhere;

import com.google.maps.android.geojson.GeoJsonLayer;

import java.util.ArrayList;
import java.util.List;

public interface Datahandler {
    List<Object> studyAreaList = new ArrayList<>();
    List<Object> schoolList = new ArrayList<>();
    List<Object> ccList = new ArrayList<>();
    List<Object> cafeList = new ArrayList<>();
    List<Object> libList = new ArrayList<>();

    void addObject(GeoJsonLayer layer);

}
