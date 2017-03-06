<<<<<<< HEAD:app/src/main/java/com/fitnation/login/LoginBaseActivity.java
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
=======
package com.fitnation.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.ContentViewEvent;
import com.crashlytics.android.answers.CustomEvent;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;

public class LoginActivity extends BaseActivity {
    private static final int VIEW_CONTAINER = R.id.activity_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        launchLoginFragment();
        onKeyMetric();

        // TODO: Use your own attributes to track content views in your app
        Answers.getInstance().logContentView(new ContentViewEvent()
                .putContentName("Workout")
                .putContentType("Video")
                .putContentId("1234")
                .putCustomAttribute("Favorites Count", 20)
                .putCustomAttribute("Screen Orientation", "Landscape"));

    }

    private void launchLoginFragment() {
        LoginFragment loginFragment = LoginFragment.newInstance();
        loginFragment.setPresenter(new LoginPresenter(loginFragment));
        getSupportFragmentManager().beginTransaction()
                .replace(VIEW_CONTAINER, loginFragment).commit();
    }

    // TODO: Move this method and use your own event name to track your key metrics
    public void onKeyMetric() {
        // TODO: Use your own string attributes to track common values over time
        // TODO: Use your own number attributes to track median value over time
        Answers.getInstance().logCustom(new CustomEvent("Login Screen")
                .putCustomAttribute("Category", "Activity")
                .putCustomAttribute("Length", 350));
    }


}
>>>>>>> refs/remotes/origin/master:app/src/main/java/com/fitnation/login/LoginActivity.java
