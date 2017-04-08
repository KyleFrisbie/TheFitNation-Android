package com.fitnation.networking.tasks;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;

import com.fitnation.base.BasePresenter;

/**
 * Common interface for all managers to return information to presenters.
 */

public interface TaskContract {
    interface Presenter extends BasePresenter {
        void showSuccess(AlertDialog.Builder successDialog);
        void showProgress(ProgressDialog progressDialog);
        void stopProgress();
        void showAuthError(AlertDialog.Builder errorDialog);
    }
}
