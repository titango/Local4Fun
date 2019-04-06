package com.example.tanthinh.local4fun.models;

import java.util.Date;

public class Booking {

    private String id;
    private String postId;
    private String userId;
    private String date;
    private int numberOfPeople;
    private double totalPrice;
    private String time;

    public Booking(String userId,String postId, String date, int numberOfPeople, double price, String time) {
        this.userId = userId;
        this.postId = postId;
        this.date = date;
        this.numberOfPeople = numberOfPeople;
        this.totalPrice = price;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

//    public void setPostId(Post post) {
//        this.postId = postId;
//    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
