package com.fitnation.login;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;

import com.fitnation.managers.LoginManager;
import com.fitnation.managers.ManagerContract;

import static com.fitnation.login.LoginBaseActivity.VIEW_CONTAINER;

/**
 * Presenter for the login screen. contains all the login logic for the login screen
 */
public class LoginPresenter implements LoginContract.Presenter, ManagerContract.Presenter{
    private LoginContract.View mView;

    public LoginPresenter (LoginContract.View view) { mView = view; }


    @Override
    public void onLoginPressed(final String userName, final String password) {

        LoginManager loginManager = new LoginManager(mView.getBaseActivity(), this);
        loginManager.requestToken(userName, password);

    }

    // TODO: Implement facebook login
    @Override
    public void onFacebookLoginPressed() {    }

    // TODO: Implement google Login
    @Override
    public void onGoogleLoginPressed() {    }

    @Override
    public void onForgotLoginButtonPressed() {
        ResetLoginFragment forgotLoginFragment = ResetLoginFragment.newInstance();
        forgotLoginFragment.setPresenter(new ResetLoginPresenter(forgotLoginFragment));
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("login fragment")
                .replace(VIEW_CONTAINER, forgotLoginFragment).commit();
    }

    @Override
    public void onSignUpButtonPressed() {
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        registerFragment.setPresenter(new RegisterPresenter(registerFragment));
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("login fragment")
                .replace(VIEW_CONTAINER, registerFragment).commit();
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

    @Override
    public void showSuccess(AlertDialog.Builder successDialog) {

    }

    @Override
    public void showProgress(ProgressDialog progressDialog) {
        mView.showProgress(progressDialog);
    }

    @Override
    public void stopProgress() {
        mView.stopProgress();
    }

    @Override
    public void showAuthError(AlertDialog.Builder errorDialog) {
        mView.showAuthError(errorDialog);
    }
}
