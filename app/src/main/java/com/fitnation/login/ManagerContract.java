package com.fitnation.login;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;

import com.fitnation.base.BaseActivity;
import com.fitnation.base.BasePresenter;

/**
 * Created by Erik on 4/7/2017.
 */

public interface ManagerContract {
    interface Presenter extends BasePresenter {
        void showSuccess(AlertDialog.Builder successDialog);
        void showProgress(ProgressDialog progressDialog);
        void stopProgress();
        void showAuthError(AlertDialog.Builder errorDialog);
    }
}
