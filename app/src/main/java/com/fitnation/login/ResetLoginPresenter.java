package com.fitnation.login;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erik on 2/18/2017.
 */

public class ResetLoginPresenter implements ResetLoginContract.Presenter {
    private ResetLoginContract.View mView;

    public ResetLoginPresenter(ResetLoginContract.View view) { mView = view; }

    @Override
    public void onViewReady() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onResetPasswordButtonPressed(String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());
        System.out.println("in the button pressed");
        String url = "https://the-fit-nation-dev.herokuapp.com/api/account/reset_password/init";
        Map<String, String> map = new HashMap<>();
        map.put("email", email);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(map), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("succesful password rest\n\n" + response);
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("failed " + error.getMessage());
                }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "text/plain");
                params.put("Authorization", "Bearer f44dbf22-0c2b-4fe2-abf1-fb76c6b9f599");
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
        requestQueue.start();
    }
}
