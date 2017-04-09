package com.fitnation.exercise.parent.tasks;

import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.fitnation.exercise.callbacks.WorkoutTemplatePostCallback;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.networking.JsonParser;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ryan on 4/6/2017.
 */

public class PostWorkoutTemplateTask extends NetworkTask{
    private static final String TAG = PostWorkoutTemplateTask.class.getSimpleName();

    public PostWorkoutTemplateTask(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }
    public void postWorkoutTemplate(final WorkoutTemplate workoutTemplate, final WorkoutTemplatePostCallback callback) {
        final String resourceRoute = "workout-templates";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;
        String json = JsonParser.convertPojoToJsonString(workoutTemplate);
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(json);
            Log.i("JSON", json);
        } catch (org.json.JSONException e) {
            Log.d("JSON", "Failed to convert User Demographic to JSON String");
            jsonObject = new JSONObject();
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                WorkoutTemplate updatedTemplate = JsonParser.convertJsonStringToPojo(response.toString(), WorkoutTemplate.class);
                callback.onSuccess(updatedTemplate);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer "+ mAuthToken);

                return params;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(jsonRequest);
    }
}
