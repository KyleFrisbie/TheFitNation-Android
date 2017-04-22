package com.fitnation.workout.edit;

import android.util.Log;

import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;

/**
 * Created by Ryan on 4/16/2017.
 */
public class SaveWorkoutPresenter implements SaveWorkoutContract.Presenter{
    private static final String TAG = SaveWorkoutPresenter.class.getSimpleName();
    private UserWorkoutDataManager mDataManager;
    private UserWorkoutInstance mUserWorkoutInstance;
    private UserWorkoutTemplate mUserWorkoutTemplate;
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                mDataManager.saveUserWorkout(mUserWorkoutInstance, mUserWorkoutTemplate, new SaveWorkoutCallback() {
                    @Override
                    public void onSuccess() {
                        //TODO notify view
                        Log.i(TAG, "User Workout Instance saved succesfully");
                    }

                    @Override
                    public void onFailure(String error) {
                        //TODO notify of error
                        Log.i(TAG, "User Workout Instance save failed" + error);
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
