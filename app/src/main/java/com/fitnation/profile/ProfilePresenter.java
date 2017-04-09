package com.fitnation.profile;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import com.fitnation.model.User;
import com.fitnation.model.UserWeight;
import com.fitnation.networking.JsonParser;
import com.fitnation.model.UserDemographic;
import com.fitnation.networking.VolleyQueueSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
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
    String accessToken = "35d91ed5-d493-4e4f-aa3f-9cd59ff0e315";
    UserDemographic userdemo;
    User user;
    UserWeight weight;
    List<UserWeight> weightList;
    private VolleyQueueSingleton queue;

    @Override
    public void saveProfileData(ProfileFragment profile) {
        //LOCAL DATE STORE
        ProfileDataManager userDataManager = new ProfileDataManager();
        userdemo = profile.userdemo;
        user = profile.user;
        weight = profile.userWeight;
        userDataManager.SaveUserDemographicData(userdemo);
        userDataManager.SaveUserData(user);
        userDataManager.SaveWeightData(weight);

        saveUserWeightData(weight);
        saveUserDemographicData(userdemo);

    }

    public void saveUserDemographicData(UserDemographic userdemo){
        //save data to web
        queue = VolleyQueueSingleton.getInstance(mView.getBaseActivity());
        url = "https://the-fit-nation-dev.herokuapp.com/api/user-demographics/byLoggedInUser";

        String jString = JsonParser.convertPojoToJsonString(userdemo);
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

    public void saveUserWeightData(UserWeight weight){
        //save data to web
        queue = VolleyQueueSingleton.getInstance(mView.getBaseActivity());
        url = "https://the-fit-nation-dev.herokuapp.com/api/user-weights/byLoggedInUser";

        String jString = JsonParser.convertPojoToJsonString(weight);
        JSONObject udjObj;
        try {
            udjObj = new JSONObject(jString);
            Log.i("JSON", jString);
        } catch (org.json.JSONException e) {
            Log.d("JSON", "Failed to convert User Demographic to JSON String");
            udjObj = new JSONObject();
        }

        JsonObjectRequest jsonWeightRequest = new JsonObjectRequest(Request.Method.POST, url, udjObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("POST", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("POST", error.toString());
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

        queue.addToRequestQueue(jsonWeightRequest);
    }

    public void saveUserData(User user){
        ProfileDataManager pdm = new ProfileDataManager();
        pdm.SaveUserData(user);
    }

    public void getProfileData(final ProfileFragment fragment){

        queue = VolleyQueueSingleton.getInstance(mView.getBaseActivity());
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
                getUserData(userdemo.getUserLogin(), fragment);
                getWeightData(fragment);
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

    public void getUserData(String loginId, final ProfileFragment fragment){
        //USER
        queue = VolleyQueueSingleton.getInstance(mView.getBaseActivity());
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

    public void getWeightData(final ProfileFragment fragment){
        //WEIGHTS
        queue = VolleyQueueSingleton.getInstance(mView.getBaseActivity());
        url = "https://the-fit-nation-dev.herokuapp.com/api/user-weights/byLoggedInUser";


        JsonArrayRequest jsonRequestWeights =
                new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("GET", response.toString());
                        weightList = JsonParser.convertJsonStringToList(response.toString(), UserWeight[].class);
                        weight = weightList.get(weightList.size()-1);
                        fragment.setUserWeight(weight);
                        fragment.loadUserWeight();
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

        queue.addToRequestQueue(jsonRequestWeights);
    }
}
