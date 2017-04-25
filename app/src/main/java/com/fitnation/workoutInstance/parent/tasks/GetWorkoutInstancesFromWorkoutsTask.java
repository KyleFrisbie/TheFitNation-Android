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
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.Unit;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.networking.JsonParser;
import com.fitnation.utils.EnvironmentManager;
import com.fitnation.workout.callbacks.ExerciseInstanceRequestCallback;
import com.fitnation.workout.callbacks.GetUnitsTaskCallback;
import com.fitnation.workout.parent.tasks.GetUnitsTask;
import com.fitnation.workout.parent.tasks.NetworkTask;
import com.fitnation.workoutInstance.callbacks.WorkoutInstanceRequestCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gets ExerciseInstances from the available Exercises
 */
public class GetWorkoutInstancesFromWorkoutsTask extends NetworkTask {
    private static final String TAG = GetWorkoutInstancesFromWorkoutsTask.class.getSimpleName();



    /**
     * Constructor
     * @param authToken - authentication token to be used for request
     * @param requestQueue - request queue to be used
     */
    public GetWorkoutInstancesFromWorkoutsTask(String authToken, RequestQueue requestQueue) {
        super(authToken, requestQueue);
    }

    public void getWorkoutInstancesFromWorkouts(final WorkoutInstanceRequestCallback callback) {
        List<WorkoutInstance> workouts = null;
        int num = 0;
        for(int i = 0; i < 10; i++) {
            WorkoutInstance workoutInstance = new WorkoutInstance();
            workoutInstance.setName("the application" + num);
            workoutInstance.setLastUpdated("date changed" + num);
            if (workouts != null) {
                workouts.add(workoutInstance);
            }
            num++;
        }

        callback.onSuccess(workouts);
    }
}
