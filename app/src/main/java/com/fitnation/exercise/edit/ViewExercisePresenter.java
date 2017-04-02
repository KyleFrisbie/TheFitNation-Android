package com.fitnation.exercise.edit;

import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseInstance;

/**
 * Created by Ryan on 4/1/2017.
 */

public class ViewExercisePresenter implements ViewExerciseContract.Presenter {
    private ExerciseInstance mExercise;
    private ViewExerciseContract.View mView;

    public ViewExercisePresenter(ExerciseInstance exercise, ViewExerciseContract.View view) {
        mExercise = exercise;
        mView = view;
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
    public void onSaveClicked() {

    }

    @Override
    public void onResetClicked() {

    }

    @Override
    public void onExit() {

    }
}
