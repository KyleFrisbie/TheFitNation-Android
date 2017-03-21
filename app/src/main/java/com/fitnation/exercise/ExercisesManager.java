package com.fitnation.exercise;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.networking.EnvironmentManager;
import com.fitnation.networking.JsonParser;

import java.util.ArrayList;
import java.util.List;

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
        // Instantiate the RequestQueue.
        String resourceRoute = "exercises";

        String url = EnvironmentManager.getRequestUrl(resourceRoute);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        List<Exercise> exercises = JsonParser.convertJsonStringToList(response, Exercise[].class);
                        List<ExerciseInstance> exerciseInstances = convertExercisesToInstances(exercises);
                        callback.onExercisesRetrieved(exerciseInstances);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage());
                callback.onError();
            }
        });
// Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    private List<ExerciseInstance> convertExercisesToInstances(List<Exercise> exercises) {
        List<ExerciseInstance> exerciseInstances = new ArrayList<>(exercises.size());
        for (Exercise exercise: exercises) {
            ExerciseInstance instance = new ExerciseInstance(exercise);
            exerciseInstances.add(instance);
        }

        return exerciseInstances;
    }

}
