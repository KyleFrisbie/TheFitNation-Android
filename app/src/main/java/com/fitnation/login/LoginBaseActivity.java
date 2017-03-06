package com.fitnation.login;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.navigation.NavigationActivity;

public class LoginBaseActivity extends BaseActivity {
    private final static String TAG = "LoginBaseActivity";
    protected static int VIEW_CONTAINER = R.id.Login_FrameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        launchLoginFragment();
    }

    private void launchLoginFragment() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        loginFragment.setPresenter(new LoginPresenter(loginFragment));
        getSupportFragmentManager().beginTransaction()
                .replace(VIEW_CONTAINER, loginFragment).commit();
    }

    private void launchMainActivity() {
        Intent launchMain = new Intent(this, NavigationActivity.class);
        startActivity(launchMain);
    }
}
