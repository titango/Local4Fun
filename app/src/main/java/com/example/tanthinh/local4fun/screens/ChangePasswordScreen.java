package com.example.tanthinh.local4fun.screens;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tanthinh.local4fun.R;
public class ChangePasswordScreen extends AppCompatActivity {

    private Button changePasswordButton;
    private TextView currentPassword, newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_screen);

        changePasswordButton = (Button) findViewById(R.id.btn_change_password);
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        currentPassword = (TextView) findViewById(R.id.current_password_text);
        newPassword = (TextView) findViewById(R.id.new_password_text);
        confirmPassword = (TextView) findViewById(R.id.confirm_password_text);
    }
}
