package com.fitnation.networking.tasks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.FactoryCallback;
import com.fitnation.Factory.VolleyErrorMessage;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.navigation.NavigationActivity;
import com.fitnation.utils.EnvironmentManager;
import com.fitnation.utils.NetworkUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the login request
 */

public class GetAuthTokenTask implements FactoryCallback.FactoryReturn{
    private TaskCallback.AuthPresenter mPresenter;
    private BaseActivity mActivity;

    /**
     * Constructor
     * @param activity The base calling activity
     * @param presenter The interface to be used
     */
    public GetAuthTokenTask(BaseActivity activity, TaskCallback.AuthPresenter presenter) {
        this.mActivity = activity;
        mPresenter = presenter;
    }

    /**
     * Request to the server to obtain and auth token by username and password
     * @param userName The username input
     * @param password The password input
     */
    public void requestToken(final String userName, final String password){
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        String endpoint = "oauth/token";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + endpoint;

        ProgressDialog progressDialog = new ProgressDialog(mActivity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(mActivity.getString(R.string.progress_message));
        progressDialog.setIndeterminate(true);
        mPresenter.showProgress(progressDialog);

        JsonObjectRequest jsonObjectPost = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                NetworkUtils.getInstance().storeTokens(response);

                mPresenter.stopProgress();
                mPresenter.loginSuccess();
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mPresenter.stopProgress();
                errorResponseMessage(error);
            }
        }){
            @Override
            public byte[] getBody() {
                Map<String,String> params = new HashMap<>();
                params.put("username", userName);
                params.put("password", password);
                params.put("grant_type", "password");
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

        jsonObjectPost.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));
        requestQueue.add(jsonObjectPost);
        requestQueue.start();
    }

    /**
     * Error response to be generated from volleyErrorFactory
     * @param error Volleys error object
     */
    private void errorResponseMessage(VolleyError error) {
        VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(mActivity, this);
        volleyErrorMessage.getErrorMessage(error);
    }

    /*--------------------------------------FactoryCallback--------------------------------------*/

    @Override
    public void showSuccessDialog(AlertDialog.Builder alertDialog) {
        mPresenter.showSuccess(alertDialog);
    }

    @Override
    public void showErrorDialog(AlertDialog.Builder alertDialog) {
        mPresenter.showAuthError(alertDialog);
    }
}
