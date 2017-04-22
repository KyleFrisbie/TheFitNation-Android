package com.fitnation.workout.edit;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;

/**
 * Created by Ryan on 4/16/2017.
 */

public interface EditWorkoutContract {
    interface View extends BaseView<Presenter> {
        void updateData(UserWorkoutInstance userWorkoutInstance);
    }

    interface Presenter extends BasePresenter{
        void setUserWorkout(UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate);
        void onSavePressed();
    }
}
