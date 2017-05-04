package com.fitnation.history.parent.tasks;

import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.networking.JsonParser;
import com.fitnation.networking.tasks.NetworkTask;
import com.fitnation.utils.EnvironmentManager;
import com.fitnation.history.callbacks.WorkoutInstanceRequestCallback;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Ryan on 4/6/2017.
 */

public class WorkoutInstancesTasks extends NetworkTask {
    private static String TAG = WorkoutInstancesTasks.class.getSimpleName();

    public WorkoutInstancesTasks(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }

    public void getAllWorkoutInstances(final WorkoutInstanceRequestCallback.getAll callback) {
        final String resourceRoute = "workout-instances";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        final StringRequest getAllWorkoutInstances = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final List<WorkoutInstance> workoutInstances = JsonParser.convertJsonStringToList(response, WorkoutInstance[].class);
                        callback.onGetAllSuccess(workoutInstances);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        callback.onGetAllFailure(String.valueOf(error.networkResponse.statusCode));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mHeaders = new ArrayMap();

                mHeaders.put("Authorization", "Bearer" + " " + mAuthToken);

                return mHeaders;
            }
        };

        getAllWorkoutInstances.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(getAllWorkoutInstances);
    }

    public void updateWorkoutInstance(final WorkoutInstance workoutInstance, final WorkoutInstanceRequestCallback.update callback) {
        final String resourceRoute = "workout-instances";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        final StringRequest updateWorkoutInstance = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        WorkoutInstance updatedInstance = JsonParser.convertJsonStringToPojo(response, WorkoutInstance.class);
                        callback.onUpdateSuccess(updatedInstance);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        callback.onUpdateFailure(String.valueOf(error.networkResponse.statusCode));
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

        updateWorkoutInstance.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(updateWorkoutInstance);
    }

    public void deleteWorkoutInstance(final long id, final WorkoutInstanceRequestCallback.delete callback) {
        final String resourceRoute = "workout-instances/" + id;
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        final StringRequest deleteWorkoutInstance = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onDeleteSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        callback.onDeleteFailure(String.valueOf(error.networkResponse.statusCode));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mHeaders = new ArrayMap();

                mHeaders.put("Authorization", "Bearer" + " " + mAuthToken);

                return mHeaders;
            }
        };

        deleteWorkoutInstance.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(deleteWorkoutInstance);
    }
}
