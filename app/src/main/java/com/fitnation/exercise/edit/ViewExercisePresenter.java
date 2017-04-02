package com.fitnation.exercise.edit;

import com.fitnation.exercise.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.ExerciseInstanceSet;

import io.realm.RealmList;

/**
 * Presenter for viewing an exercise
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
        //TODO add a set to the exercise instance
        RealmList<ExerciseInstanceSet> sets =  mExercise.getExerciseInstanceSets();
        int orderNumber = sets.size() + 1;
        sets.add(new ExerciseInstanceSet(mExercise, orderNumber));
        //update the view
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
