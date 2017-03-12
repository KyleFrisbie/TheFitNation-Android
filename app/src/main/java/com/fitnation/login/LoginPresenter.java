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
        String url = "http://www.the-fit-nation-dev.herokuapp.com/api/register";
        Map<String, String> map = new HashMap<>();
        map.put("email", "eeverson@msudenver.edu");
        map.put("langKey", "en");
        map.put("login", userName);
        map.put("password", password);



        JsonObjectRequest jsonObjectPost = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(map), new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                //send to gson/json object for formatting
                System.out.println("succesful login attempt!!!!!!!\n\n" + response);
            }
        },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("failed " + error.getMessage());
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }

        };

        requestQueue.add(jsonObjectPost);
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
