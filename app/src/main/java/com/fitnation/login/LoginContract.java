package com.fitnation.login;

import android.support.v7.app.AlertDialog;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * Interface between view and presenter
 */
public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showSuccess();
        void showProgress();
        void showAuthError(AlertDialog.Builder errorDialog);
    }

    interface Presenter extends BasePresenter {
        void onFacebookLoginPressed();
        void onGoogleLoginPressed();
        void onForgotLoginButtonPressed();
        void onLoginPressed(String userName, String password);
        void onSignUpButtonPressed();
    }
}
