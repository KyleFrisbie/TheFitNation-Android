package com.fitnation.networking.tasks;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.model.User;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.JsonParser;
import com.fitnation.networking.UserLogins;
import com.fitnation.networking.VolleyQueueSingleton;
import com.fitnation.profile.ProfileFragment;
import com.fitnation.profile.ProfilePresenter;
import com.fitnation.profile.callbacks.GetUserCallback;
import com.fitnation.utils.Environment;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by J on 4/9/2017.
 */

public class GetUserTask extends NetworkTask{

    public GetUserTask(String authToken, RequestQueue queue){
        super(authToken, queue);
    }

    public void getUser(final GetUserCallback callback){
        //USER
        Environment env = EnvironmentManager.getInstance().getCurrentEnvironment();
        String loginId = UserLogins.getUserLogin();
        String url = env.getApiUrl()+"users/"+loginId;
        final String authToken = AuthToken.getInstance().getAccessToken();

        JsonObjectRequest jsonRequestUser =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("GET", response.toString());
                        User user = JsonParser.convertJsonStringToPojo(response.toString(), User.class);
                        callback.onSuccess(user);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onFailure(error.toString());
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {

                        HashMap<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json");
                        params.put("Accept", "application/json");
                        params.put("Authorization", "Bearer "+ authToken);
                        return params;
                    }
                };

        mRequestQueue.add(jsonRequestUser);
    }


}
