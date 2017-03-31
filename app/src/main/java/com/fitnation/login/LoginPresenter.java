package com.fitnation.login;

import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.VolleyErrorMessage;
import com.fitnation.navigation.NavigationActivity;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.RealmToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.realm.Realm;

import static com.fitnation.login.LoginBaseActivity.VIEW_CONTAINER;


public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View mView;
    final String boundary = "----WebKitFormBoundary7MA4YWxkTrZu0gW";

    public LoginPresenter (LoginContract.View view) { mView = view; }

    @Override
    public void onLoginPressed(final String userName, final String password) {
        //volley implementation
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());
        String url = "http://the-fit-nation-dev.herokuapp.com/oauth/token";


        JsonObjectRequest jsonObjectPost = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                // TODO: handle json response and get the token data into the singleton
                storeTokens(response);
                System.out.println(response + " stored: " + AuthToken.getInstance().getRefreshToken() +
                        " access: " + AuthToken.getInstance().getAccessToken());
                Intent mainActivityIntent = new Intent(mView.getBaseActivity(), NavigationActivity.class);
                mView.getBaseActivity().startActivity(mainActivityIntent);
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
                String bodyString = convertToUrlEncodedPostBody(params);
                return bodyString.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return ("application/x-www-form-urlencoded");
            }
        };

        requestQueue.add(jsonObjectPost);
        requestQueue.start();
    }

    private String convertToUrlEncodedPostBody(Map<String, String> params){
        StringBuilder sbPost = new StringBuilder();
        if(params != null) {
            int count = 0;
            for (String key : params.keySet()) {
                if (params.get(key) != null) {
                    if(count != 0) {
                        sbPost.append("&");
                    }
                    sbPost.append(key);
                    sbPost.append("=");
                    sbPost.append(params.get(key));
                    count++;
                }
            }
        }
        return sbPost.toString();
    }

    private void storeTokens(JSONObject response){
        String accessToken;
        String refreshToken;

        try {

            accessToken = response.getString("access_token");
            refreshToken = response.getString("refresh_token");

            // TODO: set up realm class for token and then store token in it for persistence
//            Realm realm = Realm.getDefaultInstance();
//            realm.beginTransaction();
//
//            RealmToken realmToken = realm.createObject(RealmToken.class);
//            realmToken.setAccessToken(accessToken);
//            realmToken.setRefreshToken(refreshToken);
//
//            realm.commitTransaction();

            AuthToken.getInstance().setAccessToken(accessToken);
            AuthToken.getInstance().setRefreshToken(refreshToken);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    private void errorResponseMessage(VolleyError error){
        VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(error);
        mView.showAuthError(volleyErrorMessage.GetErrorMessage(mView.getBaseActivity()));
    }
}
