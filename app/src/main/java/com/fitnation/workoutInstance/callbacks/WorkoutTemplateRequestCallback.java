package com.fitnation.workoutInstance.callbacks;

import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;

import java.util.List;

/**
 * Created by Ryan on 4/6/2017.
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
