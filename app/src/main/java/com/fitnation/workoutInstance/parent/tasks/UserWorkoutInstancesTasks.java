package com.fitnation.workoutInstance.parent.tasks;

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
import com.fitnation.networking.tasks.NetworkTask;
import com.fitnation.utils.EnvironmentManager;
import com.fitnation.workoutInstance.callbacks.UserWorkoutInstanceRequestCallback;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Ryan on 4/6/2017.
 */

public class UserWorkoutInstancesTasks extends NetworkTask {
    private static String TAG = UserWorkoutInstancesTasks.class.getSimpleName();

    public UserWorkoutInstancesTasks(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }

    public void getAllUserWorkoutInstances(final UserWorkoutInstanceRequestCallback.getAll callback) {
        final String resourceRoute = "user-workout-instances";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        final StringRequest getAllUserWorkoutInstance = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final List<UserWorkoutInstance> userWorkoutInstances = JsonParser.convertJsonStringToList(response, UserWorkoutInstance[].class);
                        callback.onGetAllSuccess(userWorkoutInstances);
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

        getAllUserWorkoutInstance.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(getAllUserWorkoutInstance);
    }

    public void updateUserWorkoutInstance(final WorkoutInstance workoutInstance, final UserWorkoutInstanceRequestCallback.update callback) {
        final String resourceRoute = "user-workout-instances";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        final StringRequest updateUserWorkoutInstance = new StringRequest(Request.Method.PUT, url,
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

        updateUserWorkoutInstance.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(updateUserWorkoutInstance);
    }

    public void deleteUserWorkoutInstance(final long id, final UserWorkoutInstanceRequestCallback.delete callback) {
        final String resourceRoute = "user-workout-instances/" + id;
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        final StringRequest deleteUserWorkoutInstance = new StringRequest(Request.Method.DELETE, url,
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

        deleteUserWorkoutInstance.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(deleteUserWorkoutInstance);
    }
}
