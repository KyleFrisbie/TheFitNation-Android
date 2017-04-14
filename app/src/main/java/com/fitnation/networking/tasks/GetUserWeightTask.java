package com.fitnation.networking.tasks;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.model.UserWeight;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.JsonParser;
import com.fitnation.profile.ProfilePresenter;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J on 4/9/2017.
 */

public class GetUserWeightTask {
    //TODO MAKE PRIVATE USE SETTERS/GETTERS
    static List<UserWeight> weightList;
    static UserWeight weight;
    static RequestQueue queue;
    static Environment env;
    static String url;


    public static void getUserWeight(final ProfilePresenter presenter){
        //WEIGHTS

        queue = Volley.newRequestQueue(presenter.getBaseActivity());
        env = EnvironmentManager.getInstance().getCurrentEnvironment();
        url = env.getApiUrl()+"user-weights/byLoggedInUser/";

        final String authToken = AuthToken.getInstance().getAccessToken();

        JsonArrayRequest jsonRequestWeights =
                new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("GET", response.toString());
                        weightList = JsonParser.convertJsonStringToList(response.toString(), UserWeight[].class);
                        weight = weightList.get(weightList.size()-1);
                        presenter.mProfile.addWeight(weight);
                        presenter.setUserWeight(weight);
                        presenter.bindExerciseInstanceToView();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("GET", error.toString());
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Accept", "application/json");
                        params.put("Authorization", "Bearer "+authToken);
                        return params;
                    }
                };

        queue.add(jsonRequestWeights);
    }

}
