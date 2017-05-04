package com.fitnation.workout.save;

import android.content.Context;

import android.support.v7.app.AlertDialog;
import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;

/**
 * Created by Ryan on 4/16/2017.
 */
public interface SaveUserWorkoutContract {
    interface View extends BaseView<SaveUserWorkoutContract.Presenter> {
        Context getContext();
        void showProgress();
        void stopProgress();
        void showError(AlertDialog alertDialog);
        void showSuccess(AlertDialog alertDialog);
    }

    interface Presenter extends BasePresenter {
        void onSavePressed();
        void setWorkoutData(UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate);
    }
}
