package com.fitnation.networking;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RefreshAccessToken {
    private String refreshToken;
    private boolean isRefreshSuccessful;

    public boolean refresh(Context context){
        if(AuthToken.getInstance().getRefreshToken() != null){
            refreshToken = AuthToken.getInstance().getRefreshToken();
        }

        // TODO:find out how to get context
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://the-fit-nation-dev.herokuapp.com/oauth/token";

        JsonObjectRequest jsonObjectPost= new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response){
                isRefreshSuccessful = true;
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                isRefreshSuccessful = false;
            }
        }){
            @Override
            public byte[] getBody() {
                Map<String,String> params = new HashMap<>();
                params.put("refresh_token", refreshToken);
                params.put("grant_type", "refresh_token");
                params.put("scope", "read+write");
                params.put("client_secret", "my-secret-token-to-change-in-production");
                params.put("client_id", "TheFitNationapp");
                params.put("submit", "login");
                String bodyString = convertToUrlEncodedPostBody(params);
                return bodyString.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return ("application/x-www-form-urlencoded");
            }
        };


        requestQueue.add(jsonObjectPost);
        requestQueue.start();

        return isRefreshSuccessful;
    }

    private String convertToUrlEncodedPostBody(Map<String, String> params){
        StringBuilder sbPost = new StringBuilder();
        if(params != null) {
            int count = 0;
            for (String key : params.keySet()) {
                if (params.get(key) != null) {
                    if(count != 0) {
                        sbPost.append("&");
                    }
                    sbPost.append(key);
                    sbPost.append("=");
                    sbPost.append(params.get(key));
                    count++;
                }
            }
        }
        return sbPost.toString();
    }

}
