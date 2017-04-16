package com.fitnation.workout.exercise;

import android.util.Log;

import com.fitnation.model.ExerciseSetView;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserExerciseInstanceSet;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.workout.callbacks.OnSetSelectedCallback;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.ExerciseInstanceSet;

import java.util.List;

/**
 * Presenter for viewing an exercise
 */
public class ViewExercisePresenter implements ViewExerciseContract.Presenter, OnSetSelectedCallback{
    private static final String TAG = ViewExercisePresenter.class.getSimpleName();
    private ExerciseView mExercise;
    private ViewExerciseContract.View mView;
    private OnExerciseUpdatedCallback mOnExerciseUpdatedCallback;
    private ExerciseInstance mOriginalExerciseInstance;
    private ExerciseType mExerciseType;


    public ViewExercisePresenter(ExerciseView exercise, ExerciseType exerciseType, ViewExerciseContract.View view, OnExerciseUpdatedCallback onExerciseUpdatedCallback) {
        mExercise = exercise;
        mExerciseType = exerciseType;
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
        List<ExerciseSetView> sets =  mExercise.getExerciseSetView();
        int orderNumber = sets.size() + 1;
        if(mExerciseType.equals(ExerciseType.TEMPLATE)) {
            sets.add(new ExerciseInstanceSet((ExerciseInstance)mExercise, orderNumber));
        } else {
            sets.add(new UserExerciseInstanceSet(orderNumber));
        }
        mExercise.setExerciseSetViews(sets);

        mView.bindExerciseInstanceToView(mExercise, this);
    }

    @Override
    public void onSaveClicked(ExerciseView exerciseInstance) {
        mOnExerciseUpdatedCallback.exerciseUpdated(exerciseInstance);
        mView.getBaseActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onResetClicked() {
        mExercise = (ExerciseInstance) mOriginalExerciseInstance.clone();

        mView.bindExerciseInstanceToView(mExercise, this);
    }

    @Override
    public void onExit(ExerciseView exerciseInstance) {
        mOnExerciseUpdatedCallback.exerciseUpdated(exerciseInstance);
        try {
            mView.getBaseActivity().getSupportFragmentManager().popBackStack();
        } catch (IllegalStateException ise) {
            Log.e(TAG, "Error onExit(): " + ise.getMessage());
        }
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
