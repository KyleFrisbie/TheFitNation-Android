package com.fitnation.exercise;

import android.content.Context;
import android.util.Log;

import com.fitnation.R;
import com.fitnation.exercise.callbacks.ExercisesRequestCallback;
import com.fitnation.exercise.callbacks.SaveDialogCallback;
import com.fitnation.exercise.edit.ViewExerciseFragment;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseAction;
import com.fitnation.navigation.NavigationActivity;

import java.util.List;

/**
 * Created by Ryan on 3/21/2017.
 */

public class ExercisesParentPresenter implements ExercisesParentContract.Presenter, ExercisesRequestCallback, SaveDialogCallback {
    private static final String TAG = ExercisesParentPresenter.class.getSimpleName();
    private ExercisesManager mExerciseManager;
    private ExercisesParentContract.View mView;

    public ExercisesParentPresenter(Context context, ExercisesParentContract.View view) {
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

    @Override
    public void onEditPressed(ExerciseInstance exercise) {
        mView.hideForEditExercise();
        NavigationActivity navigationActivity = (NavigationActivity) mView.getBaseActivity();
        navigationActivity.displayBackArrow(true, "Edit");
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.main_content, ViewExerciseFragment.newInstance(exercise)).addToBackStack(null).commit();
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
        mExerciseManager.createWorkoutAndSave(name, null);
    }
}
