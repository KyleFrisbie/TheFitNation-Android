package com.fitnation.networking.tasks.callbacks;

import com.fitnation.model.ExerciseInstance;

import java.util.List;

/**
 * Callback for a request to get Exercises
 */
public interface ExercisesRequestCallback {
    void onExercisesRetrieved(List<ExerciseInstance> exerciseListTab1, List<ExerciseInstance> exerciseListTab2, List<ExerciseInstance> exerciseListTab3);
    void onError();
}
