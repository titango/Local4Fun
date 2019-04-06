package com.example.tanthinh.local4fun.models;

import java.util.Date;

public class Booking {

    private String id;
    private String postId;
    private String userId;
    private Date date;
    private int numberOfPeople;

    public Booking(String userId,String postId, Date date, int numberOfPeople) {
        this.userId = userId;
        this.postId = postId;
        this.date = date;
        this.numberOfPeople = numberOfPeople;
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

    public void setPostId(Post post) {
        this.postId = postId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }
}
