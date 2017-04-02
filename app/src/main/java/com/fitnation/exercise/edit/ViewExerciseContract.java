package com.fitnation.exercise.edit;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.ExerciseInstance;

/**
 * Contract for viewing an exercise
 */
public interface ViewExerciseContract {
    interface View extends BaseView<Presenter> {
        void bindExerciseInstanceToView(ExerciseInstance exerciseInstance);
    }

    interface Presenter extends BasePresenter {
        void onAddSetClicked();
        void onSaveClicked(ExerciseInstance mExerciseInstance);
        void onResetClicked();
        void onExit(ExerciseInstance exerciseInstance);
    }
}
