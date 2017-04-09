package com.fitnation.exercise.callbacks;

import com.fitnation.model.ExerciseInstance;

import java.util.List;

/**
 * Created by Ryan on 4/6/2017.
 */

public interface ExerciseInstanceRequestCallback {
    void onSuccess(List<ExerciseInstance> exerciseInstances);
    void onFailure(String error);
}
