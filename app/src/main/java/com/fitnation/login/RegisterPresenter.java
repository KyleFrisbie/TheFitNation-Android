package com.fitnation.login;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factories.VolleyErrorMessageFactory;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Erik on 2/18/2017.
 */

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
                        errorResponseMessage(error);
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

    private void errorResponseMessage(VolleyError error){
        VolleyErrorMessageFactory errorMessageFactory = new VolleyErrorMessageFactory(error);
        mView.showAuthError(errorMessageFactory.GetErrorMessage(mView.getBaseActivity()));
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
