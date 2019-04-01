package com.example.tanthinh.local4fun.models;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class    Post {


    private String postId;
    private String title;
    private String userId;
    private Double hours;
    private Double pricePerPerson;
    private String tourType;
    private String location;
    private String description;

    private ArrayList<String> pictures = new ArrayList<String>();
    public ArrayList<String> plan = new ArrayList<String>();

    public Post(){

    }

    public Post(String userId, String title, String tourType, String description, Double hours, Double pricePerPerson, String location) {
        this.title = title;
        this.tourType = tourType;
        this.userId = userId;
        this.hours = hours;
        this.pricePerPerson = pricePerPerson;
        this.location = location;
        this.description = description;
    }

    public Post(String postId, String userId, String title,  String tourType,String description, Double hours, Double pricePerPerson,  String location, ArrayList<String> pictures) {
        this.postId = postId;
        this.title = title;
        this.userId = userId;
        this.hours = hours;
        this.pricePerPerson = pricePerPerson;
        this.tourType = tourType;
        this.location = location;
        this.pictures = pictures;
        this.description = description;
    }

    public ArrayList<String> getPlan() {
        return plan;
    }

    public void setPlan(ArrayList<String> plan) {
        this.plan = plan;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }
    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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



}
