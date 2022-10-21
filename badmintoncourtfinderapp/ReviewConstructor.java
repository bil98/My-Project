package com.example.badmintoncourtfinderapp;

public class ReviewConstructor {

    String Address, CourtID, Location , imageurl;
    String Contact;
    double Lat, Lon;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getCourtID() {
        return CourtID;
    }

    public void setCourtID(String courtID) {
        CourtID = courtID;
    }

    public double getLat() {
        return Lat;
    }

    public void setLat(double lat) {
        Lat = lat;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public double getLon() {
        return Lon;
    }

    public void setLon(double lon) {
        Lon = lon;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public ReviewConstructor(String Address, String Contact, String CourtID, double Lat, String Location, double Lon, String imageurl){
        this.Address = Address;
        this.Contact = Contact;
        this.CourtID = CourtID;
        this.Lat = Lat;
        this.Location = Location;
        this.Lon = Lon;
        this.imageurl = imageurl;
    }

    public ReviewConstructor(){}


}
