package com.fitnation.workoutInstance.parent;

import android.content.Context;
import android.support.annotation.Nullable;

import com.fitnation.model.WorkoutInstance;
import com.fitnation.workoutInstance.callbacks.OnWorkoutUpdatedCallback;
import com.fitnation.workoutInstance.callbacks.WorkoutsRequestCallback;

import java.util.List;

/**
 * Created by Erik on 4/24/2017.
 */

public class WorkoutInstanceParentPresenter implements WorkoutInstanceParentContract.Presenter, WorkoutsRequestCallback, OnWorkoutUpdatedCallback {
    private static final String TAG = WorkoutInstanceParentPresenter.class.getSimpleName();
    private Context mContext;
    private WorkoutManager mWorkoutManager;
    private WorkoutInstanceParentContract.View mView;
    private WorkoutInstance mWorkoutInstance;

    public WorkoutInstanceParentPresenter(Context mContext, WorkoutInstanceParentContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
    }

    @Override
    public void onViewReady() {
        //get the exercises
        mView.showProgress();
        mWorkoutManager.getWorkouts(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onDeletePressed(WorkoutInstance workoutInstance) {
        // TODO: remove the workout instance
    }

    @Override
    public void onLaunchPressed(WorkoutInstance workoutInstance) {
        // TODO: Ryan change code here to launch to your fragment/activity
    }

    @Override
    public void onDetailsPressed(WorkoutInstance workoutInstance) {
        // TODO: Add transition to one of ryans screens
    }

    //----------------------------------WorkoutsRequestCallback----------------------------------//

    @Override
    public void onWorkoutsRetrieved(List<WorkoutInstance> workoutList) {

    }

    @Override
    public void onError() {

    }

    //----------------------------------OnWorkoutUpdatedCallback----------------------------------//

    @Override
    public void workoutUpdated(@Nullable WorkoutInstance updatedWorkoutInstance) {
        if(updatedWorkoutInstance != null) {
            mWorkoutInstance = (WorkoutInstance) updatedWorkoutInstance.clone();
            mWorkoutManager.updateWorkoutList(mWorkoutInstance, updatedWorkoutInstance, mView);
            mView.displayUpdatedWorkouts(mWorkoutManager.getExercises());
        }
    }
}