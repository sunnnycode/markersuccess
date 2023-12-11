package com.goldenkids.test;

public class flood {
    private String lat;
    private String lang;
    private String location;
    private String km;

    public flood(String lat, String lang, String location, String km) {
        this.lat = lat;
        this.lang = lang;
        this.location = location;
        this.km = km;
    }

    //retrieve user's name
    public String getLat(){
        return lat;
    }

    //retrieve users' hometown
    public String getLang(){
        return lang;
    }

    public String getLocation(){
        return location;
    }

    public String getKm() {
        return km;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setKm(String km) {
        this.km = km;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}