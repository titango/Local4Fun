package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.adapters.ViewPagerAdapter;
import com.example.tanthinh.local4fun.models.Post;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class PostDetailsScreen extends AppCompatActivity implements OnMapReadyCallback {

    //GOOGLE MAP
    GoogleMap mMap;
    Marker marker;
    String location;
    LatLng ll;

    private ViewPagerAdapter viewPagerAdapter;
    public ViewPager viewPager; // for slider
    private CircleIndicator indicator;
    private ImageButton back_arrow_btn;

    public TextView postTitle;
    public TextView postHour;
    public TextView postTour;
    public TextView postPrice;
    public TextView postLocation;
    public Post currentPost;

    private LinearLayout locationMarkerWrapper;
    private LinearLayout localtionLabelWrapper;

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
        currentPost = gson.fromJson(postString, Post.class);
//        Log.w("CurPost", curPost.getUserId());

        ll = initIntent.getParcelableExtra("latLon");
        location = (String) initIntent.getSerializableExtra("desc");

        SupportMapFragment mapFrag = (SupportMapFragment)
                getSupportFragmentManager().
                        findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);

        viewPager = findViewById(R.id.viewPager);
        postTitle = findViewById(R.id.explore_post_title);
        postHour = findViewById(R.id.explore_post_hour);
        postTour = findViewById(R.id.explore_tour);
        postPrice = findViewById(R.id.explore_price_person);
        postLocation = findViewById(R.id.explore_location);
        back_arrow_btn = findViewById(R.id.back_arrow_btn);

        viewPagerAdapter = new ViewPagerAdapter(getApplication(), currentPost.getPictures());
        viewPager.setAdapter(viewPagerAdapter);

        indicator = (CircleIndicator) findViewById(R.id.viewPagerIndicator);
        indicator.setViewPager(viewPager);
        viewPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        postTitle.setText(currentPost.getTitle());
        postHour.setText(currentPost.getHours() + "");
        postTour.setText(currentPost.getTourType() + "");
        postPrice.setText(currentPost.getPricePerPerson() + "");
        postLocation.setText(currentPost.getLocation() + "");

        back_arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        displayPlanSection();
    }

    private void displayPlanSection()
    {
        locationMarkerWrapper = (LinearLayout)findViewById(R.id.location_marker_wrapper);
        localtionLabelWrapper = (LinearLayout)findViewById(R.id.location_label_wrapper);

        // FAKE DATA NOW
        currentPost.plan = new ArrayList<>();
        currentPost.plan.add("New West Station && We will meet at new west station first");
        currentPost.plan.add("Metrotown && Check out shopping at Metrotown");
        currentPost.plan.add("Downtown && Center of Vancouver!!");


        for(int i = 0; i < currentPost.plan.size(); i++)
        {
            // String text plans
            String[] splitPlan = currentPost.plan.get(i).split("&&");
            String planMarker = splitPlan[0];
            String planLabel = splitPlan[1];

            String textWrapper = "";
            if( i == 0)
            {
                textWrapper = "Start";
            }else if(i == currentPost.plan.size() - 1)
            {
                textWrapper = "End";
            }else
            {
                textWrapper = "Stop " + i;
            }

            LinearLayout markerLayout = createMarkerLayout(textWrapper);
            LinearLayout labelLayout = createLabelLayout(planMarker.trim(), planLabel.trim());

            locationMarkerWrapper.addView(markerLayout);
            localtionLabelWrapper.addView(labelLayout);

        }

    }

    private LinearLayout createMarkerLayout(String txt)
    {
        LinearLayout lo = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lp.weight = 1;
        lp.gravity = Gravity.CENTER;
        lp.setMargins(0,10,0,10);

        lo.setLayoutParams(lp);
        lo.setPadding(10,10,10,10);
        lo.setOrientation(LinearLayout.VERTICAL);

        // IMAGE attach
        ImageView imgView = new ImageView(getApplicationContext());

        if(txt == "Start")
            imgView.setImageResource(R.drawable.ic_location_marker_icon_green);
        else if(txt == "End")
            imgView.setImageResource(R.drawable.ic_location_marker_icon_orange);
        else
            imgView.setImageResource(R.drawable.ic_location_marker_icon);

        // TEXT attach
        TextView txtWrapper = new TextView(getApplicationContext());
        LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        txtWrapper.setLayoutParams(txtParams);
        txtWrapper.setText(txt);

        lo.addView(imgView);
        lo.addView(txtWrapper);

        return lo;

    }

    private LinearLayout createLabelLayout(String txt1, String txt2)
    {
        LinearLayout lo = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        lp.setMargins(0,10,0,10);
        lo.setPadding(20,10,10,10);
        lo.setOrientation(LinearLayout.VERTICAL);
        lo.setLayoutParams(lp);


        // TEXT attach1
        TextView txtWrapper = new TextView(getApplicationContext());
        LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        txtWrapper.setLayoutParams(txtParams);
        txtWrapper.setText(txt1);
        txtWrapper.setTextSize(18.0f);
        txtWrapper.setTypeface(null, Typeface.BOLD);

        // TEXT attach2
        TextView txtWrapper2 = new TextView(getApplicationContext());
        LinearLayout.LayoutParams txtParams2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        txtWrapper2.setLayoutParams(txtParams2);
        txtWrapper2.setText(txt2);

        lo.addView(txtWrapper);
        lo.addView(txtWrapper2);

        return lo;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        if (ll != null) {


            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, 15);
            mMap.moveCamera(update);
            // Add a marker for location/description sent from MainActivity
            if(marker != null){
                marker.remove();
            }
            MarkerOptions markerOptions = new MarkerOptions()
                    .title(location)
                    .position(ll);
            marker = mMap.addMarker(markerOptions);
            //  mMap.addMarker(new MarkerOptions().position(ll));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
        }
    }
}
