package com.fitnation.login;

/**
 * Created by Ryan on 1/31/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    public LoginPresenter (LoginContract.View view) {
        mView = view;
    }

    @Override
    public void onFacebookLoginPressed() {

    }

    @Override
    public void onGoogleSignInPressed() {

    }

    @Override
    public void onViewReady() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
