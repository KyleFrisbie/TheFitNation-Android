package com.fitnation.login;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.VolleyErrorMessage;
import com.fitnation.base.BaseActivity;
import com.fitnation.utils.EnvironmentManager;

import java.util.HashMap;
import java.util.Map;

/**
 * contains the business logic for the view
 */
public class ResetLoginPresenter implements ResetLoginContract.Presenter, ResetLoginManagerContract.View {
    private ResetLoginContract.View mView;
    private ResetLoginManagerContract.Manager mManager;

    public ResetLoginPresenter(ResetLoginContract.View view) { mView = view; }

    @Override
    public void onResetPasswordButtonPressed(final String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());
        String endpoint = "api/account/reset_password/init";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + endpoint;

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

    private void responseMessage(String message){
        mView.showProgress(message);
    }

    private void errorResponseMessage(VolleyError error){
        VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(error);
        mView.showAuthError(volleyErrorMessage.GetErrorMessage(mView.getBaseActivity()));
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

    @Override
    public void setPresenter(ResetLoginManagerContract.Manager presenter) {
        mManager = presenter;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return null;
    }

    @Override
    public void successfulResponse() {

    }

    @Override
    public void errorResponse() {

    }
}
