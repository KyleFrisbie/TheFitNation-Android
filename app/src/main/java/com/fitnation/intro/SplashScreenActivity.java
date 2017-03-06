package com.fitnation.intro;

import android.content.Intent;
import android.os.Bundle;

import com.fitnation.base.BaseActivity;
import com.fitnation.login.LoginBaseActivity;

/**
 * SplashScreen for branding
 */
public class SplashScreenActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent loginIntent = new Intent(this, LoginBaseActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
