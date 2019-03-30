package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidbuts.multispinnerfilter.MultiSpinner;
import com.androidbuts.multispinnerfilter.MultiSpinnerListener;
import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Singleton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BecomeHostScreen extends AppCompatActivity {

    private ImageView back;
    private Singleton singleton;
    private TextView user_name, user_email;

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
        user_name = (TextView) findViewById(R.id.user_name);
        user_email = (TextView) findViewById(R.id.user_email);

//        final List<String> list = Arrays.asList(getResources().getStringArray(R.array.lang));
//
//        MultiSpinner simpleSpinner = (MultiSpinner) findViewById(R.id.simpleMultiSpinner);
//
//        simpleSpinner.setItems(list, new MultiSpinnerListener() {
//            @Override
//            public void onItemsSelected(boolean[] selected) {
//            }
//        });
//
//        singleton = Singleton.initInstance();
//        initSingleton();
    }

    private void backToProfileScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isEditProfileScreen", true);
        startActivity(intent);
    }

    private void initSingleton() {
        user_name.setText(singleton.loginUser.getFullname());
        user_email.setText(singleton.loginUser.getEmail());
    }
}
