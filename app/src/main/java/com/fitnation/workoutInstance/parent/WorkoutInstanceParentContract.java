package com.fitnation.workoutInstance.parent;


import android.support.v7.app.AlertDialog;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutView;

import java.util.List;

/**
 * Created by Erik on 4/24/2017.
 */

public interface WorkoutInstanceParentContract {
    interface View extends BaseView<Presenter> {
        void showProgress();
        void stopProgress();
        void displayWorkouts(List<WorkoutInstance> workouts);
        void displayUserWorkouts(List<UserWorkoutInstance> userWorkouts);
        void displayUpdatedWorkouts(List<WorkoutInstance> workoutList);

        void displayUpdatedUserWorkouts(List<UserWorkoutInstance> userWorkoutsList);
        void showSuccess(AlertDialog alertDialog);
        void showFailure(AlertDialog alertDialog);

    }

    interface Presenter extends BasePresenter{
        void onDeletePressed(WorkoutView workoutInstance);
        void onLaunchPressed(WorkoutInstance workoutInstance);

        void onDetailsPressed(WorkoutView workoutInstance);
    }
}
