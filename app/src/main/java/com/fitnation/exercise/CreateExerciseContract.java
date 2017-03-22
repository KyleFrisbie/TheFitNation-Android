package com.fitnation.exercise;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseAction;

import java.util.List;

/**
 * Created by Ryan on 3/21/2017.
 */

public interface CreateExerciseContract {
    interface View extends BaseView<Presenter> {
        void showProgress();
        void displayExercises(List<ExerciseInstance> exercises);
    }

    interface Presenter extends BasePresenter {
        void onActionButtonClicked(ExerciseAction action);
    }
}
