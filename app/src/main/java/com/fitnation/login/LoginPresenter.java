package com.fitnation.login;

import android.content.Intent;
import android.os.SystemClock;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.navigation.NavigationActivity;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static com.fitnation.login.LoginBaseActivity.VIEW_CONTAINER;


public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View mView;

    public LoginPresenter (LoginContract.View view) { mView = view; }

    @Override
    public void onLoginPressed(final String userName, final String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());
        String url = "http://the-fit-nation-dev.herokuapp.com/api/token";

        JsonObjectRequest jsonObjectPost = new JsonObjectRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println("succesful login attempt!!!!!!!\n\n" + response.toString());
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("failed " + error.getMessage());
            }
        }){
            @Override
            public byte[] getBody() {
                Map<String,String> params = new HashMap<>();
                params.put("username", userName);
                params.put("password", password);
                params.put("grant-type", "password");
                params.put("scope", "read write");
                params.put("client_secret", "production-secret-to-be-made");
                params.put("client_id", "TheFitNationapp");
                params.put("submit", "login");
                String postBody = generateFormDataForPostBody(params);

                return postBody.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "multipart/form-data;boundary=" + "&&");
                return params;
            }
        };

        requestQueue.add(jsonObjectPost);
        requestQueue.start();
    }

    private String generateFormDataForPostBody(Map<String, String> params){
        StringBuilder sbPost = new StringBuilder();
        for (String key : params.keySet()) {
            if (params.get(key) != null) {
                sbPost.append("\r\n" + "--" + "&&" + "\r\n");
                sbPost.append("Content-Disposition: form-data; name=\"" + key + "\"" + "\r\n\r\n");
                sbPost.append(params.get(key));
            }
        }

        return sbPost.toString();
    }

    @Override
    public void onFacebookLoginPressed() {    }

    @Override
    public void onGoogleLoginPressed() {    }

    @Override
    public void onForgotLoginButtonPressed() {
        ResetLoginFragment forgotLoginFragment = ResetLoginFragment.newInstance();
        forgotLoginFragment.setPresenter(new ResetLoginPresenter(forgotLoginFragment));
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("login fragment")
                .replace(VIEW_CONTAINER, forgotLoginFragment).commit();
    }

    @Override
    public void onSignUpButtonPressed() {
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        registerFragment.setPresenter(new RegisterPresenter(registerFragment));
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("login fragment")
                .replace(VIEW_CONTAINER, registerFragment).commit();
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
