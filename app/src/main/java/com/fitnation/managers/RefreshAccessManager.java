package com.fitnation.managers;

import android.content.Context;

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

public class RefreshAccessManager {
    private String refreshToken;
    private boolean isRefreshSuccessful;

    public boolean refresh(Context context){
        boolean tokenExists = false;

        if(AuthToken.getInstance().getRefreshToken() != null){
            refreshToken = AuthToken.getInstance().getRefreshToken();
            tokenExists = true;
        }

        if(tokenExists) {
            RequestQueue requestQueue = Volley.newRequestQueue(context);

            String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + "/oauth/token";

            JsonObjectRequest jsonObjectPost = new JsonObjectRequest(Request.Method.POST,
                    url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    isRefreshSuccessful = true;
                    NetworkUtils.getInstance().storeTokens(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    isRefreshSuccessful = true;
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

                    return NetworkUtils.getInstance().convertToUrlEncodedPostBody(params).getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return ("application/x-www-form-urlencoded");
                }
            };


            requestQueue.add(jsonObjectPost);
            requestQueue.start();
        }

        return isRefreshSuccessful;
    }
}
