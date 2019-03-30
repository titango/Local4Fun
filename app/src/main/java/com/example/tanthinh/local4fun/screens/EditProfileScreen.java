package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Singleton;
import com.example.tanthinh.local4fun.models.User;

public class EditProfileScreen extends AppCompatActivity {

    private Button updateButton;
    private TextView email, fullname, mobile, desciption, user_name, user_email;
    private ImageView back;
    private Singleton singleton;

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
        fullname = (TextView) findViewById(R.id.user_name_text);
        user_name = (TextView) findViewById(R.id.user_name);
        user_email = (TextView) findViewById(R.id.user_email);
        mobile = (TextView) findViewById(R.id.user_phone_text);
        desciption = (TextView) findViewById(R.id.user_description_text);

        back = (ImageView) findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               backToProfileScreen();
            }
        });
        singleton = Singleton.initInstance();
        initSingleton();
    }

    private void initSingleton() {
        user_name.setText(singleton.loginUser.getFullname());
        user_email.setText(singleton.loginUser.getEmail());
        fullname.setText(singleton.loginUser.getFullname());
        email.setText(singleton.loginUser.getEmail());
        desciption.setText(singleton.loginUser.getDescription());
        mobile.setText(singleton.loginUser.getPhone());
    }

    private void backToProfileScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isEditProfileScreen", true);
        startActivity(intent);
    }
}
