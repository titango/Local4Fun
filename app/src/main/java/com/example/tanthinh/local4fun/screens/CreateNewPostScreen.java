package com.example.tanthinh.local4fun.screens;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Post;
import com.example.tanthinh.local4fun.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class CreateNewPostScreen extends AppCompatActivity {
    private static final String TAG = CreateNewPostScreen.class.getSimpleName();
    private EditText editTextPostName, editTextMeetingPoint, editTextDescription;
    private Spinner spinnerTour, spinnerDuration, spinnerPrice;
    private Button btnCreatePost;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private String userId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post_screen);

        editTextPostName = (EditText)findViewById(R.id.editTextPostName);
        editTextMeetingPoint = (EditText)findViewById(R.id.editTextMeetingPoint);
        editTextDescription = (EditText)findViewById(R.id.editTextDescription);
        spinnerTour = (Spinner)findViewById(R.id.spinnerTour);
        spinnerDuration = (Spinner)findViewById(R.id.spinnerDuration);
        spinnerPrice = (Spinner)findViewById(R.id.spinnerPrice);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("posts");

//        mFirebaseDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Post post = null;
//                for (DataSnapshot child : dataSnapshot.getChildren()) {
//                    post = child.getValue(Post.class);
//                    userId = child.getKey();
//                    //txtDetails.setText(user.name + ", " + user.email);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        btnCreatePost = (Button)findViewById(R.id.btnCreatePost);
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                userId =  FirebaseAuth.getInstance().getCurrentUser().getUid();
                String postName = editTextPostName.getText().toString();
                String tourType = spinnerTour.getSelectedItem().toString();
                String description = editTextDescription.getText().toString();
                Double duration = Double.parseDouble(spinnerDuration.getSelectedItem().toString());
                Double price = Double.parseDouble(spinnerPrice.getSelectedItem().toString());
                String location = editTextMeetingPoint.getText().toString();

//                if (TextUtils.isEmpty(userId)) {
                    createPost(userId, postName, tourType, description, duration, price, location);

//                }
//                else {
//                    updatePost(userId, postName, tourType, duration, price, location);
//                }

                alertDialog();
            }
        });

    }

    private void createPost(String userId, String postName, String tourType, String description,
                            Double duration, Double price, String location) {
        // TODO
        // In real apps this userId should be fetched
        // by implementing firebase auth
//        if (TextUtils.isEmpty(userId)) {
//            userId = mFirebaseDatabase.push().getKey();
//        }
//
//        Post post = new Post(userId, postName, tourType, duration, price, location);
//
//        mFirebaseDatabase.child(userId).setValue(post);
//
//        addPostChangeListener();

        String key = mFirebaseDatabase.child("posts").push().getKey();
        Post post = new Post(userId, postName, tourType, description, duration, price, location);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" ,postValues);
        //childUpdates.put("/users/" + userId + "/" + key, postValues);

        mFirebaseDatabase.updateChildren(childUpdates);
    }

    private void addPostChangeListener() {
        // User data change listener
        mFirebaseDatabase.child(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);

                // Check for null
                if (post == null) {
                    Log.e(TAG, "Post data is null!");
                    return;
                }

                Log.e(TAG, "Post data is changed!" + post.getUserId() + ", " + post.getTitle());



                // clear edit text
                editTextPostName.setText("");
                editTextMeetingPoint.setText("");
                editTextDescription.setText("");
                spinnerDuration.setSelection(0);
                spinnerPrice.setSelection(0);
                spinnerTour.setSelection(0);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read user", error.toException());
            }
        });
    }

//    private void updatePost(String userId, String postName, String tourType,
//                            Double duration, Double price, String location) {
//        // updating the user via child nodes
//        if (postName != "")
//            mFirebaseDatabase.child(postName).child("title").setValue(postName);
//
//        if (tourType != "")
//            mFirebaseDatabase.child(tourType).child("tour").setValue(tourType);
//
//        if(userId != "")
//            mFirebaseDatabase.child(userId).child("userId").setValue(userId);
//
//        if(duration != 0)
//            mFirebaseDatabase.child(String.valueOf(duration)).child("hours").setValue(duration);
//
//        if(price !=0)
//            mFirebaseDatabase.child(String.valueOf(price)).child("pricePerPerson").setValue(price);
//
//        if(location != "")
//            mFirebaseDatabase.child(location).child("location").setValue(location);
//
//    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        mFirebaseDatabase.child("users").child(userId).setValue(user);
    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("New Post was successfully created");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        //alertDialog.getWindow().getAttributes();

        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(18);
        textView.setTextColor(Color.parseColor("#3F5C73"));
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        textView.setTypeface(boldTypeface);
        Button mButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        mButton.setTextColor(Color.WHITE);
        mButton.setBackgroundColor(Color.parseColor("#6895BB"));

   }

}
