package com.fitnation.networking.tasks;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.networking.AuthToken;
import com.fitnation.utils.EnvironmentManager;
import com.fitnation.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RefreshAuthTokenTask {
    private TaskCallback.Factory mFactory;
    private String refreshToken;
    private String TAG = "Refresh Auth Token: ";

    /**
     * Constructor
     * @param factory The calling factory for 401 error response
     */
    public RefreshAuthTokenTask(TaskCallback.Factory factory){
        this.mFactory = factory;
    }

    /**
     * Request to the server to refresh the users access token with the refresh token if there is one
     * @param context The base calling activity
     */
    public void refresh(Context context){
        boolean tokenExists = false;

        if(AuthToken.getInstance().getRefreshToken() != null){
            refreshToken = AuthToken.getInstance().getRefreshToken();
            tokenExists = true;
        }

        if(tokenExists) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + "oauth/token";

            JsonObjectRequest jsonObjectPost = new JsonObjectRequest(Request.Method.POST,
                    url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    mFactory.didRequestWork(true);
                    NetworkUtils.getInstance().storeTokens(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mFactory.didRequestWork(false);
                    Log.d(TAG, "onErrorResponse: " + AuthToken.getInstance().getRefreshToken());
                }
            }) {
                @Override
                public byte[] getBody() {
                    Map<String, String> params = new HashMap<>();
                    params.put("refresh_token", refreshToken);
                    params.put("grant_type", "refresh_token");
                    params.put("scope", "read+write");
                    params.put("client_secret", "my-secret-token-to-change-in-production");
                    params.put("client_id", "TheFitNationapp");
                    params.put("submit", "login");

                    Log.d(TAG, "getBody: " + NetworkUtils.getInstance().convertToUrlEncodedPostBody(params));
                    return NetworkUtils.getInstance().convertToUrlEncodedPostBody(params).getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return ("application/x-www-form-urlencoded");
                }
            };

            jsonObjectPost.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));
            requestQueue.add(jsonObjectPost);
            requestQueue.start();
        }else{
            mFactory.didRequestWork(false);
        }
    }
}
