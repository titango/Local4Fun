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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChangePasswordScreen extends AppCompatActivity {

    private Button changePasswordButton;
    private TextView currentPassword, newPassword, confirmPassword, user_name, user_email;
    private ImageView back;
    private Singleton singleton;
    private CircleImageView imgViewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);

        changePasswordButton = (Button) findViewById(R.id.btn_change_password);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword();
               backToProfileScreen();
            }
        });

        currentPassword = (TextView) findViewById(R.id.current_password_text);
        newPassword = (TextView) findViewById(R.id.new_password_text);
        confirmPassword = (TextView) findViewById(R.id.confirm_password_text);
        user_name = (TextView) findViewById(R.id.user_name);
        user_email = (TextView) findViewById(R.id.user_email);

        back = (ImageView) findViewById(R.id.img_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToProfileScreen();
            }
        });
        singleton = Singleton.initInstance();
        imgViewImage = (CircleImageView) findViewById(R.id.profile_image);
        if(singleton.loginUser.getImgUrl() != "") {
            Picasso.get().load(singleton.loginUser.getImgUrl())
                    .fit()
                    .centerCrop()
                    .into(imgViewImage);
        }
        initSingleton();
    }

    private void updatePassword() {
        boolean checkCurrentPassword = singleton.loginUser.comparePassword(currentPassword.getText().toString());
        if(checkCurrentPassword){
            String newPass = newPassword.getText().toString();
            String comfirmNewPass = confirmPassword.getText().toString();
            if(newPass.equals(comfirmNewPass)) {
                singleton.loginUser.setPassword(newPass);
            }
        }
    }

    private void initSingleton() {
        user_name.setText(singleton.loginUser.getFullname());
        user_email.setText(singleton.loginUser.getEmail());
    }

    private void backToProfileScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("isEditProfileScreen", true);
        startActivity(intent);
    }

}
