package com.fitnation.login;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * interface between the view and presenter
 */
public interface ResetLoginContract {
    interface View extends BaseView<Presenter> {
        void showProgress(String message);
        void showAuthError(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        void onResetPasswordButtonPressed(String email);
    }
}
