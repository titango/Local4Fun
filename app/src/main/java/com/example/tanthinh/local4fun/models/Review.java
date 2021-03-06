package com.example.tanthinh.local4fun.models;

import java.util.Date;

public class Review {

    private String id;
    private String userId;
    private  String fullname;
    private String postId;
    private Date reviewDate;
    private float rating;
    private String comment;

    public Review() {
    }

    public Review(String userId, String fullname, String postId, Date reviewDate, float rating, String comment) {
        this.userId = userId;
        this.fullname = fullname;
        this.postId = postId;
        this.reviewDate = reviewDate;
        this.rating = rating;
        this.comment = comment;
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullname(){
        return fullname;
    }

    public void setFullname(String fullname){
        this.fullname = fullname;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
