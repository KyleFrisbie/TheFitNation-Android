package com.fitnation.exercise.edit;

import com.fitnation.exercise.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.model.ExerciseInstance;

/**
 * Created by Ryan on 4/1/2017.
 */

public class ViewExercisePresenter implements ViewExerciseContract.Presenter {
    private ExerciseInstance mExercise;
    private ViewExerciseContract.View mView;
    private OnExerciseUpdatedCallback mOnExerciseUpdatedCallback;

    public ViewExercisePresenter(ExerciseInstance exercise, ViewExerciseContract.View view, OnExerciseUpdatedCallback onExerciseUpdatedCallback) {
        mExercise = exercise;
        mView = view;
        mOnExerciseUpdatedCallback = onExerciseUpdatedCallback;
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
    public void onAddSetClicked() {

    }

    @Override
    public void onSaveClicked(ExerciseInstance mExerciseInstance) {
        mView.getBaseActivity().getSupportFragmentManager().popBackStack();
        mOnExerciseUpdatedCallback.exerciseUpdated(mExerciseInstance);
    }

    @Override
    public void onResetClicked() {

    }

    @Override
    public void onExit() {

    }
}
