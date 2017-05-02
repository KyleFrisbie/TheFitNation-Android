package com.fitnation.networking.tasks.callbacks;

import com.fitnation.model.ExerciseInstance;

/**
 * Created by Ryan Newsom on 4/30/17. *
 */

public interface GetExerciseInstanceCallback {
    void onSuccess(ExerciseInstance exerciseInstance);
    void onFailure(String error);
}
