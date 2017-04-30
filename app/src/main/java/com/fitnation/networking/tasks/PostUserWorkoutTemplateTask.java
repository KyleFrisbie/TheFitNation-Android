package com.fitnation.networking.tasks;

import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.networking.JsonParser;
import com.fitnation.networking.tasks.callbacks.UserWorkoutTemplatePostCallback;
import com.fitnation.networking.tasks.callbacks.WorkoutTemplatePostCallback;
import com.fitnation.utils.EnvironmentManager;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Ryan on 4/22/2017.
 */

public class PostUserWorkoutTemplateTask extends NetworkTask{
    private static final String TAG = PostUserWorkoutTemplateTask.class.getSimpleName();

    public PostUserWorkoutTemplateTask(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }
    public void postUserWorkoutTemplate(final UserWorkoutTemplate userWorkoutTemplate, final UserWorkoutTemplatePostCallback callback) {
        final String resourceRoute = "user-workout-templates";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;
        final StringRequest postWorkoutTemplate = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        UserWorkoutTemplate updatedTemplate = JsonParser.convertJsonStringToPojo(response, UserWorkoutTemplate.class);
                        callback.onSuccess(updatedTemplate);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        callback.onFailure(error.getMessage());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mHeaders = new ArrayMap();

                mHeaders.put("Authorization", "Bearer" + " " + mAuthToken);
                mHeaders.put("Content-Type", "application/json");
                mHeaders.put("Accept", "application/json");

                return mHeaders;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String json = JsonParser.convertPojoToJsonString(userWorkoutTemplate);
                byte[] postBody = null;
                try
                {
                    postBody = json.toString().getBytes("utf-8");
                } catch (UnsupportedEncodingException e)
                {
                    Log.e(TAG, e.getMessage());
                }
                return postBody;
            }
        };

        postWorkoutTemplate.setRetryPolicy(new DefaultRetryPolicy(10000, 2, 1));

        mRequestQueue.add(postWorkoutTemplate);
    }
}
