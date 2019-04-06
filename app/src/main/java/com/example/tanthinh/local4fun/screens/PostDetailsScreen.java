package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.adapters.ViewPagerAdapter;
import com.example.tanthinh.local4fun.intefaces.OnDataReceiveCallback;
import com.example.tanthinh.local4fun.models.Post;
import com.example.tanthinh.local4fun.models.Review;
import com.example.tanthinh.local4fun.services.FireBaseAPI;
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

import java.util.Date;


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
    private Button book_now_btn;

    public TextView postTitle;
    public TextView postHour;
    public TextView postTour;
    public TextView postPrice;
    public TextView postLocation;
    public TextView summaryTextView;
    public TextView descriptionTextView;
    public TextView mapInfoTextView;
    public Post currentPost;

    private LinearLayout locationMarkerWrapper;
    private LinearLayout localtionLabelWrapper;
    private LinearLayout reviewsWrapper;

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
        final String postString = (String)initIntent.getExtras().get("postObject");
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
        book_now_btn = findViewById(R.id.book_now_btn);

        viewPagerAdapter = new ViewPagerAdapter(getApplication(), currentPost.getPictures());
        viewPager.setAdapter(viewPagerAdapter);

        indicator = (CircleIndicator) findViewById(R.id.viewPagerIndicator);
        indicator.setViewPager(viewPager);
        viewPagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        postTitle.setText(currentPost.getTitle());
        postHour.setText(currentPost.getHours() + " hrs");
        postTour.setText(currentPost.getTourType() + "");
        postPrice.setText("$"+currentPost.getPricePerPerson() + "");
        postLocation.setText(currentPost.getLocation() + "");

        back_arrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        book_now_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent payIntent = new Intent(getApplicationContext(), CheckBookingScreen.class);
                payIntent.putExtra("postObject", postString);
                startActivity(payIntent);
            }
        });

        displayDescriptionSummary();

        displayPlanSection();
        displayReviewSection();

        //Review r = new Review("-LbaIAoqazHPSY-cUms9", currentPost.getId(), new Date(), 3, "Thank you for your service!");
        //FireBaseAPI.insertReview(r);

    }

    private void displayDescriptionSummary()
    {
        descriptionTextView = findViewById(R.id.descriptionTextView);
        summaryTextView = findViewById(R.id.summaryTextView);
        mapInfoTextView = findViewById(R.id.mapInfoTextView);

        descriptionTextView.setText(currentPost.getDescription());
        summaryTextView.setText(currentPost.getSummary());

        mapInfoTextView.setText(currentPost.getLocation());
    }

    private void displayPlanSection()
    {
        locationMarkerWrapper = (LinearLayout)findViewById(R.id.location_marker_wrapper);
        localtionLabelWrapper = (LinearLayout)findViewById(R.id.location_label_wrapper);

        // FAKE DATA NOW

        //currentPost.plan = new ArrayList<>();
        //currentPost.plan.add("New West Station && We will meet at new west station first");
        //currentPost.plan.add("Metrotown && Check out shopping at Metrotown");
        //currentPost.plan.add("Downtown && Center of Vancouver!!");



        for(int i = 0; i < currentPost.plan.size(); i++)
        {
            // String text plans
//            String[] splitPlan = currentPost.plan.get(i).split("&&");
//            String planMarker = splitPlan[0];
//            String planLabel = splitPlan[1];

            String planMarker = currentPost.plan.get(i);
            String planLabel = currentPost.planDesc.get(i);

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


    private void displayReviewSection()
    {
        reviewsWrapper = (LinearLayout)findViewById(R.id.reviewsWrapper);

        FireBaseAPI.getReviews(new OnDataReceiveCallback(){
            @Override
            public void onReviewReceived(ArrayList<Review> list) {
                if(list.size() > 0) {

                    String username = list.get(0).getUserId();
                    String date = list.get(0).getReviewDate().toString();
                    float rating = list.get(0).getRating();
                    String comment = list.get(0).getComment();

                    LinearLayout bigWrapper = new LinearLayout(getApplicationContext());
                    bigWrapper.setPadding(10, 10, 10, 10);
                    bigWrapper.setOrientation(LinearLayout.VERTICAL);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );


                    LinearLayout userAndRating = createUserAndRating(username, rating);
                    TextView dateView = new TextView(getApplicationContext());
                    dateView.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    ));
                    dateView.setText(date);

                    TextView commentView = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams commentParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    commentParams.setMargins(0, 20, 0, 0);
                    commentView.setLayoutParams(commentParams);
                    commentView.setText(comment);

                    bigWrapper.addView(userAndRating);
                    bigWrapper.addView(dateView);
                    bigWrapper.addView(commentView);

                    reviewsWrapper.addView(bigWrapper);
                }
            }

            @Override
            public void onPostReceived(ArrayList<Post> list) {

            }

        },currentPost.getId());




    }

    public static void refreshReviewsList(){

    }

    private LinearLayout createUserAndRating(String name, float rating)
    {
        LinearLayout lo = new LinearLayout(getApplicationContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        lo.setOrientation(LinearLayout.HORIZONTAL);

        TextView userView = new TextView(getApplicationContext());
        LinearLayout.LayoutParams userlp = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        userlp.weight = 1;
        userView.setLayoutParams(userlp);
        userView.setText(name);
        userView.setTextSize(18.0f);
        userView.setTypeface(null, Typeface.BOLD);

        RatingBar rb = new RatingBar(getApplicationContext(), null, android.R.attr.ratingBarStyleSmall);
        LinearLayout.LayoutParams rblp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        rblp.gravity = Gravity.RIGHT;

        rb.setNumStars(5);
        rb.setStepSize(1);
        rb.setRating(rating);
        rb.setForegroundGravity(Gravity.RIGHT);
        rb.setPadding(0,20,0,0);
        rb.setLayoutParams(rblp);

        lo.addView(userView);
        lo.addView(rb);

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
