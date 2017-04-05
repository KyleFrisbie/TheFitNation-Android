package com.fitnation.login;

import android.app.AlertDialog;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * interface between the view and presenter
 */
public interface ResetLoginContract {
    interface View extends BaseView<Presenter> {
        void showSuccess();
        void showProgress(String message);
        void showAuthError(android.support.v7.app.AlertDialog.Builder errorDialog);
    }

    interface Presenter extends BasePresenter {
        void onResetPasswordButtonPressed(String email);
    }
}
