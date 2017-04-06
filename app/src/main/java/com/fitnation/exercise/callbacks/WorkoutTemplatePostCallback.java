package com.fitnation.exercise.callbacks;

import com.fitnation.model.WorkoutTemplate;

/**
 * Created by Ryan on 4/6/2017.
 */

public interface WorkoutTemplatePostCallback {
    void onSuccess(WorkoutTemplate updatedTemplate);
    void onFailure(String error);
}
