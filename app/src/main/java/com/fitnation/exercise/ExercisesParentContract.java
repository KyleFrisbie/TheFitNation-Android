package com.fitnation.exercise;

import android.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseAction;

import java.util.List;

/**
 * Created by Ryan on 3/21/2017.
 */

public interface ExercisesParentContract {
    interface View extends BaseView<Presenter> {
        void showProgress();
        void displayExercises(List<ExerciseInstance> exercisesTabOne,List<ExerciseInstance> exercisesTabTwo, List<ExerciseInstance> exercisesTabThree);
        void displayUpdatedExercises(List<ExerciseInstance> updatedExercises);
        void promptUserToSave(DialogFragment alertDialog);
        int getSelectedTab();
    }

    interface Presenter extends BasePresenter {
        void onActionButtonClicked(ExerciseAction action);
        void onExerciseSelected(ExerciseInstance exerciseInstance, boolean isSelected);

        void onEditPressed(ExerciseInstance exercise);
    }
}
