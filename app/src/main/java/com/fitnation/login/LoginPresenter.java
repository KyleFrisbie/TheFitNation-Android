package com.fitnation.login;

import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.navigation.NavigationActivity;
import com.stormpath.sdk.Provider;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathCallback;
import com.stormpath.sdk.models.Account;
import com.stormpath.sdk.models.StormpathError;

import static com.fitnation.login.LoginActivity.VIEW_CONTAINER;

/**
 * Created by Ryan on 1/31/2017.
 */

public class LoginPresenter implements LoginContract.Presenter{
    private final static String TAG = "LoginPresenter";
    private LoginContract.View mView;

    public LoginPresenter (LoginContract.View view) { mView = view; }

    @Override
    public void onFacebookLoginPressed() {

        Stormpath.loginWithProvider(Provider.FACEBOOK, mView.getBaseActivity(), loginCallback);

    }

    @Override
    public void onGoogleLoginPressed() {

        Stormpath.loginWithProvider(Provider.GOOGLE, mView.getBaseActivity(), loginCallback);

    }

    @Override
    public void onLoginPressed(final String userName, final String password) {
        //check to see if stormpath already has the user logged in
        Stormpath.getAccount(new StormpathCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                homeActivityIntent();
            }

            @Override
            public void onFailure(StormpathError error) {
                Stormpath.login(userName, password, loginCallback);
            }
        });
    }

    @Override
    public void onSignUpButtonPressed() {
        RegisterFragment registerFragment = RegisterFragment.newInstance();
        registerFragment.setPresenter(new RegisterPresenter(registerFragment));
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("login")
                .replace(VIEW_CONTAINER, registerFragment).commit();
    }

    @Override
    public void onForgotLoginButtonPressed() {
        ForgotLoginFragment forgotLoginFragment = ForgotLoginFragment.newInstance();
        forgotLoginFragment.setPresenter(new ForgotLoginPresenter(forgotLoginFragment));
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack("login")
                .replace(VIEW_CONTAINER, forgotLoginFragment).commit();
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

    private void homeActivityIntent(){
        mView.getBaseActivity().startActivity(new Intent(mView.getBaseActivity(), NavigationActivity.class));
        mView.getBaseActivity().finish();
    }

    private StormpathCallback<Void> loginCallback = new StormpathCallback<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            homeActivityIntent();
        }

        @Override
        public void onFailure(StormpathError error) {

        }
    };

    private void returnAuthError(){
        String AuthError;
        mView.showAuthError();
    }
}
