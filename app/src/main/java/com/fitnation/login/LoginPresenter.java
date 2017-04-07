package com.fitnation.login;

import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fitnation.Factory.VolleyErrorMessage;
import com.fitnation.base.BaseActivity;
import com.fitnation.navigation.NavigationActivity;
import com.fitnation.networking.AuthToken;
import com.fitnation.utils.EnvironmentManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    public void showProgress() {
        mView.showProgress();
    }

    @Override
    public void showAuthError(AlertDialog.Builder errorDialog) {
        mView.showAuthError(errorDialog);
    }
}
