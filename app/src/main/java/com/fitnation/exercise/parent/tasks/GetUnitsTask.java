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
import com.fitnation.exercise.callbacks.GetUnitsTaskCallback;
import com.fitnation.model.Unit;
import com.fitnation.model.enums.SkillLevel;
import com.fitnation.networking.JsonParser;
import com.fitnation.utils.EnvironmentManager;

import java.util.List;
import java.util.Map;

/**
 * Created by Ryan Newsom on 4/9/17. *
 */

public class GetUnitsTask extends NetworkTask{
    private static final String TAG = GetUnitsTask.class.getSimpleName();

    public GetUnitsTask(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }

    public void getUnits(final GetUnitsTaskCallback callback) {
        String resourceRoute = "units";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        StringRequest getExercisesRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Unit> units = JsonParser.convertJsonStringToList(response, Unit[].class);

                        callback.onSuccess(units);
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

                return mHeaders;
            }
        };

        getExercisesRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(getExercisesRequest);
    }

}
