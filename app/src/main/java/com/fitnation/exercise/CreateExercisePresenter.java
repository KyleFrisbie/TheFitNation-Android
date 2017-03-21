package com.fitnation.exercise;

import android.content.Context;

import com.fitnation.model.ExerciseInstance;

import java.util.List;

/**
 * Created by Ryan on 3/21/2017.
 */

public class CreateExercisePresenter implements CreateExerciseContract.Presenter, ExercisesRequestCallback {
    private ExercisesManager mExerciseManager;
    private CreateExerciseContract.View mView;

    public CreateExercisePresenter(Context context, CreateExerciseContract.View view) {
        mView = view;
        mExerciseManager = new ExercisesManager(context);
    }

    @Override
    public void onViewReady() {
        //get the exercises
        mExerciseManager.getExercises(this);

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    //----------------------------------ExercisesRequestCallback----------------------------------//

    @Override
    public void onExercisesRetrieved(List<ExerciseInstance> exerciseList) {
        mView.displayExercises(exerciseList);
    }

    @Override
    public void onError() {

    }
}
