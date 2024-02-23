package com.example.civiladvocacyapp;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Offical implements Serializable {

    private String normilizedAddress;
    public String name;
    private String office;
    private String party; // Republicans are true Lmaoooooo nah tho go blue actual tho fuck this im not political
    private String address;
    private String email;
    private String phone;
    private String website;
    private String facebook;
    private String twitter;
    private String youtube;
    private String photoUrl;

    public Offical(String normadd, String name, String office, String party, String address, String email, String phone,
                   String website,String fb, String tt, String yt, String photoUrl){
        this.normilizedAddress = normadd;
        this.name = name;
        this.office = office;
        this.party = party;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.facebook = fb;
        this.twitter = tt;
        this.youtube = yt;
        this.photoUrl = photoUrl;
    }

    public String getName() {return name;}

    public String getOffice() {return office;}

    public String getParty() {return party;}

    public String getAddress() {return address;}

    public String getEmail() {return email;}

    public String getPhone() {return phone;}

    public String getWebsite() {return website;}

    public String getNormilizedAddress(){
        return normilizedAddress;
    }

    public String getFacebook() {return facebook;}

    public String getTwitter() {return twitter;}

    public String getYoutube() {return youtube;}

    public String getPhotoUrl() {return photoUrl;}

    //public String getImage() {return image;}

}
