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
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.networking.JsonParser;
import com.fitnation.networking.tasks.callbacks.UserWorkoutInstancePostCallback;
import com.fitnation.networking.tasks.callbacks.WorkoutInstancePostCallback;
import com.fitnation.utils.EnvironmentManager;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Ryan on 4/22/2017.
 */

public class PostUserWorkoutInstanceTask extends NetworkTask {
    private static final String TAG = PostUserWorkoutInstanceTask.class.getSimpleName();

    public PostUserWorkoutInstanceTask(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }

    /**
     * Posts a WorkoutInstance to the web services
     * @param userWorkoutInstance - workout instance to be posted
     * @param callback - callback to be invoked after the response is finished
     */
    public void  postUserWorkoutInstance(final UserWorkoutInstance userWorkoutInstance, final UserWorkoutInstancePostCallback callback) {
        final String resourceRoute = "user-workout-instances";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;
        final StringRequest postWorkoutInstance = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        UserWorkoutInstance updatedInstance = JsonParser.convertJsonStringToPojo(response, UserWorkoutInstance.class);
                        callback.onSuccess(updatedInstance);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        callback.onFailure(String.valueOf(error.networkResponse.statusCode));
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
                String json = JsonParser.convertPojoToJsonString(userWorkoutInstance);
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

        postWorkoutInstance.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(postWorkoutInstance);
    }
}
