package com.fitnation.workout.edit;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fitnation.base.DataManager;
import com.fitnation.base.DataResult;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.tasks.PostUserWorkoutInstanceTask;
import com.fitnation.networking.tasks.PostUserWorkoutTemplateTask;
import com.fitnation.networking.tasks.callbacks.UserWorkoutInstancePostCallback;
import com.fitnation.networking.tasks.callbacks.UserWorkoutTemplatePostCallback;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;

/**
 * Created by Ryan on 4/16/2017.
 */
public class UserWorkoutDataManager extends DataManager {
    private static final String TAG = UserWorkoutDataManager.class.getSimpleName();
    private RequestQueue mRequestQueue;

    public UserWorkoutDataManager(Context context) {
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public void saveUserWorkout(final UserWorkoutInstance userWorkoutInstance, final UserWorkoutTemplate userWorkoutTemplate, SaveWorkoutCallback saveWorkoutCallback) {
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
                        postToWebServices(userWorkoutTemplate, userWorkoutInstance);
                    }
                });
            }
        });
    }

    private void postToWebServices(final UserWorkoutTemplate userWorkoutTemplate, final UserWorkoutInstance userWorkoutInstance) {
        final String authToken = AuthToken.getInstance().getAccessToken();
        PostUserWorkoutTemplateTask postUserWorkoutTemplateTask = new PostUserWorkoutTemplateTask(authToken, mRequestQueue);
        postUserWorkoutTemplateTask.postUserWorkoutTemplate(userWorkoutTemplate, new UserWorkoutTemplatePostCallback() {
            @Override
            public void onSuccess(UserWorkoutTemplate updatedUserWorkoutTemplate) {
                Log.i(TAG, "User Workout Template posted to web success!");

                userWorkoutInstance.setUserWorkoutTemplateId(updatedUserWorkoutTemplate.getId());
                PostUserWorkoutInstanceTask postUserWorkoutInstanceTask = new PostUserWorkoutInstanceTask(authToken, mRequestQueue);
                postUserWorkoutInstanceTask.postUserWorkoutInstance(userWorkoutInstance, new UserWorkoutInstancePostCallback() {
                    @Override
                    public void onSuccess(UserWorkoutInstance updatedUserWorkoutInstance) {
                        Log.i(TAG, "User Workout Instance posted to web success!");
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.e(TAG, "User Workout Instance posted to web failure" + error);
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, "User Workout Template posted to web failure" + error);
            }
        });
    }

}
