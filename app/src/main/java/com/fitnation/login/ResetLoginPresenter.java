package com.fitnation.login;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.VolleyErrorMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * contains the business logic for the view
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
        // TODO: change to the url class when it is implemented
        String url = "http://the-fit-nation-dev.herokuapp.com/api/account/reset_password/init";

        StringRequest resetPasswordWithEmailRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
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

    private void responseMessage(String message){
        mView.showProgress(message);
    }

    private void errorResponseMessage(VolleyError error){
        VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(error);
        mView.showAuthError(volleyErrorMessage.GetErrorMessage(mView.getBaseActivity()));
    }
}
