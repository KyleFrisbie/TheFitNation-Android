package com.fitnation.login;

import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.VolleyErrorMessage;
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

public class LoginManager {
    private ManagerContract.Presenter mPresenter;
    private BaseActivity mActivity;

    public LoginManager(BaseActivity activity, ManagerContract.Presenter presenter) {
        this.mActivity = activity;
        mPresenter = presenter;
    }

    public void requestToken(final String userName, final String password){
        RequestQueue requestQueue = Volley.newRequestQueue(mActivity);
        String endpoint = "oauth/token";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + endpoint;

        JsonObjectRequest jsonObjectPost = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                NetworkUtils.getInstance().storeTokens(response);

                Intent mainActivityIntent = new Intent(mActivity, NavigationActivity.class);
                mainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mActivity.startActivity(mainActivityIntent);

            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(error.networkResponse != null){
                    errorResponseMessage(error);
                }
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

        requestQueue.add(jsonObjectPost);
        requestQueue.start();
    }

    private void errorResponseMessage(VolleyError error) {
        VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(error);
        mPresenter.showAuthError(volleyErrorMessage.getErrorMessage(mActivity));
    }
}
