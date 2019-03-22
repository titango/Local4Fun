package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;

public class EditProfileScreen extends AppCompatActivity {

    private Button updateButton;
    private TextView email, fullname, mobile, desciption;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_screen);

        updateButton = (Button) findViewById(R.id.btn_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToProfileScreen();
            }
        });

        email = (TextView) findViewById(R.id.user_email_text);
        fullname = (TextView) findViewById(R.id.user_email_text);
        mobile = (TextView) findViewById(R.id.user_phone_text);
        desciption = (TextView) findViewById(R.id.user_description_text);

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
