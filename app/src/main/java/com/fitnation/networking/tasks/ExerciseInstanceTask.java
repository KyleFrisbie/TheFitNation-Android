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
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.networking.JsonParser;
import com.fitnation.networking.tasks.callbacks.GetExerciseInstanceCallback;
import com.fitnation.utils.EnvironmentManager;

import java.util.Map;

/**
 * Created by Ryan Newsom on 4/30/17. *
 */
public class ExerciseInstanceTask extends NetworkTask {
    private static final String TAG = ExerciseInstanceTask.class.getSimpleName();

    public ExerciseInstanceTask(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }

    public void getExerciseInstance(long id, final GetExerciseInstanceCallback callback) {
        final String resourceRoute = "exercise-instances" + "/+" + id;
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        StringRequest getExercisesRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final ExerciseInstance exerciseInstance = JsonParser.convertJsonStringToPojo(response, ExerciseInstance.class);
                        callback.onSuccess(exerciseInstance);
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
        };

        getExercisesRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(getExercisesRequest);

    }
}
