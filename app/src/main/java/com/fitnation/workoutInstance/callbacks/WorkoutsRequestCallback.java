package com.fitnation.workoutInstance.callbacks;

import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.WorkoutInstance;

import java.util.List;

/**
 * Callback for a request to get Exercises
 */
public interface WorkoutsRequestCallback {
    void onWorkoutsRetrieved(List<WorkoutInstance> workoutList);
    void onError();
}
