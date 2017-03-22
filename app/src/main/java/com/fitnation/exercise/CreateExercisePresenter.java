package com.fitnation.exercise;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;

import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 3/21/2017.
 */

public class CreateExercisePresenter implements CreateExerciseContract.Presenter, ExercisesRequestCallback, SaveDialogCallback {
    private static final String TAG = CreateExercisePresenter.class.getSimpleName();
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

    @Override
    public void onActionButtonClicked(ExerciseAction action) {
        Log.i(TAG, "ExerciseActionButtonClicked: " + action);
        if(action == ExerciseAction.SAVE) {
            mView.promptUserToSave(SaveWorkoutDialogFragment.newInstance(this));

        } else if(action == ExerciseAction.LAUNCH) {
            //TODO Launch a new instance of this workout
        }
    }

    @Override
    public void onExerciseSelected(ExerciseInstance exerciseInstance, boolean isSelected) {
        mExerciseManager.exerciseInstanceSelected(exerciseInstance, isSelected);
    }

    //----------------------------------SaveDialogCallback----------------------------------//

    @Override
    public void onSaveRequested(String name) {
        Log.i(TAG, "User requested to save workout with name: " + name);
        mExerciseManager.createWorkoutAndSave();
    }
}
