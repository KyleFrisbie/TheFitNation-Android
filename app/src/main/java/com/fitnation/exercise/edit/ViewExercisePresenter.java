package com.fitnation.exercise.edit;

import android.util.Log;

import com.fitnation.exercise.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.ExerciseInstanceSet;

import io.realm.RealmList;

/**
 * Presenter for viewing an exercise
 */
public class ViewExercisePresenter implements ViewExerciseContract.Presenter {
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

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onAddSetClicked() {
        //TODO add a set to the exercise instance
        RealmList<ExerciseInstanceSet> sets =  mExercise.getExerciseInstanceSets();
        int orderNumber = sets.size() + 1;
        sets.add(new ExerciseInstanceSet(mExercise, orderNumber));
        mView.bindExerciseInstanceToView(mExercise);
    }

    @Override
    public void onSaveClicked(ExerciseInstance exerciseInstance) {
        mOnExerciseUpdatedCallback.exerciseUpdated(exerciseInstance);
        mView.getBaseActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onResetClicked() {
        mExercise = (ExerciseInstance) mOriginalExerciseInstance.clone();

        mView.bindExerciseInstanceToView(mExercise);
    }

    @Override
    public void onExit(ExerciseInstance exerciseInstance) {
        mOnExerciseUpdatedCallback.exerciseUpdated(exerciseInstance);
        mView.getBaseActivity().getSupportFragmentManager().popBackStack();
    }

}
