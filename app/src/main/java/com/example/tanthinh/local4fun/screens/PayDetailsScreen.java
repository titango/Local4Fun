package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Post;
import com.example.tanthinh.local4fun.screens.ScreenFragment.myFragment;
import com.google.gson.Gson;

public class PayDetailsScreen extends AppCompatActivity {
    private ImageButton back_arrow_btn;
    private Button book_now_btn;

    private double totalPrice = 0;
    private int numberPerson = 0;
    private String bookingDate = "";
    private String numberTime = "";

    private Post currentPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_details_screen);

        Intent initIntent = getIntent();
        final String postString = (String)initIntent.getExtras().get("postObject");
        Gson gson = new Gson();
        currentPost = gson.fromJson(postString, Post.class);
        totalPrice = (double)initIntent.getExtras().get("totalPrice");
        numberPerson = (int)initIntent.getExtras().get("numberPerson");
        numberTime = (String)initIntent.getExtras().get("numberTime");
        bookingDate = (String)initIntent.getExtras().get("bookingDate");

        back_arrow_btn = findViewById(R.id.back_arrow_btn);
        back_arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        book_now_btn = findViewById(R.id.book_now_btn);
        book_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myFragment fragment
                        = new myFragment();
                fragment.setData(currentPost,numberPerson,bookingDate,totalPrice,numberTime);
                fragment.show(getFragmentManager(), "my fragment");

            }
        });

    }
}
