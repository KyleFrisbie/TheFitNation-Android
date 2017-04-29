package com.fitnation.networking.tasks.callbacks;

import com.fitnation.model.WorkoutInstance;

/**
 * Created by Ryan on 4/6/2017.
 */

public interface WorkoutInstancePostCallback {
    void onSuccess(WorkoutInstance updatedWorkoutInstance);
    void onFailure(String error);
}
