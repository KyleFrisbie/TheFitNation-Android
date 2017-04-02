package com.fitnation.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.utils.EnvironmentManager;

import java.util.HashMap;
import java.util.Map;

// TODO: get interface working for connecting to presenter
/**
 * handles the email reset password request
 */

public class ResetLoginManager implements ResetLoginManagerContract.Manager {
    private ResetLoginManagerContract.View mView;
    public ResetLoginManager(ResetLoginManagerContract.View view) {
        mView = view;
    }

    public void resetPasswordRequest(final String email){
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());
        String endpoint = "api/account/reset_password/init";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + endpoint;;

        StringRequest resetPasswordWithEmailRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                // TODO: convert to a successResponse return
                responseMessage(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null){
                    errorResponseMessage(error);
                }
            }
        }){
            @Override
            public byte[] getBody() throws AuthFailureError {
                return email.getBytes();
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "text/plain");
                return params;
            }
        };

        requestQueue.add(resetPasswordWithEmailRequest);
        requestQueue.start();
    }

    private void responseMessage(String response) {
    }

    private void errorResponseMessage(VolleyError error){

    }

    @Override
    public void onViewReady() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
