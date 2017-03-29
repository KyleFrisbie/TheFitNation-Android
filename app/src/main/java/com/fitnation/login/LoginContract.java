package com.fitnation.login;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * Interface between view and presenter
 */
public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showProgress(String message);
        void showAuthError(String errorMessage);
    }

    interface Presenter extends BasePresenter {
        void onFacebookLoginPressed();
        void onGoogleLoginPressed();
        void onForgotLoginButtonPressed();
        void onLoginPressed(String userName, String password);
        void onSignUpButtonPressed();
    }
}
