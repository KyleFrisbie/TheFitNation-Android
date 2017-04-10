package com.fitnation.networking.tasks;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.model.UserWeight;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.JsonParser;
import com.fitnation.networking.VolleyQueueSingleton;
import com.fitnation.profile.ProfileFragment;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J on 4/9/2017.
 */

public class GetUserWeightTask {

    static List<UserWeight> weightList;
    static UserWeight weight;
    static RequestQueue queue;
    static Environment env;
    static String url;


    public static void getUserWeight(final ProfileFragment fragment){
        //WEIGHTS

        queue = Volley.newRequestQueue(fragment.getBaseActivity());
        env = EnvironmentManager.getInstance().getCurrentEnvironment();
        String userDemographicId = fragment.userDemoId.toString();
        url = env.getApiUrl()+"user-weights/byLoggedInUser/"+userDemographicId;

        //final String authToken = AuthToken.getInstance().getAccessToken();
        //TODO CHANGE THIS TO PREVIOUS COMMENTED OUT LINE
        final String authToken = AuthToken.getHardToken();

        JsonArrayRequest jsonRequestWeights =
                new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i("GET", response.toString());
                        weightList = JsonParser.convertJsonStringToList(response.toString(), UserWeight[].class);
                        weight = weightList.get(weightList.size()-1);
                        fragment.setUserWeight(weight);
                        fragment.loadUserWeight();
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
