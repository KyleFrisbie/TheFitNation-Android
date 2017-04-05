package com.fitnation.profile;

import android.app.Fragment;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.fitnation.model.User;
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
    String accessToken = "3578ace0-6de2-459f-8047-9b888ed1f947";
    UserDemographic userdemo;
    User user;
    private UserDemographicSingleton queue;

    @Override
    public void saveData(UserDemographic pUserDemo) {
        final UserDemographic userDemo = pUserDemo;
        UserDataManager userDataManager = new UserDataManager();
        userDataManager.SaveProfileData(userDemo);

        //save data to web
        queue = UserDemographicSingleton.getInstance(mView.getBaseActivity());
        url = "https://the-fit-nation-dev.herokuapp.com/api/user-demographics/byLoggedInUser";


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

        Log.d("JSON REQUEST", jsonRequest.toString());
        queue.addToRequestQueue(jsonRequest);
    }



    public void getUserDemographic(final ProfileFragment fragment){

        queue = UserDemographicSingleton.getInstance(mView.getBaseActivity());
        String id = "3154";
        url = "https://the-fit-nation-dev.herokuapp.com/api/user-demographics/"+id;



        //USERDEMOGRAPHIC
        JsonObjectRequest jsonRequestUserDemo =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("GET", response.toString());
                userdemo = JsonParser.convertJsonStringToPojo(response.toString(), UserDemographic.class);
                fragment.setDemographic(userdemo);
                fragment.loadDemographics();
                getUserInfo(userdemo.getUserLogin(), fragment);

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

        queue.addToRequestQueue(jsonRequestUserDemo);
    }

    public void getUserInfo(String loginId, final ProfileFragment fragment){
        //USER
        queue = UserDemographicSingleton.getInstance(mView.getBaseActivity());
        url = "https://the-fit-nation-dev.herokuapp.com/api/users/"+loginId;
        JsonObjectRequest jsonRequestUser =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("GET", response.toString());
                        user = JsonParser.convertJsonStringToPojo(response.toString(), User.class);
                        fragment.setUser(user);
                        fragment.loadUser();
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

        queue.addToRequestQueue(jsonRequestUser);
    }
}
