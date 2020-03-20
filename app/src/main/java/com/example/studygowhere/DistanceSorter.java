package com.example.studygowhere;

import com.google.android.gms.maps.model.LatLng;

import java.util.Collections;
import java.util.Comparator;

public class DistanceSorter implements Comparable {
    public double distancedouble;
    private String name;
    private String imageurl;
    private String address;
    private int zipcode;
    private String website;
    private String type;
    private LatLng latLng;
    private String distance;

    public DistanceSorter(double distancedouble, String name, String imageurl, String address, int zipcode, String website, String type, LatLng latLng, String distance) {
        this.distancedouble = distancedouble;
        this.name = name;
        this.imageurl = imageurl;
        this.address = address;
        this.zipcode = zipcode;
        this.website = website;
        this.type = type;
        this.latLng = latLng;
        this.distance = distance;
    }

    public static class sortByDistance implements Comparator<StudyArea>
    {

        @Override
        public int compare(StudyArea o1, StudyArea o2) {
            return o1.getDistance().compareTo(o2.getDistance());
        }
    }
    @Override
    public int compareTo(Object o) {
        return 0;
    }
/*
    @Override
    public int compare(StudyArea o1, StudyArea o2) {


        return o1.getName().compareTo(o2.getName());
    }

    @Override
    public Comparator<StudyArea> reversed() {
        return null;
    }
*/


/*    @Override
    public int compareTo(StudyArea o) {
        double distance = o.getDistancedouble();

        if (this.distance == distance)
            return 0;
        else if (this.distance > distance)
            return 1;
        else
            return -1;
    }*/
/*
    @Override
    public int compareTo(Object o) {
        double distance = ((StudyArea)o).getDistancedouble();

        if (this.distancedouble == distance)
            return 0;
        else if (this.distancedouble > distance)
            return 1;
        else
            return -1;
    }*/
/*    @Override
    public int compareTo(Object o) {

        return this.getClass().compareTo(((Student) o).getName());
    }

                Collections.sort(Datahandler.studyAreaList);*/

}
