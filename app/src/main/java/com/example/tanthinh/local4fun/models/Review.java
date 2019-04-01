package com.example.tanthinh.local4fun.models;

import java.util.Date;

public class Review {

    private String userId;
    private String postId;
    private Date reviewDate;
    private int rating;
    private String comment;

    public Review() {
    }

    public Review(String userId, String postId, Date reviewDate, int rating, String comment) {
        this.userId = userId;
        this.postId = postId;
        this.reviewDate = reviewDate;
        this.rating = rating;
        this.comment = comment;
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

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
