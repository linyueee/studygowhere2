package com.example.studygowhere;

import android.widget.ImageView;

public class StudyArea {
    private String name;
    private String imageurl;
    private String address;
    private int zipcode;
    private String website;
    private String type;


    public StudyArea(String name, String imageurl, String address, int zipcode, String website, String type) {
        this.name = name;
        this.imageurl = imageurl;
        this.address = address;
        this.zipcode = zipcode;
        this.website = website;
        this.type = type;
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
}
