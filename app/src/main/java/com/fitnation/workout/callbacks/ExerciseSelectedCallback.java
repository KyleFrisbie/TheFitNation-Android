package com.fitnation.workout.callbacks;

import com.fitnation.model.ExerciseInstance;

/**
 * Callback for when an individual exercise is selected
 */
public interface ExerciseSelectedCallback {
    void onExerciseSelected(ExerciseInstance exerciseInstance, boolean isSelected);
}
