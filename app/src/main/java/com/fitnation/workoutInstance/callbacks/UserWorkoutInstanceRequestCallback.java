package com.fitnation.workoutInstance.callbacks;

import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.WorkoutInstance;

import java.util.List;

/**
 * Callback for userWorkoutInstance endpoint transactions
 */

public interface UserWorkoutInstanceRequestCallback {
    interface getAll {
        void onGetAllSuccess(List<UserWorkoutInstance> userWorkoutInstances);
        void onGetAllFailure(String error);
    }

    interface update {
        void onUpdateSuccess(WorkoutInstance workoutInstance);
        void onUpdateFailure(String error);
    }
    interface delete {
        void onDeleteSuccess();
        void onDeleteFailure(String error);
    }
}
