package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.tanthinh.local4fun.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BookingDetailsScreen extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mMap;
    Marker marker;
    String location;
    LatLng ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details_screen);

        Intent intent = getIntent();
        ll = intent.getParcelableExtra("latLon");
        location = (String) intent.getSerializableExtra("desc");

        SupportMapFragment mapFrag = (SupportMapFragment)
                getSupportFragmentManager().
                        findFragmentById(R.id.map);
        mapFrag.getMapAsync(BookingDetailsScreen.this);



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
