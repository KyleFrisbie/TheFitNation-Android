package com.fitnation.workout.create;

import android.util.Log;

import com.android.volley.toolbox.Volley;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseSetView;
import com.fitnation.model.ExerciseView;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.tasks.ExerciseTask;
import com.fitnation.networking.tasks.callbacks.ExerciseRequestCallback;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.workout.callbacks.OnSetSelectedCallback;

import java.util.List;

/**
 * Presenter for viewing an exercise
 */
public class ViewExercisePresenter implements ViewExerciseContract.Presenter, OnSetSelectedCallback{
    private static final String TAG = ViewExercisePresenter.class.getSimpleName();
    private ExerciseView mExercise;
    private ViewExerciseContract.View mView;
    private OnExerciseUpdatedCallback mOnExerciseUpdatedCallback;
    private ExerciseView mOriginalExerciseInstance;


    public ViewExercisePresenter(ExerciseView exercise, ViewExerciseContract.View view, OnExerciseUpdatedCallback onExerciseUpdatedCallback) {
        mExercise = exercise;
        mView = view;
        mOnExerciseUpdatedCallback = onExerciseUpdatedCallback;
        mOriginalExerciseInstance = (ExerciseView) exercise.clone();
    }

    @Override
    public void onViewReady() {
        mView.bindExerciseInstanceToView(mExercise, this);
        if(!mExercise.hasExerciseParent()) {
            ExerciseTask exerciseTask = new ExerciseTask(AuthToken.getInstance().getAccessToken(), Volley.newRequestQueue(mView.getBaseActivity()));
            exerciseTask.getExerciseInstance(mExercise.getParentExerciseId(), new ExerciseRequestCallback() {
                @Override
                public void onError(String error) {
                    Log.e(TAG, "Error retrieving parent exercise" + error);
                }

                @Override
                public void onSuccess(Exercise exercise) {
                    Log.i(TAG, "Success retrieving parent exercise" + exercise);
                    mExercise.setParentExercise(exercise);
                    mView.bindExerciseInstanceToView(mExercise, ViewExercisePresenter.this);
                }
            });
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onAddSetClicked() {
        List<ExerciseSetView> sets =  mExercise.getExerciseSetView();
        int orderNumber = sets.size() + 1;

        mExercise.addExerciseSetView(mExercise, orderNumber);

        mView.bindExerciseInstanceToView(mExercise, this);
    }

    @Override
    public void onSaveClicked(ExerciseView exerciseInstance) {
        onExit(exerciseInstance);
        mView.getBaseActivity().onBackPressed();
    }

    @Override
    public void onResetClicked() {
        mExercise = (ExerciseView) mOriginalExerciseInstance.clone();

        mView.bindExerciseInstanceToView(mExercise, this);
    }

    @Override
    public void onExit(ExerciseView exerciseInstance) {
        mOnExerciseUpdatedCallback.exerciseUpdated(exerciseInstance);
    }

    @Override
    public void onSetSelected(ExerciseSetView selectedSet) {
        List<ExerciseSetView> sets =  mExercise.getExerciseSetView();
        sets.remove(selectedSet);

        for (int i = 0; i < sets.size(); i++) {
            ExerciseSetView currentSet = sets.get(i);
            currentSet.setOrderNumber(i+1);
        }

        mExercise.setExerciseSetViews(sets);

        mView.bindExerciseInstanceToView(mExercise, this);
    }
}
