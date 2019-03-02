package com.example.tanthinh.local4fun.utilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.Window;

import com.example.tanthinh.local4fun.R;

public class Config {

    //Static variables
    public static final String APP_NAME = "Local4Fun";

    //Change status bar color
    public static void changeStatusBarColor(Activity context, int color)
    {
        //Change status bar color
        Window w = context.getWindow();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            w.setStatusBarColor(context.getColor(R.color.colorPrimary));
        }else
        {
            w.setStatusBarColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }
}
