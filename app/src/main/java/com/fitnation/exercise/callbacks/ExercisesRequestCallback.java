package com.fitnation.exercise.callbacks;

import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;

import java.util.List;

/**
 * Created by Ryan on 3/21/2017.
 */
public interface ExercisesRequestCallback {
    void onExercisesRetrieved(List<ExerciseInstance> exerciseList);
    void onError();
}
