package com.fitnation.exercise;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseFamily;
import com.fitnation.networking.EnvironmentManager;
import com.fitnation.networking.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Ryan on 3/21/2017.
 */
public class ExercisesManager {
    private static final String TAG = ExercisesManager.class.getSimpleName();
    private RequestQueue mRequestQueue;

    public ExercisesManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void getExercises(final ExercisesRequestCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Instantiate the RequestQueue.
                String resourceRoute = "exercises";

                String url = EnvironmentManager.getRequestUrl(resourceRoute);
                StringRequest getExercisesRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                List<Exercise> exercises = JsonParser.convertJsonStringToList(response, Exercise[].class);
                                List<ExerciseInstance> exerciseInstances = convertExercisesToInstances(exercises);
                                callback.onExercisesRetrieved(exerciseInstances);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e(TAG, error.toString());
                                callback.onError();
                            }
                        }
                ) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        String authToken = "811f0d0b-02a4-407a-8e4d-85ce0af348b8";
                        Map<String, String> mHeaders = new ArrayMap();

                        mHeaders.put("Authorization", "Bearer" + " " + authToken);
                        mHeaders.put("Content-Type", "application/json");

                        return mHeaders;
                    }
                };

                mRequestQueue.add(getExercisesRequest);
            }
        }).start();
    }

    private List<ExerciseInstance> convertExercisesToInstances(List<Exercise> exercises) {
        List<ExerciseInstance> exerciseInstances = new ArrayList<>(exercises.size());
        for (Exercise exercise: exercises) {
            ExerciseInstance instance = new ExerciseInstance(exercise);
            exerciseInstances.add(instance);
        }

        return exerciseInstances;
    }

    public static List<ExerciseInstance> filterExerciseBySkillLevel(List<ExerciseInstance> exerciseInstances, String filter) {
        List<ExerciseInstance> filteredList = new ArrayList<>();

        for (ExerciseInstance exerciseInstance: exerciseInstances) {
            if(exerciseInstance.getExercise().getSkillLevelLevel().equalsIgnoreCase(filter)) {
                filteredList.add(exerciseInstance);
            }
        }

        return filteredList;
    }
}
