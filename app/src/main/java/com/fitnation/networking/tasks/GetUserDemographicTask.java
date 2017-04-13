package com.fitnation.networking.tasks;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fitnation.networking.UserLogins;
import com.fitnation.profile.ProfileData;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.networking.JsonParser;
import com.fitnation.profile.callbacks.GetUserDemographicsCallback;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeremy on 4/9/2017.
 */

public class GetUserDemographicTask extends NetworkTask{

    private RequestQueue queue;

    public GetUserDemographicTask(String authToken, RequestQueue queue) {
        super(authToken, queue);
    }

    public void getUserDemographic(final GetUserDemographicsCallback callback){
        String resourceRoute = "user-demographics/";
        String userDemographicId = UserLogins.getInstance().getUserDemographicId();
        String url = EnvironmentManager.getInstance().
                getCurrentEnvironment().getApiUrl() + resourceRoute + userDemographicId;

        //USERDEMOGRAPHIC
        JsonObjectRequest jsonRequestUserDemo =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("GET", response.toString());
                        UserDemographic userdemo = JsonParser.convertJsonStringToPojo(response.toString(), UserDemographic.class);
                        callback.onSuccess(userdemo);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(error.toString());
                        Log.d("GET", error.toString());
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Accept", "application/json");
                        params.put("Authorization", "Bearer "+mAuthToken);
                        return params;
                    }
                };

        mRequestQueue.add(jsonRequestUserDemo);
    }



}
