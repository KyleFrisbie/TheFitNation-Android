package com.fitnation.login;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * Created by Ryan on 1/31/2017.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void showProgress();
        void showAuthError();
    }

    interface Presenter extends BasePresenter {
        void onFacebookLoginPressed();
        void onGoogleLoginPressed();
        void onForgotLoginButtonPressed();
        void onLoginPressed(String userName, String password);
        void onSignUpButtonPressed();
    }
}
