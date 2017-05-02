package com.fitnation.networking.tasks.callbacks;

import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.UserExerciseInstance;

import java.util.List;

/**
 * Created by Ryan on 4/30/2017.
 */

public interface GetExerciseInstancesForListCallback {
    void onSuccess(List<UserExerciseInstance> userExerciseInstancesUpdated);
    void onFailure(String error);
}
