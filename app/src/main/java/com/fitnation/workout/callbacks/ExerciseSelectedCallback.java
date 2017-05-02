package com.fitnation.workout.callbacks;

import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.ExerciseView;

/**
 * Callback for when an individual exercise is selected
 */
public interface ExerciseSelectedCallback {
    void onExerciseSelected(ExerciseView exerciseInstance, boolean isSelected);
}
