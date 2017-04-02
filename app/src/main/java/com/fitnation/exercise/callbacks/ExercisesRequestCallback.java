package com.fitnation.exercise.callbacks;

import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;

import java.util.List;

/**
 * Created by Ryan on 3/21/2017.
 */
public interface ExercisesRequestCallback {
    void onExercisesRetrieved(List<ExerciseInstance> exerciseListTab1, List<ExerciseInstance> exerciseListTab2, List<ExerciseInstance> exerciseListTab3);
    void onError();
}
