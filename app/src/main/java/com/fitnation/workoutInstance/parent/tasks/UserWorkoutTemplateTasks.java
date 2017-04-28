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
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.networking.JsonParser;
import com.fitnation.utils.EnvironmentManager;
import com.fitnation.workout.parent.tasks.NetworkTask;
import com.fitnation.workoutInstance.callbacks.UserWorkoutTemplateRequestCallback;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/**
 * Created by Ryan on 4/6/2017.
 */

public class UserWorkoutTemplateTasks extends NetworkTask{
    private static final String TAG = UserWorkoutTemplateTasks.class.getSimpleName();

    public UserWorkoutTemplateTasks(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }

    public void getAllUserWorkoutTemplates(final UserWorkoutTemplateRequestCallback.getAll callback) {
        final String resourceRoute = "workout-templates";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        final StringRequest getAllUserWorkoutTemplates = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final List<UserWorkoutTemplate> userWorkoutTemplates = JsonParser.convertJsonStringToList(response, UserWorkoutTemplate[].class);
                        callback.onGetAllSuccess(userWorkoutTemplates);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        callback.onGetAllFailure(error.getMessage());
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

        getAllUserWorkoutTemplates.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(getAllUserWorkoutTemplates);
    }

    public void updateUserWorkoutTemplate(final WorkoutTemplate workoutTemplate, final UserWorkoutTemplateRequestCallback.update callback) {
        final String resourceRoute = "workout-templates";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        final StringRequest updateUserWorkoutTemplate = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        WorkoutTemplate updatedTemplate = JsonParser.convertJsonStringToPojo(response, WorkoutTemplate.class);
                        callback.onUpdateSuccess(updatedTemplate);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        callback.onUpdateFailure(error.getMessage());
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
                String json = JsonParser.convertPojoToJsonString(workoutTemplate);
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

        updateUserWorkoutTemplate.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(updateUserWorkoutTemplate);
    }

    public void deleteUserWorkoutTemplate(final UserWorkoutTemplateRequestCallback.delete callback, final int id) {
        final String resourceRoute = "workout-templates/" + id;
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        final StringRequest deleteUserWorkoutTemplate = new StringRequest(Request.Method.DELETE, url,
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
                        callback.onDeleteFailure(error.getMessage());
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

        deleteUserWorkoutTemplate.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

        mRequestQueue.add(deleteUserWorkoutTemplate);
    }
}
