package com.fitnation.login;

import android.os.Bundle;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;

import io.fabric.sdk.android.Fabric;

/**
 * The base login activity to hold all of the fragments
 */
public class LoginBaseActivity extends BaseActivity {
    public static final int VIEW_CONTAINER = R.id.activity_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_base_activity);
        launchLoginFragment();
        if (!Fabric.isInitialized()) {
            Fabric.with(this, new Crashlytics());
        }
    }

    /**
     * Launches the login fragment with a fragment transaction
     */
    private void launchLoginFragment() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        loginFragment.setPresenter(new LoginPresenter(loginFragment));
        getSupportFragmentManager().beginTransaction()
                .replace(VIEW_CONTAINER, loginFragment).commit();
    }
}
