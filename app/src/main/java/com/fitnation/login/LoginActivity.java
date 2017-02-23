package com.fitnation.login;
import android.os.Bundle;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathConfiguration;

public class LoginActivity extends BaseActivity {
    protected static int VIEW_CONTAINER = R.id.Login_FrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initialize strompath
        if(!Stormpath.isInitialized()) {
            StormpathConfiguration stormpathConfiguration = new StormpathConfiguration.Builder()
                    .baseUrl("https://zippy-sword.apps.stormpath.io/")
                    .build();
            Stormpath.init(this, stormpathConfiguration);
        }

        setContentView(R.layout.activity_login);
        launchLoginFragment();
    }

    private void launchLoginFragment() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        loginFragment.setPresenter(new LoginPresenter(loginFragment));
        getSupportFragmentManager().beginTransaction()
                .replace(VIEW_CONTAINER, loginFragment).commit();
    }
}
