package com.fitnation.exercise.callbacks;

import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;

/**
 * Callback for when an individual exercise is selected
 */
public interface ExerciseSelectedCallback {
    void onExerciseSelected(ExerciseInstance exerciseInstance, boolean isSelected);
}
