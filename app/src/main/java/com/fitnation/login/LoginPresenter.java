package com.fitnation.login;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.fitnation.base.BaseActivity;
import com.fitnation.navigation.NavigationActivity;
import com.stormpath.sdk.Provider;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathCallback;
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
        //unworking code section...has something to do with not being able to use startActivity
        /*
        Stormpath.loginWithProvider(Provider.FACEBOOK, this, new StormpathCallback<Void>() {

            @Override
            public void onSuccess(Void aVoid) {
                BaseActivity baseActivity = mView.getBaseActivity();
                Intent launchMain = new Intent(baseActivity, NavigationActivity.class);
                launchMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                mView.getBaseActivity().startActivity(launchMain);
            }

            @Override
            public void onFailure(StormpathError error) {
                // Handle login error
            }
        });
        */
    }

    @Override
    public void onGoogleLoginPressed() {
        //unworking code section...has something to do with not being able to use startActivity

        Stormpath.loginWithProvider(Provider.GOOGLE, mView.getBaseActivity(), new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                String token = Stormpath.getAccessToken();
                Log.i(TAG, token);
            }

            @Override
            public void onFailure(StormpathError error) {
                String TAG = "hello";
                Log.i(TAG, "onFailure: ");

            }
        });

    }

    @Override
    public void onLoginPressed(final String userName, final String password) {
        Stormpath.login(userName, password, new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                String token = Stormpath.getAccessToken();
                BaseActivity baseActivity = mView.getBaseActivity();
                Intent launchMain = new Intent(baseActivity, NavigationActivity.class);
                launchMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                launchMain.putExtra("Token", token);

                mView.getBaseActivity().startActivity(launchMain);
            }

            @Override
            public void onFailure(StormpathError error) {
                Toast loginFailedToast = Toast.makeText(mView.getBaseActivity(),
                        "UserName or Password is incorrect" + userName + " " + password, Toast.LENGTH_SHORT);
                loginFailedToast.show();
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
}
