package com.fitnation.login;

import android.content.Intent;

import com.fitnation.base.BaseActivity;
import com.fitnation.navigation.NavigationActivity;

/**
 * Created by Ryan on 1/31/2017.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;

    public LoginPresenter (LoginContract.View view) {
        mView = view;
    }

    @Override
    public void onFacebookLoginPressed() {
        BaseActivity baseActivity = mView.getBaseActivity();
        Intent launchMain = new Intent(baseActivity, NavigationActivity.class);

        mView.getBaseActivity().startActivity(launchMain);
    }

    @Override
    public void onGoogleSignInPressed() {

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
