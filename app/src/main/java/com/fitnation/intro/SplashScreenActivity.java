package com.fitnation.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Interpolator;

import com.crashlytics.android.Crashlytics;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.login.LoginActivity;
import io.fabric.sdk.android.Fabric;

/**
 * SplashScreen for branding
 */
public class SplashScreenActivity extends BaseActivity {
    private static final int SPLASH_SCREEN_DELAY = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.login_activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();
        launchLoginScreen();
    }

    private void launchLoginScreen() {
        final Intent loginIntent = new Intent(this, LoginActivity.class);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(loginIntent);
            }
        }, SPLASH_SCREEN_DELAY);
    }
}
