package com.fitnation.workout.view.instance;

import android.content.Context;
import android.util.Log;

import com.fitnation.workout.services.UserWorkoutDataManager;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;
import com.fitnation.workout.common.WorkoutAlertDialogFactory;

/**
 * Created by Ryan on 4/16/2017.
 */
public class SaveUserWorkoutPresenter implements SaveUserWorkoutContract.Presenter{
    private static final String TAG = SaveUserWorkoutPresenter.class.getSimpleName();
    private UserWorkoutDataManager mDataManager;
    private UserWorkoutInstance mUserWorkoutInstance;
    private UserWorkoutTemplate mUserWorkoutTemplate;
    private SaveUserWorkoutContract.View mView;

    public SaveUserWorkoutPresenter(SaveUserWorkoutContract.View view, Context context) {
        mView = view;
        mDataManager = new UserWorkoutDataManager(context);
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
        mView.showProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDataManager.saveUserWorkout(mUserWorkoutInstance, mUserWorkoutTemplate, new SaveWorkoutCallback() {
                    @Override
                    public void onSuccess() {
                        mView.stopProgress();
                        Log.i(TAG, "User Workout saved succesfully");
                        //TODO take user to saved user workout instances page
                    }

                    @Override
                    public void onFailure(String error) {
                        mView.stopProgress();
                        mView.showError(WorkoutAlertDialogFactory.getCreateWorkoutInstanceError(error, mView.getBaseActivity()));
                        Log.i(TAG, "User Workout save failed" + error);
                    }
                });
            }
        }).start();
    }

    @Override
    public void setWorkoutData(UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate) {
        mUserWorkoutInstance = userWorkoutInstance;
        mUserWorkoutTemplate = userWorkoutTemplate;
    }
}
