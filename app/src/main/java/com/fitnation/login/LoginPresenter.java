package com.fitnation.login;

import android.content.Intent;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factories.VolleyErrorMessageFactory;
import com.fitnation.navigation.NavigationActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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


        JsonObjectRequest jsonObjectPost = new JsonObjectRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response) {
                String token = null;
                Intent mainActivityIntent = new Intent(mView.getBaseActivity(), NavigationActivity.class)
                        .putExtra("token", token);
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
                params.put("grant-type", "password");
                params.put("scope", "read write");
                params.put("client_secret", "my-secret-token-to-change-in-production");
                params.put("client_id", "TheFitNationapp");
                params.put("submit", "login");
                String postBody = generateFormDataForPostBody(params);
                return postBody.getBytes();
            }

//            @Override
//            public Map<String, String> getHeaders(){
//                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Content-Type", "multipart/form-data; boundary=" + boundary);
//                return headers;
//            }

            @Override
            public String getBodyContentType() {
                return ("multipart/form-data; boundary=" + boundary);
            }

        };

        requestQueue.add(jsonObjectPost);
        requestQueue.start();
    }

    private String generateFormDataForPostBody(Map<String, String> params){
        StringBuilder sbPost = new StringBuilder();
        if(params != null) {
            for (String key : params.keySet()) {
                if (params.get(key) != null) {
                    sbPost.append("\r\n" + "--" + boundary + "\r\n");
                    sbPost.append("Content-Disposition: form-data; name=\"");
                    sbPost.append(key);
                    sbPost.append("\"");
                    sbPost.append("\r\n\r\n");
                    sbPost.append(params.get(key));
                }
            }
        }
        sbPost.append("--" + boundary + "--");
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

    private void errorResponseMessage(VolleyError error){
        VolleyErrorMessageFactory volleyErrorMessageFactory = new VolleyErrorMessageFactory(error);
        mView.showAuthError(volleyErrorMessageFactory.GetErrorMessage());
    }
}
