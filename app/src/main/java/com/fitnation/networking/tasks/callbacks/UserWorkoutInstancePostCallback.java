package com.fitnation.networking.tasks.callbacks;

import com.fitnation.model.UserWorkoutInstance;

/**
 * Created by Ryan on 4/22/2017.
 */

public interface UserWorkoutInstancePostCallback {
    void onSuccess(UserWorkoutInstance updatedUserWorkoutInstance);
    void onFailure(String error);
}
