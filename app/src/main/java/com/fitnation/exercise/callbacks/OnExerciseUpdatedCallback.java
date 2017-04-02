package com.fitnation.exercise.callbacks;

import android.support.annotation.Nullable;

import com.fitnation.model.ExerciseInstance;

/**
 * Created by Ryan on 4/1/2017.
 */

public interface OnExerciseUpdatedCallback {
    /**
     * Notified when an exercise is finished being updated
     * @param updatedExerciseInstance - if null it wasn't updated
     */
    void exerciseUpdated(@Nullable ExerciseInstance updatedExerciseInstance);
}
