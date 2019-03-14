package com.example.tanthinh.local4fun.utilities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tanthinh.local4fun.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class accountRegister extends AppCompatActivity {
    public String TAG = "accountRegister";
    EditText emailTV, pwdTV, cofPwdTV;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_register);

        emailTV = (EditText) findViewById(R.id.registerEmail);
        pwdTV = (EditText) findViewById(R.id.registerPwd);
        cofPwdTV = (EditText) findViewById(R.id.registerCofPwd);
        mAuth = FirebaseAuth.getInstance();
    }
    private boolean validateForm() {
        boolean valid = true;

        String email = emailTV.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailTV.setError("Required.");
            valid = false;
        } else {
            emailTV.setError(null);
        }
        String password = pwdTV.getText().toString();
        if (TextUtils.isEmpty(password)) {
            pwdTV.setError("Required.");
            valid = false;
        } else {
            pwdTV.setError(null);
        }
        if (password.length() > 6) {
            pwdTV.setError(null);
        } else {
            pwdTV.setError("At least 6 characters.");
            valid = false;
        }
        String cofpassword = cofPwdTV.getText().toString();
        if (password.equals(cofpassword)) {
            cofPwdTV.setError(null);
        } else {
            cofPwdTV.setError("Password not same.");
            valid = false;
        }


        return valid;
    }
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(accountRegister.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
    public void register(View view){
        createAccount(emailTV.getText().toString(), pwdTV.getText().toString());

    }
}
