package com.fitnation.workout.view.template;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.workout.view.instance.EditUserWorkoutContract;

/**
 * Created by Ryan on 4/23/2017.
 */

public interface EditWorkoutContract {
    interface View extends BaseView<EditWorkoutContract.Presenter> {
        void updateData(WorkoutInstance workoutInstance);
    }

    interface Presenter extends BasePresenter {
        void setWorkout(WorkoutInstance workoutInstance);
        void onSavePressed();
    }
}
