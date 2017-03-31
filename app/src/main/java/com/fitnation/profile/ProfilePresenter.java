package com.fitnation.profile;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.fitnation.networking.JsonParser;
import com.fitnation.model.UserDemographic;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View mView;

    public ProfilePresenter (ProfileContract.View view) {
        mView = view;
    }

    @Override
    public void onViewReady() {}

    @Override
    public void start() {}

    @Override
    public void stop() {}

    String url;
    String accessToken = "65b3a0fa-c5c9-411d-b91c-50ebc04d6582";

    @Override
    public void saveData(UserDemographic pUserDemo) {
        final UserDemographic userDemo = pUserDemo;
        UserDataManager userDataManager = new UserDataManager();
        userDataManager.SaveProfileData(userDemo);

        //save data to webservice

        UserDemographicSingleton queue = UserDemographicSingleton.getInstance(mView.getBaseActivity());
        url = "https://the-fit-nation-dev.herokuapp.com/api/user-demographics/byLoggedInUser";


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

        String jString = JsonParser.convertPojoToJsonString(userDemo);
        JSONObject udjObj;
        try {
            udjObj = new JSONObject(jString);
            Log.i("JSON", jString);
        } catch (org.json.JSONException e) {
            Log.d("JSON", "Failed to convert User Demographic to JSON String");
            udjObj = new JSONObject();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.PUT, url, udjObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("PUT", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PUT", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer "+accessToken);
                return params;
            }
        };
        /*
        StringRequest stringRequest = new UserDemoStringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("PUT", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("PUT", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = JsonParser.convertPojoToJsonMap(userDemo);
                Log.d("PUT", params.toString());
                return params;
            }
        }; */


        Log.d("JSON REQUEST", jsonRequest.toString());
        queue.addToRequestQueue(jsonRequest);
    }
    public UserDemographic loadData(){

        UserDemographicSingleton queue = UserDemographicSingleton.getInstance(mView.getBaseActivity());
        String id = "";
        url = "https://the-fit-nation-dev.herokuapp.com/api/user-demographics/"+id;

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("GET", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("GET", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer "+accessToken);
                return params;
            }
        };

        queue.addToRequestQueue(jsonRequest);
        return null;
    }


}
