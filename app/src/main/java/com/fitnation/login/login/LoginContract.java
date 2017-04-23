package com.fitnation.login.login;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * Interface between view and presenter
 */
public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSuccess();
        void showSuccess(AlertDialog.Builder successDialog);
        void showProgress(ProgressDialog progressDialog);
        void stopProgress();
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
