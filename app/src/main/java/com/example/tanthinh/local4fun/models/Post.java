package com.example.tanthinh.local4fun.models;

import java.util.ArrayList;

public class Post {

    private String title;
    private String description;
    private String userId;
    private Double hours;
    private Double pricePerPerson;
    private ArrayList<String> pictures = new ArrayList<String>();

    public Post(String title, String description, String userId, Double hours, Double pricePerPerson) {
        this.title = title;
        this.description = description;
        this.userId = userId;
        this.hours = hours;
        this.pricePerPerson = pricePerPerson;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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




}
