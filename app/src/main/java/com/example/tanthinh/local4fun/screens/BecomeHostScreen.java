package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tanthinh.local4fun.R;

public class BecomeHostScreen extends AppCompatActivity {

    private ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_host_screen);

        back = (ImageView) findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToProfileScreen();;
            }
        });
    }

    private void backToProfileScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isEditProfileScreen", true);
        startActivity(intent);
    }
}
