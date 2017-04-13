package com.fitnation.login;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;

/**
 * An interface between the view and the presenter
 */
public interface RegisterContract {
    interface View extends BaseView<Presenter> {
        void showSuccess(AlertDialog.Builder successDialog);
        void showProgress(ProgressDialog progressDialog);
        void stopProgress();
        void showAuthError(AlertDialog.Builder errorDialog);
    }

    interface Presenter extends BasePresenter {
        void onRegisterCreatePressed(String email, String password, String userName, String language);
    }
}
