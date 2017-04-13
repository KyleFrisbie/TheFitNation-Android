package com.fitnation.intro;

import android.content.Intent;
import android.os.Bundle;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.login.LoginBaseActivity;

/**
 * SplashScreen for branding and app loading
 */
public class SplashScreenActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
        launchLoginScreen();
    }

    private void launchLoginScreen() {
        final Intent loginIntent = new Intent(this, LoginBaseActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
