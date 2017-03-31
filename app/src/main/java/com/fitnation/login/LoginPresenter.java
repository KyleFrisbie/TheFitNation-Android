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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fitnation.login.LoginBaseActivity.VIEW_CONTAINER;

/**
 * Presenter for the login screen. contains all the login logic for the login screen
 */
public class LoginPresenter implements LoginContract.Presenter{
    private LoginContract.View mView;

    public LoginPresenter (LoginContract.View view) { mView = view; }

    @Override
    public void onLoginPressed(final String userName, final String password) {
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());

        // TODO: change over to the url selector class when that is implemented.
        String url = "http://the-fit-nation-dev.herokuapp.com/oauth/token";

        JsonObjectRequest jsonObjectPost = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {

                storeTokens(response);

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

    /**
     * Converts a hashmap of values to url encoded form for requests.
     * @param params Hashmap to be converted
     * @return String containing the converted hashmap
     */
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

    /**
     * Stores the json response token from the server to a local singleton for universal access
     * @param response The Json object from the server
     */
    private void storeTokens(JSONObject response){
        String accessToken;
        String refreshToken;

        try {

            accessToken = response.getString("access_token");
            refreshToken = response.getString("refresh_token");

            AuthToken.getInstance().setAccessToken(accessToken);
            AuthToken.getInstance().setRefreshToken(refreshToken);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the error message by getting the factory
     * @param error The volley error message.
     */
    private void errorResponseMessage(VolleyError error){
        VolleyErrorMessage volleyErrorMessage = new VolleyErrorMessage(error);
        mView.showAuthError(volleyErrorMessage.GetErrorMessage(mView.getBaseActivity()));
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
