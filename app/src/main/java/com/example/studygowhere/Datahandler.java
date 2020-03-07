package com.example.studygowhere;

import com.google.maps.android.geojson.GeoJsonLayer;

import java.util.ArrayList;
import java.util.List;

public interface Datahandler {
    List<Object> studyAreaList = new ArrayList<>();

    void addObject(GeoJsonLayer layer);

}
