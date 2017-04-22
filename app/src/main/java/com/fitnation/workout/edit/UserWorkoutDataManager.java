package com.fitnation.workout.edit;

import android.util.Log;

import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;

/**
 * Created by Ryan on 4/16/2017.
 */
public class UserWorkoutDataManager extends DataManager {
    private static final String TAG = UserWorkoutDataManager.class.getSimpleName();

    public void saveUserWorkout(final UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate, SaveWorkoutCallback saveWorkoutCallback) {
        saveData(userWorkoutTemplate, new DataResult() {
            @Override
            public void onError() {
                Log.e(TAG, "Saving user workout template failed");
            }

            @Override
            public void onSuccess() {
                Log.e(TAG, "Saving user workout template success!");
                saveData(userWorkoutInstance, new DataResult() {
                    @Override
                    public void onError() {
                        Log.e(TAG, "Saving user workout instance failed");
                    }

                    @Override
                    public void onSuccess() {
                        Log.e(TAG, "Saving user workout instance success!");
                        //TODO post to web service
                    }
                });
            }
        });
    }

}
