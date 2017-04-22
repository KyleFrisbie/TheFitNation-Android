package com.fitnation.workout.edit;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;

/**
 * Created by Ryan on 4/16/2017.
 */

public interface SaveWorkoutContract {
    interface View extends BaseView<SaveWorkoutContract.Presenter> {

    }

    interface Presenter extends BasePresenter {
        void onSavePressed();
        void setWorkoutData(UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate);
    }
}
