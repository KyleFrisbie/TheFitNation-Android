package com.fitnation.workoutInstance.parent;

import android.content.Context;
import android.util.Log;

import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.workoutInstance.callbacks.WorkoutManagerWorkoutsCallback;

import java.util.List;

/**
 * Calls classes, preforms business logic and returns data to view for display.
 */

public class WorkoutInstanceParentPresenter implements WorkoutInstanceParentContract.Presenter, WorkoutManagerWorkoutsCallback.instance, WorkoutManagerWorkoutsCallback.userInstance {
    private static final String TAG = WorkoutInstanceParentPresenter.class.getSimpleName();
    private WorkoutManager mWorkoutManager;
    private WorkoutInstanceParentContract.View mView;

    public WorkoutInstanceParentPresenter(Context mContext, WorkoutInstanceParentContract.View mView) {
        mWorkoutManager = new WorkoutManager(mContext);
        this.mView = mView;
    }

    @Override
    public void onViewReady() {
        //get the workouts
        mView.showProgress();
        mWorkoutManager.getAllWorkoutInstances(this);
        mWorkoutManager.getAllUserWorkoutInstances(this);
    }

    @Override
    public void start() {
        Log.i(TAG, "onStart()");
    }

    @Override
    public void stop() {
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDeletePressed(WorkoutInstance workoutInstance) {
        // TODO: remove the workout instance
        Log.i(TAG, "onDeletePressed()");
        mWorkoutManager.deleteWorkoutInstance(workoutInstance);
    }

    @Override
    public void onLaunchPressed(WorkoutInstance workoutInstance) {
        // TODO: Ryan change code here to launch to your fragment/activity
        Log.i(TAG, "onLaunchPressed()");
    }

    @Override
    public void onDetailsPressed(WorkoutInstance workoutInstance) {
        // TODO: Add transition to one of ryans screens
        Log.i(TAG, "onDetailsPressed()");
    }

    //---------------------------WorkoutManagerWorkoutsCallback.instance--------------------------//

    @Override
    public void onWorkoutInstancesRetrieved(List<WorkoutInstance> workoutList) {
        mView.stopProgress();
        mView.displayWorkouts(workoutList);
    }

    @Override
    public void onError() {
        Log.e(TAG, "Error from workout instances");
    }

    //-------------------------WorkoutManagerRequestCallback.userInstance-------------------------//

    @Override
    public void onUserWorkoutInstancesRetrieved(List<UserWorkoutInstance> userWorkoutList) {
        mView.stopProgress();
        mView.displayUserWorkouts(userWorkoutList);
    }

    @Override
    public void onUserError() {
        Log.e(TAG, "Error from user workout instances");
    }
}
