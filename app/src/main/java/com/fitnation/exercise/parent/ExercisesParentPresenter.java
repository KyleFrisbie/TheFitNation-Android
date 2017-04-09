package com.fitnation.exercise.parent;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fitnation.R;
import com.fitnation.exercise.callbacks.ExercisesRequestCallback;
import com.fitnation.exercise.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.exercise.callbacks.SaveDialogCallback;
import com.fitnation.exercise.callbacks.SaveWorkoutCallback;
import com.fitnation.exercise.common.ExerciseAlertDialogFactory;
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
        mView.showProgress();
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
        ViewExerciseFragment viewExerciseFragment = ViewExerciseFragment.newInstance(exercise);

        viewExerciseFragment.setPresenter(new ViewExercisePresenter(exercise, viewExerciseFragment, this));
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction().add(R.id.content_main_container, viewExerciseFragment).addToBackStack(null).commit();
    }

    //----------------------------------ExercisesRequestCallback----------------------------------//

    @Override
    public void onExercisesRetrieved(List<ExerciseInstance> exerciseList1, List<ExerciseInstance> exerciseList2, List<ExerciseInstance> exerciseList3) {
        mView.stopProgress();
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
        mView.showProgress();
        mExerciseManager.createWorkoutAndSave(name, new SaveWorkoutCallback() {
            @Override
            public void onSuccess() {
                mView.stopProgress();
                mView.showSuccess(ExerciseAlertDialogFactory.getSuccess(mView.getBaseActivity(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                        //TODO clear back stack, launch Fragment for viewing created workouts
                    }
                }));

            }

            @Override
            public void onFailure(String error) {
                mView.stopProgress();
                mView.showFailure(ExerciseAlertDialogFactory.getErrorDialog(error, mView.getBaseActivity()));
            }
        });
    }

    //----------------------------------OnExerciseUpdatedCallback----------------------------------//

    @Override
    public void exerciseUpdated(@Nullable ExerciseInstance updatedExerciseInstance) {
        if(updatedExerciseInstance != null) {
            mExerciseInstanceBeingEdited = (ExerciseInstance) updatedExerciseInstance.clone();
            mExerciseManager.updateExerciseList(mExerciseInstanceBeingEdited, updatedExerciseInstance, mView.getSelectedTab());
            mView.displayUpdatedExercises(mExerciseManager.getExercisesTab1(), mExerciseManager.getExercisesTab2(), mExerciseManager.getExercisesTab3());
        }
    }
}
