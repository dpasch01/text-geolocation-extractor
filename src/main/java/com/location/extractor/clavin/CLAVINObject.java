package com.location.extractor.clavin;

import com.bericotech.clavin.gazetteer.GeoName;

public class CLAVINObject {

    private String location;
    private String parent;
    private double latitude;
    private double longitude;

    public CLAVINObject(GeoName geoname) {
        this.location = geoname.getName();
        this.parent = geoname.getParent().getName();
        this.latitude = geoname.getLatitude();
        this.longitude = geoname.getLongitude();
    }

    public CLAVINObject(String location, String parent, double latitude, double longitude) {
        this.location = location;
        this.parent = parent;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public String getParent() {
        return parent;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

}
