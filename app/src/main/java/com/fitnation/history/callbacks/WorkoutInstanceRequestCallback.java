package com.fitnation.history.callbacks;

import com.fitnation.model.WorkoutInstance;

import java.util.List;

/**
 * Callback for WorkoutInstance endpoint transactions
 */

public interface WorkoutInstanceRequestCallback {
    interface getAll {
        void onGetAllSuccess(List<WorkoutInstance> workoutInstances);
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
