package com.fitnation.workout.callbacks;

import com.fitnation.model.WorkoutTemplate;

/**
 * Created by Ryan on 4/6/2017.
 */

public interface WorkoutTemplateRequestCallback {
    void onSuccess(WorkoutTemplate updatedTemplate);
    void onFailure(String error);
}
