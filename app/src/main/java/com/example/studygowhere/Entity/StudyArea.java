package com.example.studygowhere.Entity;

import com.google.android.gms.maps.model.LatLng;

/**
 * <h1>StudyArea Entity</h1>
 * This is a StudyArea Entity class that specify the attributes of StudyArea object.
 * @author ILOVESSADMORE
 * @version 1.0
 */
public class StudyArea{
    /**
     * Instance variable that stores the name of the StudyArea
     */
    private String name;

    /**
     * Instance variable that stores the URL of the image of the StudyArea
     */
    private String imageURL;

    /**
     * Instance variable that stores the address of the StudyArea
     */
    private String address;

    /**
     * Instance variable that stores the latitude and longitude of the StudyArea
     */
    private LatLng latLng;

    /**
     * Instance variable that stores the distance away from user current location
     */
    private String distance;

    /**
     * Instance variable that stores the distance away from user current location
     */
    private double distanceDouble;


    /**
     * Constructor
     */
    public StudyArea() {

    }

    /**
     * Getter method of instance variable name
     * @return StudyArea name
     */
    public String getName() {
        return name;
    }


    /**
     * Setter method of instance variable name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Getter method of instance variable imageURL
     * @return URL of the image
     */
    public String getImageURL() {
        return imageURL;
    }


    /**
     * Setter method of instance variable imageURL
     * @param imageURL
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }


    /**
     * Getter method of instance variable address
     * @return address
     */
    public String getAddress() {
        return address;
    }


    /**
     * Setter method of instance variable address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Getter method of instance variable latLng
     * @return latitude and longitude
     */
    public LatLng getLatLng() { return latLng; }


    /**
     * Setter method of instance variable latLng
     * @param latLng
     */
    public void setLatLng(LatLng latLng) { this.latLng = latLng; }


    /**
     * Getter method of instance variable distance
     * @return distance
     */
    public String getDistance() {
        return distance;
    }


    /**
     * Setter method of instance variable distance
     * This method convert the unit of the distance passed in and round it up.
     * @param distance
     */
    public void setDistance(double distance) {
        double inkm = distance/1000;
        double round = Math.round(inkm * 100.0)/100.0;
        this.distance = Double.toString(round);
    }



    /**
     * Getter method of instance variable distanceDouble
     * @return distance in double
     */
    public double getDistanceDouble() {
        return distanceDouble;
    }


    /**
     * Setter method of instance variable distanceDouble
     * @param distanceDouble
     */
    public void setDistanceDouble(double distanceDouble) {
        this.distanceDouble = distanceDouble;
    }
}
