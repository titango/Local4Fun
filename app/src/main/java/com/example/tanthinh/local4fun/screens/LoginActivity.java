package com.example.tanthinh.local4fun.screens;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanthinh.local4fun.BuildConfig;
import com.example.tanthinh.local4fun.R;
import com.example.tanthinh.local4fun.models.Singleton;
import com.example.tanthinh.local4fun.models.User;
import com.example.tanthinh.local4fun.services.FireBaseAPI;
import com.example.tanthinh.local4fun.utilities.accountRegister;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    public String TAG = "MainActivity";
    EditText loginEmail, loginPwd;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private CallbackManager mCallbackManager;
    private Singleton singleton;
    public static  User loginUser;

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

        //google login
        ImageView googleImg = (ImageView) findViewById(R.id.googleImg);
        googleImg.setOnClickListener(this);
        facebookLogin();

        //Setbg image alpha 0.3
        View rootView = findViewById(R.id.rootLogin);
        Drawable bgDrawable = rootView.getBackground();
//        bgDrawable.setAlpha(80);

//        Config.changeStatusBarColor(this, R.color.colorPrimary);
        singleton = Singleton.initInstance();
    }

    private void moveToMainActivity(Singleton singleton, FirebaseUser user){
        FireBaseAPI.getUserByEmail(singleton, user.getEmail());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void signIn(String email, String password) {

        if(email.isEmpty() || email.equals(null))
        {
            email = "!@#!@#!@#";
        }
        if(password.isEmpty() || password.equals(null))
        {
            password = "!@#!@#!@#";
        }
        Log.d(TAG, "signIn:" + email);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //Toast.makeText(LoginActivity.this, "login success.",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            moveToMainActivity(singleton, user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed. Please try again",Toast.LENGTH_SHORT).show();
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


    //google login begin
    public void googleLogin(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();


    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);

        FirebaseUser currentUser = mAuth.getCurrentUser();

    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

                User newuser = new User(account.getDisplayName(),
                        account.getEmail(),"", "", account.getDisplayName());
                FireBaseAPI.insertUser(newuser);

            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

        if(FacebookSdk.isFacebookRequestCode(requestCode)){
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            moveToMainActivity(singleton, user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
    //google login end


    //facebook login begin
    private void facebookLogin(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true);
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
        }
        AppEventsLogger.activateApp(this);
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton facebookImg = findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();
        facebookImg.setReadPermissions("email", "public_profile");
        facebookImg.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
//                FirebaseUser user = mAuth.getCurrentUser();

//                moveToMainActivity(singleton, user);
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);

            }
        });
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Pass the activity result back to the Facebook SDK
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = mAuth.getCurrentUser();

                            User newuser = new User(user.getDisplayName(),
                                    user.getEmail(),"", "", user.getDisplayName());
                            FireBaseAPI.insertUser(newuser);

                            moveToMainActivity(singleton, user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    //facebook login end


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
                break;
            case R.id.googleImg:
                googleLogin();
                signIn();
                break;
            default:
                break;
        }
    }
}