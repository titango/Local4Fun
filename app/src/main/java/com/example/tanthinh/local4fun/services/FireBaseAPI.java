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
import com.example.tanthinh.local4fun.models.Booking;
import com.example.tanthinh.local4fun.models.Post;
import com.example.tanthinh.local4fun.models.User;
import com.example.tanthinh.local4fun.screens.LoginScreen.LoginActivity;
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

import static android.content.ContentValues.TAG;

public class FireBaseAPI {

    private FireBaseResponse apiResponse;
    private Context context;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReference();
    public FireBaseAPI(Activity context)
    {
        this.context = context;
        apiResponse = (FireBaseResponse)context;
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
                    /*Post p = new Post(singleSnapshot.child("userId").getValue().toString(),
                            singleSnapshot.child("title").getValue().toString(),"tourType?",
                            singleSnapshot.child("description").getValue().toString(),
                            Double.parseDouble(singleSnapshot.child("hours").getValue().toString()),
                            Double.parseDouble(singleSnapshot.child("pricePerPerson").getValue().toString()),singleSnapshot.child("location").getValue().toString());

                    for(DataSnapshot picSnapshot : singleSnapshot.child("pictures/addresses").getChildren()){
                        p.addPicture(picSnapshot.getValue().toString());
                    }*/
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

        /*
        ? Send pictures to file storage
         */
        myRef.child("Post").child(id).setValue(p);
        return id;
    }

    public static String insertBooking(Booking b){
        String id = myRef.child("Booking").push().getKey();
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
                        BookingScreenFragment.bookings.add(new Booking(userId,singleSnapshot.child("postId").getValue().toString(),
                                new Date(),((Long)singleSnapshot.child("numberOfPeople").getValue()).intValue()));
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

    public static String insertUser(User u){
        String id = myRef.child("User").push().getKey();
        myRef.child("User").child(id).setValue(u);
        return id;
    }


    public static void getUserAddIfDoesNotExist(final String email){

        final ArrayList<Post> posts = new ArrayList<Post>();

        Query phoneQuery = myRef.child("User");
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String id = "";
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    User u = singleSnapshot.getValue(User.class);
                    if(email.equals(u.getEmail()))
                    {
                        LoginActivity.loginUser.setId(u.getId());
                    }

                }

                if(id.length() == 0){
                    id = myRef.child("User").push().getKey();
                    LoginActivity.loginUser.setId(id);
                    myRef.child("User").child(id).setValue(LoginActivity.loginUser);
                }



            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException());
            }

        });

    }
}
