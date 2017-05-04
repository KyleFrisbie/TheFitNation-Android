package com.fitnation.history.callbacks;

import com.fitnation.model.WorkoutTemplate;

import java.util.List;

/**
 * Callback for WorkoutTemplate endpoint transactions
 */

public interface WorkoutTemplateRequestCallback {
    interface getAll {
        void onGetAllSuccess(List<WorkoutTemplate> userWorkoutInstances);
        void onGetAllFailure(String error);
    }

    interface update {
        void onUpdateSuccess(WorkoutTemplate workoutInstance);
        void onUpdateFailure(String error);
    }
    interface delete {
        void onDeleteSuccess();
        void onDeleteFailure(String error);
    }
}
