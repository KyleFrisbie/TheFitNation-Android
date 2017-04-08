package com.fitnation.exercise.parent.tasks;

import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fitnation.exercise.callbacks.WorkoutInstancePostCallback;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.networking.JsonParser;
import com.fitnation.utils.EnvironmentManager;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Ryan on 4/6/2017.
 */

public class PostWorkoutInstanceTask extends NetworkTask {
    private static String TAG = PostWorkoutInstanceTask.class.getSimpleName();

    public PostWorkoutInstanceTask(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }

    /**
     * Posts a WorkoutInstance to the web services
     * @param workoutInstance - workout instance to be posted
     * @param callback - callback to be invoked after the response is finished
     */
    public void  postWorkoutInstance(final WorkoutInstance workoutInstance, final WorkoutInstancePostCallback callback) {
        final String resourceRoute = "workout-instances";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;
        final StringRequest postWorkoutInstance = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        WorkoutInstance updatedInstance = JsonParser.convertJsonStringToPojo(response, WorkoutInstance.class);
                        callback.onSuccess(updatedInstance);
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

                return mHeaders;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String json = JsonParser.convertPojoToJsonString(workoutInstance);
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
