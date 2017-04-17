package com.fitnation.workout.edit;

import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;

/**
 * Created by Ryan on 4/16/2017.
 */
public class SaveWorkoutPresenter implements SaveWorkoutContract.Presenter{
    private UserWorkoutDataManager mDataManager;
    private UserWorkoutInstance mUserWorkoutInstance;
    private SaveWorkoutContract.View mView;

    public SaveWorkoutPresenter(SaveWorkoutContract.View view) {
        mView = view;
        mDataManager = new UserWorkoutDataManager();
    }

    @Override
    public void onViewReady() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onSavePressed() {
        mDataManager.saveUserWorkoutInstance(mUserWorkoutInstance, new SaveWorkoutCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    @Override
    public void setUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        mUserWorkoutInstance = userWorkoutInstance;
    }
}
