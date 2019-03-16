package com.example.tanthinh.local4fun.screens;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.tanthinh.local4fun.R;

public class CreateNewPostScreen extends AppCompatActivity {
    Button btnCreatePost;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post_screen);

        btnCreatePost = (Button)findViewById(R.id.btnCreatePost);
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

}
