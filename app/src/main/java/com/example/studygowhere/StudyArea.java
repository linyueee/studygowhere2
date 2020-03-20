package com.example.studygowhere;

import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.Comparator;

public class StudyArea implements Comparable{
    private String name;
    private String imageurl;
    private String address;
    private int zipcode;
    private String website;
    private String type;
    private LatLng latLng;
    private String distance;
    private double distancedouble;

    public StudyArea(String name, String imageurl, String address, int zipcode, String website, String type) {}

    public StudyArea(String name, String imageurl, String address, int zipcode, String website, String type, LatLng latLng) {
        this.name = name;
        this.imageurl = imageurl;
        this.address = address;
        this.zipcode = zipcode;
        this.website = website;
        this.type = type;
        this.latLng = latLng;
    }

    public StudyArea(String name, String imageurl) {
        this.name = name;
        this.imageurl = imageurl;
    }

    public StudyArea() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LatLng getLatLng() { return latLng; }

    public void setLatLng(LatLng latLng) { this.latLng = latLng; }

    public String getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        double inkm = distance/1000;
        double round = Math.round(inkm * 100.0)/100.0;
        this.distance = Double.toString(round);
    }

    public static class sortByDistance implements Comparator<StudyArea>
    {

        @Override
        public int compare(StudyArea o1, StudyArea o2) {
            //return o1.getDistance().compareTo(o2.getDistance());
            return Double.compare(o1.distancedouble, o2.distancedouble);
        }

        @Override
        public Comparator<StudyArea> reversed() {
            return null;
        }
    }
    @Override
    public int compareTo(Object o) {
        return this.getDistance().compareTo(((StudyArea) o).getDistance());

    }

    public double getDistancedouble() {
        return distancedouble;
    }

    public void setDistancedouble(double distancedouble) {
        this.distancedouble = distancedouble;
    }
}
