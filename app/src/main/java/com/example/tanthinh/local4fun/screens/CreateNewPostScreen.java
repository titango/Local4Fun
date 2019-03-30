package com.example.tanthinh.local4fun.screens;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tanthinh.local4fun.R;

public class CreateNewPostScreen extends AppCompatActivity {
    Button btnCreatePost;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post_screen);

        btnCreatePost = (Button)findViewById(R.id.btnCreatePost);
        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog();
            }
        });

    }

    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("New Post was successfully created");
        dialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                    }
                });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        //alertDialog.getWindow().getAttributes();

        TextView textView = (TextView) alertDialog.findViewById(android.R.id.message);
        textView.setTextSize(18);
        textView.setTextColor(Color.parseColor("#3F5C73"));
        Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
        textView.setTypeface(boldTypeface);
        Button mButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        mButton.setTextColor(Color.WHITE);
        mButton.setBackgroundColor(Color.parseColor("#6895BB"));

   }

}
