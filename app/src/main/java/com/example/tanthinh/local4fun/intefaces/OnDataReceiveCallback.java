package com.example.tanthinh.local4fun.intefaces;

import com.example.tanthinh.local4fun.models.Post;
import com.example.tanthinh.local4fun.models.Review;
import com.example.tanthinh.local4fun.models.User;

import java.util.ArrayList;

public interface OnDataReceiveCallback {
    void onReviewReceived(ArrayList<Review> list);
    void onPostReceived(ArrayList<Post> list);
    void onUserReceived(ArrayList<User> list);
}
