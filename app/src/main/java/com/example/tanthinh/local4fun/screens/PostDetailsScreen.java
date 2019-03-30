package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.adapters.ViewPagerAdapter;
import com.example.tanthinh.local4fun.models.Post;
import com.google.gson.Gson;

import me.relex.circleindicator.CircleIndicator;

public class PostDetailsScreen extends AppCompatActivity {

    private ViewPagerAdapter viewPagerAdapter;
    public ViewPager viewPager; // for slider
    private CircleIndicator indicator;
    private ImageButton back_arrow_btn;

    public TextView postTitle;
    public TextView postHour;
    public TextView postTour;
    public TextView postPrice;
    public TextView postLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details_screen);

        init();
    }

    private void init()
    {
        //Get post object
        Intent initIntent = getIntent();
        String postString = (String)initIntent.getExtras().get("postObject");
        Gson gson = new Gson();
        Post curPost = gson.fromJson(postString, Post.class);
        Log.w("CurPost", curPost.getUserId());

        viewPager = findViewById(R.id.viewPager);
        postTitle = findViewById(R.id.explore_post_title);
        postHour = findViewById(R.id.explore_post_hour);
        postTour = findViewById(R.id.explore_tour);
        postPrice = findViewById(R.id.explore_price_person);
        postLocation = findViewById(R.id.explore_location);
        back_arrow_btn = findViewById(R.id.back_arrow_btn);

        viewPagerAdapter = new ViewPagerAdapter(getApplication(), curPost.getPictures());
        viewPager.setAdapter(viewPagerAdapter);

        indicator = (CircleIndicator) findViewById(R.id.viewPagerIndicator);
        indicator.setViewPager(viewPager);
        viewPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        postTitle.setText(curPost.getTitle());
        postHour.setText(curPost.getHours() + "");
        postTour.setText(curPost.getTourType() + "");
        postPrice.setText(curPost.getPricePerPerson() + "");
        postLocation.setText(curPost.getLocation() + "");

        back_arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
