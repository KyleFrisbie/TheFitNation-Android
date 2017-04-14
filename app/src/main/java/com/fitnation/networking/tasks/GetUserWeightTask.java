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
import com.fitnation.profile.callbacks.GetUserCallback;
import com.fitnation.profile.callbacks.GetUserWeightCallback;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by J on 4/9/2017.
 */

public class GetUserWeightTask extends NetworkTask{

    private List<UserWeight> weightList;
    private UserWeight weight;
    private RequestQueue queue;
    private Environment env;
    private String url;

    private String TAG = GetUserWeightTask.class.getSimpleName();

    public GetUserWeightTask(String authToken, RequestQueue queue){
        super(authToken, queue);
    }

    public void getUserWeight(final GetUserWeightCallback callback){
        //WEIGHTS

        env = EnvironmentManager.getInstance().getCurrentEnvironment();
        url = env.getApiUrl()+"user-weights/byLoggedInUser/";

        final String authToken = AuthToken.getInstance().getAccessToken();

        JsonArrayRequest jsonRequestWeights =
                new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, response.toString());
                        weightList = JsonParser.convertJsonStringToList(response.toString(), UserWeight[].class);
                        callback.onSuccess(weightList);

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

        mRequestQueue.add(jsonRequestWeights);
    }

}
