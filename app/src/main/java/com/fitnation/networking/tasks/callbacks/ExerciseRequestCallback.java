package com.fitnation.networking.tasks.callbacks;

import com.fitnation.model.Exercise;

/**
 * Created by Ryan on 4/29/2017.
 */

public interface ExerciseRequestCallback {
    void onError(String error);
    void onSuccess(Exercise exercise);
}
