package com.example.tanthinh.local4fun.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tanthinh.local4fun.intefaces.FireBaseResponse;
import com.example.tanthinh.local4fun.intefaces.GoogleMapResponse;

import org.json.JSONArray;

public class GoogleMapAPI {

    private GoogleMapResponse apiResponse;
    private Context context;

    public GoogleMapAPI(Activity context)
    {
        this.context = context;
        apiResponse = (GoogleMapResponse)context;
    }

    // Implement different URL call
    public void loginAPI(String url)
    {
        Log.w("doInBackground","doInBackground");

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest requestObject = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("Rest api",response.toString());
                        apiResponse.onRequestMapFinished();
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest error: ", error.toString());
            }
        });

        requestQueue.add(requestObject);
    }
}
