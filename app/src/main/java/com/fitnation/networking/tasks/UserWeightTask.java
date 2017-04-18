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
import com.fitnation.networking.UserLogins;
import com.fitnation.profile.callbacks.GetUserWeightCallback;
import com.fitnation.profile.callbacks.PutUserWeightCallback;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J on 4/9/2017.
 */

public class UserWeightTask extends NetworkTask{

    private List<UserWeight> weightList;
    private Environment env;

    private String TAG = UserWeightTask.class.getSimpleName();

    public UserWeightTask(String authToken, RequestQueue queue){
        super(authToken, queue);
    }

    //Get All User Weights by Logged in User
    public void getUserWeights(final GetUserWeightCallback callback){
        String url = EnvironmentManager.
                getInstance().getCurrentEnvironment().
                getApiUrl()+"user-weights";

        final String authToken = AuthToken.getInstance().getAccessToken();

        JsonArrayRequest jsonRequestWeights =
                new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());
                        weightList = JsonParser.convertJsonStringToList(response.toString(), UserWeight[].class);
                        callback.onSuccess(weightList);

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
                        params.put("Authorization", "Bearer "+authToken);
                        return params;
                    }
                };

        mRequestQueue.add(jsonRequestWeights);
    }

    public void putUserWeight(UserWeight weight, final PutUserWeightCallback callback) {
        //save data to web
        String url = EnvironmentManager.
                getInstance().getCurrentEnvironment().
                getApiUrl()+"user-weights";

        String jString = JsonParser.convertPojoToJsonString(weight);
        JSONArray udjObj;
        try {
            udjObj = new JSONArray(jString);
            Log.i(TAG, "User Weight Successfully POST to Web");
        } catch (org.json.JSONException e) {
            Log.d(TAG, "Failed to convert User Demographic to JSON String");
            udjObj = new JSONArray();
        }

        JsonArrayRequest jsonWeightRequest =
                new JsonArrayRequest(Request.Method.PUT, url, udjObj, new Response.Listener<JSONArray> () {
                    @Override
                    public void onResponse(JSONArray response) {
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
                params.put("Authorization", "Bearer " + mAuthToken);
                return params;
            }
        };

        mRequestQueue.add(jsonWeightRequest);
    }

}
