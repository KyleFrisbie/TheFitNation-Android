package com.fitnation.exercise.parent;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseAction;

import java.util.List;

/**
 * Contract for displaying Exercises for MVP
 */
public interface ExercisesParentContract {
    interface View extends BaseView<Presenter> {
        void showProgress();
        void stopProgress();
        void displayExercises(List<ExerciseInstance> exercisesTabOne,List<ExerciseInstance> exercisesTabTwo, List<ExerciseInstance> exercisesTabThree);
        void displayUpdatedExercises(List<ExerciseInstance> exerciseListTab1, List<ExerciseInstance> exerciseListTab2, List<ExerciseInstance> exerciseListTab3);
        void promptUserToSave(DialogFragment alertDialog);
        int getSelectedTab();
        void showSuccess(AlertDialog alertDialog);
        void showFailure(AlertDialog alertDialog);
    }

    interface Presenter extends BasePresenter {
        void onActionButtonClicked(ExerciseAction action);
        void onExerciseSelected(ExerciseInstance exerciseInstance, boolean isSelected);

        void onEditPressed(ExerciseInstance exercise);
    }
}
