package com.example.studygowhere.Entity;

import com.google.android.gms.maps.model.LatLng;

import java.util.Comparator;

public class StudyArea implements Comparable{
    private String name;
    private String imageURL;
    private String address;
    private int zipCode;
    private String website;
    private String type;
    private LatLng latLng;
    private String distance;
    private double distanceDouble;

    public StudyArea(String name, String imageURL, String address, int zipCode, String website, String type) {}

    public StudyArea(String name, String imageURL, String address, int zipCode, String website, String type, LatLng latLng) {
        this.name = name;
        this.imageURL = imageURL;
        this.address = address;
        this.zipCode = zipCode;
        this.website = website;
        this.type = type;
        this.latLng = latLng;
    }

    public StudyArea(String name, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
    }

    public StudyArea() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
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
            return Double.compare(o1.distanceDouble, o2.distanceDouble);
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

    public double getDistanceDouble() {
        return distanceDouble;
    }

    public void setDistanceDouble(double distanceDouble) {
        this.distanceDouble = distanceDouble;
    }
}
