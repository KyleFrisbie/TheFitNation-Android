package com.fitnation.profile;

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
    String accessToken = "0252a67c-49fe-4237-a4d7-adffe6d5e66b";
    UserDemographic userdemo;
    User user;
    private UserDemographicSingleton queue;

    @Override
    public void saveProfileData(ProfileFragment profile) {
        ProfileDataManager userDataManager = new ProfileDataManager();
        userDataManager.SaveUserDemographicData(profile.userdemo);
        userDataManager.SaveUserData(profile.user);
        final UserDemographic userDemo = profile.userdemo;

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

    public void saveUserData(User user){
        ProfileDataManager pdm = new ProfileDataManager();
        pdm.SaveUserData(user);
    }

    public void getProfileData(final ProfileFragment fragment){

        queue = UserDemographicSingleton.getInstance(mView.getBaseActivity());
        String id = "3154";  //User Demographic ID
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
                fragment.setUser(new User());
                fragment.setDemographic(new UserDemographic());
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
