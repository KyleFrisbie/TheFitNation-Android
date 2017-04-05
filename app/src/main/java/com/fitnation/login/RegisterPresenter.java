package com.fitnation.login;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.VolleyErrorMessage;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Presenter for register screen that contains all the business logic associated with the screen
 */
public class RegisterPresenter implements RegisterContract.Presenter{
    private RegisterContract.View mView;

    public RegisterPresenter (RegisterContract.View view){ mView = view; }

    @Override
    public void onRegisterCreatePressed(final String email, final String password, final String userName,
                                        final String language) {
        RequestQueue requestQueue = Volley.newRequestQueue(mView.getBaseActivity());
        String endpoint = "api/register";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getBaseUrl() + endpoint;

        StringRequest jsonObjectPost = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // TODO: convert to a successResponse return
                        handleJsonResponse();

                    }
                },  new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse != null) {
                            errorResponseMessage(error);
                        }
                    }
                }
                ){

            @Override
            public byte[] getBody() {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("langKey", language);
                map.put("login", userName);
                map.put("password", password);

                JSONObject jsonObject = new JSONObject(map);
                return jsonObject.toString().getBytes();
            }

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

    /**
     * handles the json from the server
     */
    private void handleJsonResponse(){
        String message = null;
        mView.showSuccess();
    }

    /**
     * gets and returns the error message to the view for display.
     * @param error error message containing http error codes
     */
    private void errorResponseMessage(VolleyError error){
        VolleyErrorMessage errorMessageFactory = new VolleyErrorMessage(error);
        mView.showAuthError(errorMessageFactory.getErrorMessage(mView.getBaseActivity()));
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
