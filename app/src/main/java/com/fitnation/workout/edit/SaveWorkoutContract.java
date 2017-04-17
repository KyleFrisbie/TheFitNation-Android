package com.fitnation.workout.edit;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.UserWorkoutInstance;

/**
 * Created by Ryan on 4/16/2017.
 */

public interface SaveWorkoutContract {
    interface View extends BaseView<SaveWorkoutContract.Presenter> {

    }

    interface Presenter extends BasePresenter {
        void onSavePressed();
        void setUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance);
    }
}
