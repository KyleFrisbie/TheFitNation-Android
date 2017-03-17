package com.fitnation.login;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
    public void onResetPasswordButtonPressed(final String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());
        System.out.println("in the button pressed");
        String url = "http://the-fit-nation-dev.herokuapp.com/api/account/reset_password/init";

        StringRequest resetPasswordWithEmailRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                System.out.println("succesful password rest\n\n" + response);
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("failed " + error.getMessage());
                }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return email.getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "text/plain");
                return params;
            }
        };

        requestQueue.add(resetPasswordWithEmailRequest);
        requestQueue.start();
    }
}
