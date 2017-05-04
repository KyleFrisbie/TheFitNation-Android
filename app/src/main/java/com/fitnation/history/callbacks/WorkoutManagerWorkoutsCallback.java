package com.fitnation.history.callbacks;

import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.WorkoutInstance;

import java.util.List;

/**
 * Callback for userWorkoutInstance endpoint transactions
 */
public interface WorkoutManagerWorkoutsCallback {
    interface instance {
        void onWorkoutInstancesRetrieved(List<WorkoutInstance> workoutList);
        void onError();
    }

    interface userInstance {
        void onUserWorkoutInstancesRetrieved(List<UserWorkoutInstance> userWorkoutList);
        void onUserError();
    }
}
