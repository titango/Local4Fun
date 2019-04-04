package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Post;
import com.google.gson.Gson;

public class PayDetailsScreen extends AppCompatActivity {
    private ImageButton back_arrow_btn;
    private Button book_now_btn;

    private double totalPrice = 0;
    private String numberPerson = "";
    private String bookingDate = "";
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
        numberPerson = (String)initIntent.getExtras().get("numberPerson");
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
                Intent payIntent = new Intent(getApplicationContext(), MainActivity.class);
                payIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(payIntent);

                finish();
            }
        });

    }
}
