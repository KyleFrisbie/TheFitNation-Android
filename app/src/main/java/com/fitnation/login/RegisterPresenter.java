package com.fitnation.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.VolleyErrorMessage;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Presenter for register screen that contains all the business logic associated with the screen
 */
public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;

    public RegisterPresenter (RegisterContract.View view){ mView = view; }

    @Override
    public void onRegisterCreatePressed(final String email, final String password, final String userName,
                                        final String language) {
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());

        // TODO: convert to accept url class when it become available
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
                        handleJsonResponse();

                    }
                },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse != null) {
                            errorResponseMessage(error);
                        }else{
                            handleJsonResponse();
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

    // TODO: finish up handleing the return message

    /**
     * handles the json from the server
     */
    private void handleJsonResponse(){
        String message = null;
        mView.showProgress(message);
    }

    /**
     * gets and returns the error message to the view for display.
     * @param error error message containing http error codes
     */
    private void errorResponseMessage(VolleyError error){
        VolleyErrorMessage errorMessageFactory = new VolleyErrorMessage(error);
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
