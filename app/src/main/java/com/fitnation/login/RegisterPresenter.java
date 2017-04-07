package com.fitnation.login;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    public void showProgress() {
        mView.showProgress();
    }

    @Override
    public void showAuthError(AlertDialog.Builder errorDialog) {
        mView.showAuthError(errorDialog);
    }
}
