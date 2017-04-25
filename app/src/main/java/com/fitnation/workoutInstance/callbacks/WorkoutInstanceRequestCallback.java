package com.fitnation.workoutInstance.callbacks;

import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.WorkoutInstance;

import java.util.List;

/**
 * Created by Ryan on 4/6/2017.
 */

public interface WorkoutInstanceRequestCallback {
    void onSuccess(List<WorkoutInstance> workoutInstances);
    void onFailure(String error);
}
