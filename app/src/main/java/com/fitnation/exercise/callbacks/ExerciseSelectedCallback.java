package com.fitnation.exercise.callbacks;

import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;

/**
 * Created by Ryan on 3/21/2017.
 */

public interface ExerciseSelectedCallback {
    void onExerciseSelected(ExerciseInstance exerciseInstance, boolean isSelected);
}
