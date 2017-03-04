package com.fitnation.login;

import android.content.Intent;

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

public class SocialLoginPresenter implements SocialLoginContract.Presenter{
    private final static String TAG = "LoginPresenter";
    private SocialLoginContract.View mView;

    public SocialLoginPresenter(SocialLoginContract.View view) { mView = view; }

    @Override
    public void onFacebookLoginPressed() {
        Stormpath.loginWithProvider(Provider.FACEBOOK, mView.getBaseActivity(), new StormpathCallback<Void>() {

            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(StormpathError error) {
                // Handle login error
                returnAuthError();
            }
        });
    }

    @Override
    public void onGoogleLoginPressed() {
        Stormpath.loginWithProvider(Provider.GOOGLE, mView.getBaseActivity(), new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(StormpathError error) {
                returnAuthError();

            }
        });
    }

    @Override
    public void onGitHubLoginPressed() {
        Stormpath.loginWithProvider(Provider.GITHUB, mView.getBaseActivity(), new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(StormpathError error) {
                returnAuthError();

            }
        });
    }

    @Override
    public void onTwitterLoginPressed() {
        Stormpath.loginWithProvider(Provider.TWITTER, mView.getBaseActivity(), new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }

            @Override
            public void onFailure(StormpathError error) {
                returnAuthError();

            }
        });
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

    private void returnAuthError(){
        //set error to a blank return until error string generating class is made
        String errorMessage = "error occured";
        mView.showAuthError(errorMessage);
    }
}
