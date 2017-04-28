package com.fitnation.workoutInstance.callbacks;

import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.WorkoutInstance;

import java.util.List;

/**
 * Callback for a request to get Exercises
 */
public interface UserWorkoutInstancesRequestCallback {
    void onUserWorkoutInstancesRetrieved(List<UserWorkoutInstance> userWorkoutList);
    void onError();
}
