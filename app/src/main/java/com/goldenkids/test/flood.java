package com.goldenkids.test;

public class flood {
    private String lat;
    private String lang;
    private String location;

    public flood(String lat, String lang,String location) {
        this.lat = lat;
        this.lang = lang;
        this.location = location;
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

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
