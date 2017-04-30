package com.fitnation.networking.tasks.callbacks;

import com.fitnation.model.UserWorkoutTemplate;

/**
 * Created by Ryan on 4/22/2017.
 */

public interface UserWorkoutTemplatePostCallback {
    void onSuccess(UserWorkoutTemplate updatedUserWorkoutTemplate);
    void onFailure(String error);
}
