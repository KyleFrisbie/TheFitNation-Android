package com.fitnation.login;


import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.VolleyErrorMessage;
import com.fitnation.utils.EnvironmentManager;

import java.util.HashMap;
import java.util.Map;

/**
 * contains the business logic for the view
 */
public class ResetLoginPresenter implements ResetLoginContract.Presenter, ManagerContract.Presenter{
    private ResetLoginContract.View mView;

    public ResetLoginPresenter(ResetLoginContract.View view) { mView = view; }

    @Override
    public void onResetPasswordButtonPressed(final String email) {

        ResetLoginManager resetLoginManager = new ResetLoginManager(mView.getBaseActivity(), this);
        resetLoginManager.resetPasswordRequest(email);

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
