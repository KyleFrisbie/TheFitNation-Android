package com.fitnation.intro;

import android.content.Intent;
import android.os.Bundle;

<<<<<<< HEAD
import com.fitnation.base.BaseActivity;
import com.fitnation.login.LoginBaseActivity;
=======
import com.crashlytics.android.Crashlytics;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.login.LoginActivity;
import io.fabric.sdk.android.Fabric;
>>>>>>> refs/remotes/origin/master

/**
 * SplashScreen for branding
 */
public class SplashScreenActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
<<<<<<< HEAD
        final Intent loginIntent = new Intent(this, LoginBaseActivity.class);
        startActivity(loginIntent);
        finish();
=======
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash_screen);
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
>>>>>>> refs/remotes/origin/master
    }
}
