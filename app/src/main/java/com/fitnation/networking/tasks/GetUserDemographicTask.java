package com.fitnation.networking.tasks;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.model.ProfileInstance;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.JsonParser;
import com.fitnation.profile.ProfileFragment;
import com.fitnation.profile.ProfilePresenter;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeremy on 4/9/2017.
 */

public class GetUserDemographicTask {

    static EnvironmentManager em;
    static Environment env;
    static String url;
    static String accessToken;
    static RequestQueue queue;
    UserDemographic userdemo;


    public static void getUserDemographic(final ProfilePresenter presenter, String userDemographicId){
        final ProfileInstance profileInstance = new ProfileInstance();
        queue = Volley.newRequestQueue(presenter.getBaseActivity());

        //USERDEMOGRAPHIC
        JsonObjectRequest jsonRequestUserDemo =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("GET", response.toString());
                        UserDemographic userdemo = JsonParser.convertJsonStringToPojo(response.toString(), UserDemographic.class);
                        profileInstance.addUserDemographicInfo(userdemo);
                        presenter.setDemographic(userdemo);
                        GetUserTask.getUser(userdemo.getUserLogin(), presenter);
                        GetUserWeightTask.getUserWeight(presenter);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        presenter.setUser(new User());
                        presenter.setDemographic(new UserDemographic());
                        Log.d("GET", error.toString());
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Accept", "application/json");
                        params.put("Authorization", "Bearer "+accessToken);
                        return params;
                    }
                };

        queue.add(jsonRequestUserDemo);
    }

}
