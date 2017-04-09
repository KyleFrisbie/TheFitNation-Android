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
import com.android.volley.toolbox.Volley;
import com.fitnation.exercise.callbacks.ExerciseInstanceRequestCallback;
import com.fitnation.exercise.callbacks.ExercisesRequestCallback;
import com.fitnation.exercise.callbacks.GetSkillLevelsCallback;
import com.fitnation.exercise.callbacks.GetUnitsTaskCallback;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.Unit;
import com.fitnation.model.enums.SkillLevel;
import com.fitnation.networking.JsonParser;
import com.fitnation.utils.EnvironmentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gets ExerciseInstances from the available Exercises
 */
public class GetExerciseInstancesFromExercisesTask extends NetworkTask {
    private static final String TAG = GetExerciseInstancesFromExercisesTask.class.getSimpleName();

    /**
     * Constructor
     * @param authToken - authentication token to be used for request
     * @param requestQueue - request queue to be used
     */
    public GetExerciseInstancesFromExercisesTask(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }

    public void getExerciseInstancesFromExercises(final ExerciseInstanceRequestCallback callback) {
        String resourceRoute = "exercises";
        String url = EnvironmentManager.getInstance().getCurrentEnvironment().getApiUrl() + resourceRoute;

        StringRequest getExercisesRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        final List<Exercise> exercises = JsonParser.convertJsonStringToList(response, Exercise[].class);
                        final List<ExerciseInstance> exerciseInstances = convertExercisesToInstances(exercises);
                        GetUnitsTask getUnitsTask = new GetUnitsTask(mAuthToken, mRequestQueue);
                        getUnitsTask.getUnits(new GetUnitsTaskCallback() {
                            @Override
                            public void onSuccess(List<Unit> units) {
                                Unit effortUnit = null;
                                Unit repUnit = null;

                                for (Unit unit :units) {
                                    if(unit.getName().equals(Unit.DEFAULT_EFFORT_UNIT)) {
                                        effortUnit = unit;
                                    } else if (unit.getName().equals(Unit.DEFAULT_REPS_UNIT)) {
                                        repUnit = unit;
                                    }
                                }


                                for (ExerciseInstance exerciseInstance : exerciseInstances) {
                                    exerciseInstance.setEffortUnit(effortUnit);
                                    exerciseInstance.setRepUnit(repUnit);
                                }

                                callback.onSuccess(exerciseInstances);
                            }

                            @Override
                            public void onFailure(String error) {
                                Log.e(TAG, error.toString());
                                callback.onFailure(error);
                            }
                        });
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

    private List<ExerciseInstance> convertExercisesToInstances(List<Exercise> exercises) {
        List<ExerciseInstance> exerciseInstances = new ArrayList<>(exercises.size());
        for (Exercise exercise: exercises) {
            ExerciseInstance instance = new ExerciseInstance(exercise);
            exerciseInstances.add(instance);
        }

        return exerciseInstances;
    }
}
