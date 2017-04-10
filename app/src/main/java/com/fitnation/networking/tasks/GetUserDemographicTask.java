package com.fitnation.networking.tasks;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.JsonParser;
import com.fitnation.profile.ProfileFragment;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeremy on 4/9/2017.
 */

public class GetUserDemographicTask {

    static EnvironmentManager em;
    static Environment env;
    static String url;
    static String accessToken;
    static RequestQueue queue;
    UserDemographic userdemo;

    public static void putUserDemographicData(UserDemographic userdemo, Context context){
        //save data to web
        queue = Volley.newRequestQueue(context);
        env = EnvironmentManager.getInstance().getCurrentEnvironment();
        url = env.getApiUrl()+"user-demographics/byLoggedInUser";
        //final String authToken = AuthToken.getInstance().getAccessToken();
        //TODO CHANGE THIS TO PREVIOUS COMMENTED OUT LINE
        final String authToken = AuthToken.getHardToken();

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
                params.put("Authorization", "Bearer "+authToken);
                return params;
            }
        };

        Log.d("JSON REQUEST", jsonRequest.toString());
        queue.add(jsonRequest);
    }

    public void getUserDemographic(final ProfileFragment fragment, String userDemographicId){

        queue = Volley.newRequestQueue(fragment.getBaseActivity());
        userDemographicId = "3154";  //User Demographic ID

        //USERDEMOGRAPHIC
        JsonObjectRequest jsonRequestUserDemo =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("GET", response.toString());
                        userdemo = JsonParser.convertJsonStringToPojo(response.toString(), UserDemographic.class);
                        fragment.setDemographic(userdemo);
                        fragment.loadDemographics();
                        GetUserTask.getUser(userdemo.getUserLogin(), fragment);
                        GetUserWeightTask.getUserWeight(fragment);
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

        queue.add(jsonRequestUserDemo);
    }

}
