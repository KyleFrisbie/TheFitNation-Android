package com.fitnation.login;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.navigation.NavigationActivity;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathCallback;
import com.stormpath.sdk.StormpathConfiguration;
import com.stormpath.sdk.models.Account;
import com.stormpath.sdk.models.StormpathError;

public class LoginActivity extends BaseActivity {
    private final static String TAG = "LoginActivity";
    protected static int VIEW_CONTAINER = R.id.Login_FrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize strompath for temp solution to logout test in navigation screen test
        if(!Stormpath.isInitialized()) {
            StormpathConfiguration stormpathConfiguration = new StormpathConfiguration.Builder()
                    .baseUrl("https://zippy-sword.apps.stormpath.io/")
                    .build();
            Stormpath.init(this, stormpathConfiguration);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //check to see if stormpath already has the user logged in
        Stormpath.getAccount(new StormpathCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                Log.i(TAG, "account was already authenticated" + account.getEmail());
                launchMainActivity();
            }

            @Override
            public void onFailure(StormpathError error) {
                setContentView(R.layout.activity_login);
                launchLoginFragment();
            }
        });
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
