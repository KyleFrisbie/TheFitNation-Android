package com.fitnation.workoutInstance.callbacks;

import com.fitnation.model.WorkoutInstance;

import java.util.List;

/**
 * Callback for a request to get Exercises
 */
public interface WorkoutInstancesRequestCallback {
    void onWorkoutInstancesRetrieved(List<WorkoutInstance> workoutList);
    void onError();
}
