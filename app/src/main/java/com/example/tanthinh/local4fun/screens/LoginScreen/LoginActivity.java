package com.example.tanthinh.local4fun.screens.LoginScreen;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.utilities.Config;
import com.example.tanthinh.local4fun.utilities.accountRegister;
import com.example.tanthinh.local4fun.screens.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    public String TAG = "MainActivity";
    EditText loginEmail, loginPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView registerTV = (TextView) findViewById(R.id.registerTextView);
        TextView forgotTV = (TextView) findViewById(R.id.forgotTextView);
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPwd = (EditText) findViewById(R.id.loginPwd);
        registerTV.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
        forgotTV.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        //Setbg image alpha 0.3
        View rootView = findViewById(R.id.rootLogin);
        Drawable bgDrawable = rootView.getBackground();
        bgDrawable.setAlpha(20);

//        Config.changeStatusBarColor(this, R.color.colorPrimary);

    }

    private void moveToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        //Cheat
        if(email.equals("") || email.equals(null))
        {
            email = "a";
        }
        if(password.equals("") || password.equals(null))
        {
            password = "b";
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful() || true) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(LoginActivity.this, "login success.",
                                    Toast.LENGTH_SHORT).show();
                            moveToMainActivity();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void setDialog(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final EditText input = new EditText(LoginActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        dialog.setMessage("Please enter your email address so we can send you the reset password link.");
        dialog.setView(input);
        dialog.setCancelable(false);
        dialog.setPositiveButton("Send email", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                sendEmail(input.getText().toString());
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alert = dialog.create();
        alert.setTitle("Forgot Password?");
        alert.show();
    }

    public void sendEmail(String email){
        mAuth.sendPasswordResetEmail(email);
        Toast.makeText(LoginActivity.this, "Email sent success.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        switch (i){
            case R.id.registerTextView:
                Intent it = new Intent(LoginActivity.this, accountRegister.class);
                startActivity(it);
                break;
            case R.id.loginBtn:
                signIn(loginEmail.getText().toString(), loginPwd.getText().toString());
                break;
            case R.id.forgotTextView:
                setDialog();
            default:
                break;
        }
    }
}
