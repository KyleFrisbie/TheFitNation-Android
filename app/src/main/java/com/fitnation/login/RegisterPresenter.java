package com.fitnation.login;


import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.fitnation.login.LoginBaseActivity.VIEW_CONTAINER;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;

    public RegisterPresenter (RegisterContract.View view){ mView = view; }

    @Override
    public void onRegisterCreatePressed(final String email, final String password, final String userName,
                                        final String language) {
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());
        String url = "http://the-fit-nation-dev.herokuapp.com/api/register";
        Map<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("langKey", language);
        map.put("login", userName);
        map.put("password", password);

        /*
        * This implementation of volley has a workaround which ignores the null return from the
        * server. The solution to this is to create a custom request which will accept string
        * responses from the server yet will be able to send a JSON Body.
         */
        JsonObjectRequest jsonObjectPost = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(map), new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse != null){
                            errorResponseMessage(error);
                        }else{
                            registerResponse("sucessful register", true);
                        }
                    }
                }
                ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                return params;
            }
        };

        requestQueue.add(jsonObjectPost);
        requestQueue.start();

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

    private void responseMessage(String message){
        mView.showProgress(message);
    }

    private void errorResponseMessage(VolleyError error){
        VolleyErrorMessageGenerator volleyErrorMessageGenerator = new VolleyErrorMessageGenerator(error);
        mView.showAuthError(volleyErrorMessageGenerator.GetErrorMessage());
    }

    private void registerResponse(String response, Boolean registerSuccess){
        mView.showProgress(response);
        if(registerSuccess){
            String message = "Registration Successful";
            responseMessage(message);
            mView.getBaseActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
