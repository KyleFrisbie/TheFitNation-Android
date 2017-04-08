package com.fitnation.login;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;

import com.fitnation.managers.ManagerContract;
import com.fitnation.managers.RegisterManager;

/**
 * Presenter for register screen that contains all the business logic associated with the screen
 */
public class RegisterPresenter implements RegisterContract.Presenter, ManagerContract.Presenter{
    private RegisterContract.View mView;

    public RegisterPresenter (RegisterContract.View view){ mView = view; }

    @Override
    public void onRegisterCreatePressed(final String email, final String password, final String userName,
                                        final String language) {
        RegisterManager registerManager = new RegisterManager(mView.getBaseActivity(), this);
        registerManager.requestRegistration(email, password, userName, language);

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
        mView.showSuccess(successDialog);
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
