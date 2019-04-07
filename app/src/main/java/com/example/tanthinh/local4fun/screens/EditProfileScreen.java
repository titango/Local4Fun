package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Singleton;
import com.example.tanthinh.local4fun.models.User;
import com.example.tanthinh.local4fun.services.FireBaseAPI;
import com.example.tanthinh.local4fun.services.UploadImage;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileScreen extends AppCompatActivity {

    private Button updateButton;
    private TextView email, fullname, mobile, desciption, user_name, user_email;
    private ImageView back;
    private Singleton singleton;
    private CircleImageView imgPath,imgViewImage;
    private final int PICK_IMAGE_REQUEST = 4;
    private Uri filePath;
    private  String imgPath1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_screen);

        updateButton = (Button) findViewById(R.id.btn_update);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singleton.loginUser.setFullname(fullname.getText().toString());
                singleton.loginUser.setPhone(mobile.getText().toString());
                singleton.loginUser.setEmail(user_email.getText().toString());
                singleton.loginUser.setDescription(desciption.getText().toString());
                UploadImage uploadImageService = new UploadImage();
                imgPath1 = uploadImageService.uploadImage(EditProfileScreen.this, filePath);
                singleton.loginUser.setImgUrl(imgPath1);
                FireBaseAPI.updateUser(singleton.loginUser);
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
        imgViewImage = (CircleImageView) findViewById(R.id.profile_image);
        imgPath = (CircleImageView) findViewById(R.id.profile_choose_image);
        imgPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

        singleton = Singleton.initInstance();
        if(singleton.loginUser.getImgUrl() != null && !singleton.loginUser.getImgUrl().equals("")) {

            Picasso.get().invalidate(singleton.loginUser.getImgUrl());
            Picasso.get().load(singleton.loginUser.getImgUrl())
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)
                    .fit()
                    .centerCrop()
                    .into(imgViewImage);
        }
        initSingleton();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                Log.e("D", filePath + "");
                imgViewImage.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
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
