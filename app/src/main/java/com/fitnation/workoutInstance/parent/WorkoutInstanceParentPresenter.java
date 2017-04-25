package com.fitnation.workoutInstance.parent;

import android.content.Context;

/**
 * Created by Erik on 4/24/2017.
 */

public class WorkoutInstanceParentPresenter implements WorkoutInstanceParentContract.Presenter{
    private Context mContext;
    private WorkoutInstanceParentContract.View mView;

    public WorkoutInstanceParentPresenter(Context mContext, WorkoutInstanceParentContract.View mView) {
        this.mContext = mContext;
        this.mView = mView;
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
}
