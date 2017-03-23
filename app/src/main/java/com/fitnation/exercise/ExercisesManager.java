package com.fitnation.exercise;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.exercise.callbacks.ExercisesRequestCallback;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
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
    private List<ExerciseInstance> mSelectedExercises;


    public ExercisesManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
        mSelectedExercises = new ArrayList<>();
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
                        String authToken = "fd352e55-36f5-4632-a3e9-31cd6521fa28";
                        Map<String, String> mHeaders = new ArrayMap();

                        mHeaders.put("Authorization", "Bearer" + " " + authToken);
                        mHeaders.put("Content-Type", "application/json");

                        return mHeaders;
                    }
                };

                getExercisesRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 1, 1));

                mRequestQueue.add(getExercisesRequest);
            }
        }).start();
    }

    public void exerciseInstanceSelected(ExerciseInstance exerciseInstance, boolean isSelected) {
        if(isSelected) {
            Log.i(TAG, "Exercise was selected: " + exerciseInstance.getExercise().getName());
            mSelectedExercises.add(exerciseInstance);
        } else {
            Log.i(TAG, "Attempting to remove Exercise");
            if(mSelectedExercises.remove(exerciseInstance)){
                Log.i(TAG, "Exercise was removed: " + exerciseInstance.getExercise().getName());
            }
        }
    }

    /**
     * Creates a workout out of the currently selected exercises and saves it under the given name
     * @param name - The name of the workout
     */
    public void createWorkoutAndSave(String name) {
        //TODO Create UserExerciseInstance out of the ExerciseInstance list
        //TODO Create a UserWorkoutTemplate with a child UserWorkoutInstance
        //TODO Save the WorkoutInstance to the DB
        //TODO Save the UserWorkoutTemplate to the web service
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
