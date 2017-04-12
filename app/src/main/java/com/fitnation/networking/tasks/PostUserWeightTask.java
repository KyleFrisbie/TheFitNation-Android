package com.fitnation.networking.tasks;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.model.UserWeight;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.JsonParser;
import com.fitnation.networking.VolleyQueueSingleton;
import com.fitnation.profile.ProfileFragment;
import com.fitnation.profile.ProfilePresenter;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J on 4/9/2017.
 */

public class PostUserWeightTask {

    static List<UserWeight> weightList;
    static UserWeight weight;
    static RequestQueue queue;
    static Environment env;
    static String url;


    public static void postUserWeight(UserWeight weight, ProfilePresenter presenter) {
        //save data to web
        queue = Volley.newRequestQueue(presenter.getBaseActivity());
        env = EnvironmentManager.getInstance().getCurrentEnvironment();
        String userDemographicId = presenter.userdemo.getId().toString();
        url = env.getApiUrl() + "user-weights/byLoggedInUser/" + userDemographicId;


        //final String authToken = AuthToken.getInstance().getAccessToken();
        //TODO CHANGE THIS TO PREVIOUS COMMENTED OUT LINE
        final String authToken = AuthToken.getHardToken();

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
                params.put("Authorization", "Bearer " + authToken);
                return params;
            }
        };

        queue.add(jsonWeightRequest);
    }
}