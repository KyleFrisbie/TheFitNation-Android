package com.fitnation.workoutInstance.callbacks;

import android.support.annotation.Nullable;

import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.WorkoutInstance;

/**
 * Callback for when an exercise gets updated
 */
public interface OnWorkoutUpdatedCallback {
    /**
     * Notified when an workout is finished being updated
     * @param updatedWorkoutInstance - if null it wasn't updated
     */
    void workoutUpdated(@Nullable WorkoutInstance updatedWorkoutInstance);
}
