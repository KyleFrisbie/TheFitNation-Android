package com.fitnation.workout.edit.template;

import android.support.v7.app.AlertDialog;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

import com.fitnation.model.WorkoutInstance;


/**
 * Created by Ryan on 4/23/2017.
 */

public interface EditWorkoutContract {
    interface View extends BaseView<EditWorkoutContract.Presenter> {
        void showSuccess(AlertDialog alertDialog);
        void showFailure(AlertDialog alertDialog);
        void showProgress();
        void hideProgress();
    }

    interface Presenter extends BasePresenter {
        void setWorkout(WorkoutInstance workoutInstance);
        void onSavePressed();
    }
}
