package com.fitnation.workout.exercise;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserExerciseInstance;
import com.fitnation.workout.callbacks.OnSetSelectedCallback;
import com.fitnation.model.ExerciseInstance;

/**
 * Contract for viewing an exercise
 */
public interface ViewExerciseContract {
    interface View extends BaseView<Presenter> {
        void bindExerciseInstanceToView(ExerciseView exerciseInstance, OnSetSelectedCallback callback);
    }

    interface Presenter extends BasePresenter {
        void onAddSetClicked();
        void onSaveClicked(ExerciseView mExerciseInstance);
        void onResetClicked();
        void onExit(ExerciseView exerciseInstance);
    }
}
