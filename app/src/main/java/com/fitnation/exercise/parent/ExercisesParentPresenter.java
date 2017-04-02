package com.fitnation.exercise.parent;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fitnation.R;
import com.fitnation.exercise.callbacks.ExercisesRequestCallback;
import com.fitnation.exercise.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.exercise.callbacks.SaveDialogCallback;
import com.fitnation.exercise.edit.ViewExerciseFragment;
import com.fitnation.exercise.edit.ViewExercisePresenter;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseAction;
import com.fitnation.navigation.NavigationActivity;

import java.util.List;

/**
 * Presenter for the ExercisesParent Fragment
 */
public class ExercisesParentPresenter implements ExercisesParentContract.Presenter, ExercisesRequestCallback, SaveDialogCallback, OnExerciseUpdatedCallback {
    private static final String TAG = ExercisesParentPresenter.class.getSimpleName();
    private ExercisesManager mExerciseManager;
    private ExercisesParentContract.View mView;
    private ExerciseInstance mExerciseInstanceBeingEdited;

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
        mExerciseInstanceBeingEdited = exercise;
        NavigationActivity navigationActivity = (NavigationActivity) mView.getBaseActivity();
        ViewExerciseFragment viewExerciseFragment = ViewExerciseFragment.newInstance(exercise);
        navigationActivity.displayBackArrow(true, "Edit");
        viewExerciseFragment.setPresenter(new ViewExercisePresenter(exercise, viewExerciseFragment, this));
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction().add(R.id.content_main_container, viewExerciseFragment).addToBackStack(null).commit();
    }

    //----------------------------------ExercisesRequestCallback----------------------------------//

    @Override
    public void onExercisesRetrieved(List<ExerciseInstance> exerciseList1, List<ExerciseInstance> exerciseList2, List<ExerciseInstance> exerciseList3) {
        mView.displayExercises(exerciseList1, exerciseList2, exerciseList3);
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

    //----------------------------------OnExerciseUpdatedCallback----------------------------------//

    @Override
    public void exerciseUpdated(@Nullable ExerciseInstance updatedExerciseInstance) {
        if(updatedExerciseInstance != null) {
            mExerciseInstanceBeingEdited = updatedExerciseInstance;
            mExerciseManager.updateExerciseList(mExerciseInstanceBeingEdited, updatedExerciseInstance, mView.getSelectedTab());
            mView.displayUpdatedExercises(mExerciseManager.getExercises());
        }
    }
}
