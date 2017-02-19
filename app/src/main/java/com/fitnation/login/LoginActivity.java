package com.fitnation.login;
import android.os.Bundle;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    protected static int VIEW_CONTAINER = R.id.activity_login;

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
}
