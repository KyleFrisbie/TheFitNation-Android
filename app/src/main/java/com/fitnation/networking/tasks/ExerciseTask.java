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
import com.fitnation.model.Unit;
import com.fitnation.networking.JsonParser;
import com.fitnation.networking.tasks.callbacks.ExerciseInstanceRequestCallback;
import com.fitnation.networking.tasks.callbacks.ExerciseRequestCallback;
import com.fitnation.networking.tasks.callbacks.GetUnitsTaskCallback;
import com.fitnation.utils.EnvironmentManager;

import java.util.List;
import java.util.Map;

/**
 * Created by Ryan on 4/29/2017.
 */

public class ExerciseTask extends NetworkTask {
    private static final String TAG = ExerciseTask.class.getSimpleName();

    public ExerciseTask(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }

    public void getExerciseInstance(long id, final ExerciseRequestCallback callback) {
        final String resourceRoute = "exercises" + "/+" + id;
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        StringRequest getExercisesRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final Exercise exercise = JsonParser.convertJsonStringToPojo(response, Exercise.class);
                        callback.onSuccess(exercise);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        callback.onError(error.getMessage());
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
