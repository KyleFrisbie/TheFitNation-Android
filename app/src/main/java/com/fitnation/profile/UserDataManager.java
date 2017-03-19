package com.fitnation.profile;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.UserDemographic;
import com.fitnation.networking.JsonParser;
import com.fitnation.base.FitNationApplication;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.*;

import org.json.JSONObject;


/**
 * Created by Jeremy on 2/26/2017.
 */

public class UserDataManager extends DataManager {


    public void SaveProfileData(final UserDemographic userDemographic){

        //save data to local data store
        saveData(userDemographic, new DataResult() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });

        //save data to webservice

        Context ctx = FitNationApplication.context;
        UserDemographicSingleton queue = UserDemographicSingleton.getInstance(ctx);
        String url = "https://the-fit-nation-dev.herokuapp.com/api/user-demographics/byLoggedInUser";


        //  GET a User Demographic
        /*
        StringRequest stringRequest = new UserDemoStringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("GET", error.toString());
            }
        });
        */

        StringRequest stringRequest = new UserDemoStringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("GET", error.toString());
            }
        });

        Log.d("", stringRequest.toString());
        queue.addToRequestQueue(stringRequest);
    }

    private String getJson(UserDemographic userDemo){
        return JsonParser.convertPojoToJsonString(userDemo);
    }

}
