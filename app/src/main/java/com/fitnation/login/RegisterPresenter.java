package com.fitnation.login;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;

<<<<<<< HEAD
import com.fitnation.networking.tasks.loginTasks.TaskCallback;
import com.fitnation.networking.tasks.loginTasks.RegisterUserTask;
=======
import com.android.volley.toolbox.StringRequest;
import com.fitnation.networking.tasks.TaskCallback;
import com.fitnation.networking.tasks.RegisterUserTask;
>>>>>>> refs/remotes/origin/master

/**
 * Presenter for register screen that contains all the business logic associated with the screen
 */
public class RegisterPresenter implements RegisterContract.Presenter, TaskCallback.Presenter{
    private RegisterContract.View mView;

    public RegisterPresenter (RegisterContract.View view){ mView = view; }

    @Override
    public void onRegisterCreatePressed(final String email, final String password,
                                        final String userName, final String firstName,
                                        String lastName, final String language) {
        RegisterUserTask registerUserTask = new RegisterUserTask(mView.getBaseActivity(), this);
        registerUserTask.requestRegistration(email, password, userName, firstName, lastName, language);
    }

    /*--------------------------------------RegisterContract--------------------------------------*/

    @Override
    public void onViewReady() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    /*----------------------------------------TaskCallback----------------------------------------*/

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
