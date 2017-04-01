package com.fitnation.exercise.edit;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * Contract for viewing an exercise
 */
public interface ViewExerciseContract {
    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {
        void onAddSetClicked();
        void onSaveClicked();
        void onResetClicked();
        void onExit();
    }
}
