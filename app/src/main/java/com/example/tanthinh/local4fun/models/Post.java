package com.example.tanthinh.local4fun.models;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Post {

    private String title;
    private String userId;
    private Double hours;
    private Double pricePerPerson;
    private String tourType;
    private String description;
    private String location;
    public Map<String, Boolean> stars = new HashMap<>();

    private ArrayList<String> pictures = new ArrayList<String>();

    public Post(){

    }

    public Post(String userId, String title, String tourType, String description, Double hours, Double pricePerPerson, String location ) {
        this.title = title;
        this.tourType = tourType;
        this.description = description;
        this.userId = userId;
        this.hours = hours;
        this.pricePerPerson = pricePerPerson;
        this.location = location;

    }



    public ArrayList<String> getPictures() {
        return pictures;
    }
    public void addPicture(String path){
        pictures.add(path);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }

    public Double getPricePerPerson() {
        return pricePerPerson;
    }

    public void setPricePerPerson(Double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public String getTourType() {
        return tourType;
    }

    public void setTourType(String tourType) {
        this.tourType = tourType;
    }

    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public  void setDescription(String description){
        this.description = description;
    }
    public  String getDescription(){
        return description;
    }


    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("userId", userId);
        result.put("hours", hours);
        result.put("pricePerPerson", pricePerPerson);
        result.put("tour", tourType);
        result.put("description", description);
        result.put("location", location);
        result.put("stars", stars);

        return result;
    }
}
