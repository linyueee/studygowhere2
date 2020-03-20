package com.example.studygowhere;

import com.google.maps.android.geojson.GeoJsonLayer;

import java.util.ArrayList;
import java.util.List;

public interface Datahandler {
    List<StudyArea> studyAreaList = new ArrayList<>();
    List<StudyArea> schoolList = new ArrayList<>();
    List<StudyArea> ccList = new ArrayList<>();
    List<StudyArea> cafeList = new ArrayList<>();
    List<StudyArea> libList = new ArrayList<>();

    void addObject(GeoJsonLayer layer);

}
