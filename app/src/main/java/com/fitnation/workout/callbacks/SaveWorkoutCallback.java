package com.fitnation.workout.callbacks;

/**
 * Created by Ryan on 4/8/2017.
 */

public interface SaveWorkoutCallback {
    void onSuccess();
    void onFailure(String error);
}
