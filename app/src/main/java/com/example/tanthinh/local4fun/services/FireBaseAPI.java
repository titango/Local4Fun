package com.example.tanthinh.local4fun.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tanthinh.local4fun.intefaces.FireBaseResponse;
import com.example.tanthinh.local4fun.intefaces.OnDataReceiveCallback;
import com.example.tanthinh.local4fun.models.Booking;
import com.example.tanthinh.local4fun.models.Post;
import com.example.tanthinh.local4fun.models.Review;
import com.example.tanthinh.local4fun.models.Singleton;
import com.example.tanthinh.local4fun.models.User;
import com.example.tanthinh.local4fun.screens.LoginActivity;
import com.example.tanthinh.local4fun.screens.ScreenFragment.BookingScreenFragment;
import com.example.tanthinh.local4fun.screens.ScreenFragment.ExploreScreenFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FireBaseAPI {

    private FireBaseResponse apiResponse;
    private Context context;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReference();
    List<User> list = new ArrayList<>();

    public static User getLoginUser() {
        return loginUser;
    }

    public static void setLoginUser(User loginUser) {
        FireBaseAPI.loginUser = loginUser;
    }

    private static User loginUser;

    public FireBaseAPI(Activity context)
    {
        this.context = context;
        apiResponse = (FireBaseResponse)context;
    }
    public FireBaseAPI()
    {
    }

    // Implement different URL call
    public void loginAPI(String url)
    {
        Log.w("doInBackground","doInBackground");

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest requestObject = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest api",response.toString());
                        apiResponse.onLoginFinished();
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest error: ", error.toString());
            }
        });

        requestQueue.add(requestObject);
    }

    public static void getPosts(){

        final ArrayList<Post> posts = new ArrayList<Post>();

        Query phoneQuery = myRef.child("Post");
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Post p = singleSnapshot.getValue(Post.class);
                    ExploreScreenFragment.posts.add(p);
                }

                ExploreScreenFragment.refreshUI();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }

        });

    }

    public static String insertPost(Post p){
        String id = myRef.child("Post").push().getKey();
        p.setId(id);

        myRef.child("Post").child(id).setValue(p);
        return id;
    }



    public static String insertBooking(Booking b){
        String id = myRef.child("Booking").push().getKey();
        b.setId(id);
        myRef.child("Booking").child(id).setValue(b);

        return id;
    }

    public static void getBookings(final String userId){

        final ArrayList<Post> posts = new ArrayList<Post>();

        Query phoneQuery = myRef.child("Booking");
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    if(userId.equals(singleSnapshot.child("userId").getValue().toString()))
                    {
                        BookingScreenFragment.bookings.add(new Booking(
                                userId,
                                singleSnapshot.child("postId").getValue().toString(),
                                singleSnapshot.child("date").getValue().toString(),
                                ((Long)singleSnapshot.child("numberOfPeople").getValue()).intValue(),
                                ((Long)singleSnapshot.child("totalPrice").getValue()).doubleValue(),
                                (singleSnapshot.child("numberTime").getValue().toString())));
                    }

                    //ExploreScreenFragment.posts.add(p);
                }

                BookingScreenFragment.refreshUI();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }

        });

    }


    public static String insertReview(Review u){
        String id = myRef.child("Review").push().getKey();
        myRef.child("Review").child(id).setValue(u);
        u.setId(id);
        return id;
    }


    public static void getUserByEmail(final Singleton singleton, final String email){

        final ArrayList<Post> posts = new ArrayList<Post>();

        Query phoneQuery = myRef.child("User");

        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = "";
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    User u = singleSnapshot.getValue(User.class);
                    if (email!= null){
                        if(email.equals(u.getEmail()))
                        {
//                            String id , fullname, String email, String phone, String description, String username
                            id = u.getId();
                            singleton.loginUser = new User(id ,u.getFullname(), u.getEmail()
                                    , u.getPhone(), u.getDescription(), u.getUsername(), u.getImgUrl());

                        }
                    }

                }
//                if(id.length() == 0){
//                    id = myRef.child("User").push().getKey();
//                    singleton.loginUser.setId(id);
//                    myRef.child("User").child(id).setValue(singleton.loginUser);
//                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }

        });

    }

    public static void searchPosts(final OnDataReceiveCallback callback, final String title){

        final ArrayList<Post> posts = new ArrayList<Post>();

        Query phoneQuery = myRef.child("Post");
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Post p = singleSnapshot.getValue(Post.class);

                    if(p.getTitle().contains(title)){
                        posts.add(p);
                    }

                }

                callback.onPostReceived(posts);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }

        });
    }


    public static void getReviews(final OnDataReceiveCallback callback, final String postId){

        final ArrayList<Review> reviews = new ArrayList<Review>();

        Query phoneQuery = myRef.child("Review");
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    Review p = singleSnapshot.getValue(Review.class);

                    if(postId.equals(p.getPostId())){
                        reviews.add(p);
                    }

                }

                callback.onReviewReceived(reviews);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }

        });
    }

    public static void getPostsOrderBy(final OnDataReceiveCallback callback, String attribute) {

        final ArrayList<Post> posts = new ArrayList<Post>();
        Query query = myRef.child("Post").orderByChild(attribute);
        query.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                            Post p = singleSnapshot.getValue(Post.class);

                            posts.add(p);

                        }

                        callback.onPostReceived(posts);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }

    public static void deletePost(Post p) {
        myRef.child("Post").child(p.getId()).removeValue();
    }

    public static void updateUser(User u) {

        myRef.child("User").child(u.getId()).setValue(u);

    }
    public static String insertUser(User u){
        String id = myRef.child("User").push().getKey();
        myRef.child("User").child(id).setValue(u);

        u.setId(id);
        updateUser(u);
        return id;
    }

    public static void getAllUsers(final OnDataReceiveCallback callback){

        final ArrayList<User> users = new ArrayList<User>();

        Query phoneQuery = myRef.child("User");
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    User u = singleSnapshot.getValue(User.class);
                    users.add(u);
                }

                callback.onUserReceived(users);

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }

        });
    }


}