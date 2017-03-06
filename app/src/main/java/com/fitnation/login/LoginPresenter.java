package com.fitnation.login;

import android.content.Intent;
import android.os.SystemClock;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.navigation.NavigationActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fitnation.login.LoginBaseActivity.VIEW_CONTAINER;

/**
 * Created by Ryan on 1/31/2017.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View mView;

    public LoginPresenter (LoginContract.View view) { mView = view; }

    @Override
    public void onFacebookLoginPressed() {


    }

    @Override
    public void onGoogleLoginPressed() {


    }

    @Override
    public void onLoginPressed(final String userName, final String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());
        String url = "http://www.the-fit-nation.com/api/account";

        StringRequest sr = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    //send to gson/json object for formatting
                    System.out.println("succesful login attempt!!!!!!!\n\n" + response);
                }
            },  new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("failed " + error);
                }
            }
        ){
            /*
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", "eeverson@msudenver.edu");
                params.put("firstName", "Erik");
                params.put("lastName", "Everson");
                params.put("login", "eeverson");
                params.put("password", "Pa55w0rd");

                return params;
            }
            */

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                //change bearer token to current one
                params.put("Authorization", "bearer 821cea17-7d89-4e53-9e4b-23480f1be5e7");

                return params;
            }

        };

        requestQueue.add(sr);
        requestQueue.start();
    }

    @Override
    public void onSignUpButtonPressed() {
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        registerFragment.setPresenter(new RegisterPresenter(registerFragment));
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("login")
                .replace(VIEW_CONTAINER, registerFragment).commit();
    }

    @Override
    public void onForgotLoginButtonPressed() {
        ResetLoginFragment forgotLoginFragment = ResetLoginFragment.newInstance();
        forgotLoginFragment.setPresenter(new ResetLoginPresenter(forgotLoginFragment));
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("login")
                .replace(VIEW_CONTAINER, forgotLoginFragment).commit();
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

    private void homeActivityIntent(){
        mView.getBaseActivity().startActivity(new Intent(mView.getBaseActivity(), NavigationActivity.class));
        mView.getBaseActivity().finish();
    }

    private void returnAuthError(){
        mView.showAuthError();
    }
}
