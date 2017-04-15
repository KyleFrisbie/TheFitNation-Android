package com.fitnation.workout.exercise;

import android.util.Log;

import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.workout.callbacks.OnSetSelectedCallback;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.ExerciseInstanceSet;

import io.realm.RealmList;

/**
 * Presenter for viewing an exercise
 */
public class ViewExercisePresenter implements ViewExerciseContract.Presenter, OnSetSelectedCallback{
    private static final String TAG = ViewExercisePresenter.class.getSimpleName();
    private ExerciseInstance mExercise;
    private ViewExerciseContract.View mView;
    private OnExerciseUpdatedCallback mOnExerciseUpdatedCallback;
    private ExerciseInstance mOriginalExerciseInstance;


    public ViewExercisePresenter(ExerciseInstance exercise, ViewExerciseContract.View view, OnExerciseUpdatedCallback onExerciseUpdatedCallback) {
        mExercise = exercise;
        mView = view;
        mOnExerciseUpdatedCallback = onExerciseUpdatedCallback;
        mOriginalExerciseInstance = (ExerciseInstance) exercise.clone();
    }

    @Override
    public void onViewReady() {
        mView.bindExerciseInstanceToView(mExercise, this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onAddSetClicked() {
        RealmList<ExerciseInstanceSet> sets =  mExercise.getExerciseInstanceSets();
        int orderNumber = sets.size() + 1;
        sets.add(new ExerciseInstanceSet(mExercise, orderNumber));
        mView.bindExerciseInstanceToView(mExercise, this);
    }

    @Override
    public void onSaveClicked(ExerciseInstance exerciseInstance) {
        mOnExerciseUpdatedCallback.exerciseUpdated(exerciseInstance);
        mView.getBaseActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onResetClicked() {
        mExercise = (ExerciseInstance) mOriginalExerciseInstance.clone();

        mView.bindExerciseInstanceToView(mExercise, this);
    }

    @Override
    public void onExit(ExerciseInstance exerciseInstance) {
        mOnExerciseUpdatedCallback.exerciseUpdated(exerciseInstance);
        try {
            mView.getBaseActivity().getSupportFragmentManager().popBackStack();
        } catch (IllegalStateException ise) {
            Log.e(TAG, "Error onExit(): " + ise.getMessage());
        }
    }

    @Override
    public void onSetSelected(ExerciseInstanceSet selectedSet) {
        RealmList<ExerciseInstanceSet> sets =  mExercise.getExerciseInstanceSets();
        sets.remove(selectedSet);

        for (int i = 0; i < sets.size(); i++) {
            ExerciseInstanceSet currentSet = sets.get(i);
            currentSet.setOrderNumber(i+1);
        }

        mView.bindExerciseInstanceToView(mExercise, this);
    }
}
