package com.fitnation.login;

import android.content.Intent;
import android.widget.Toast;
import com.fitnation.base.BaseActivity;
import com.fitnation.navigation.NavigationActivity;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathCallback;
import com.stormpath.sdk.models.RegistrationForm;
import com.stormpath.sdk.models.StormpathError;

import static com.fitnation.login.LoginActivity.VIEW_CONTAINER;

/**
 * Created by Erik on 2/18/2017.
 */

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;

    public RegisterPresenter (RegisterContract.View view){ mView = view; }

    @Override
    public void onRegisterCreatePressed(final String email, final String password) {

        //register is always displaying error even when success has occured
        RegistrationForm registrationForm = new RegistrationForm(email, password);

        Stormpath.register(registrationForm, new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // Handle successful registration
                Stormpath.getAccessToken();
                BaseActivity baseActivity = mView.getBaseActivity();
                Intent launchMain = new Intent(baseActivity, NavigationActivity.class);
                launchMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                mView.getBaseActivity().startActivity(launchMain);
            }

            @Override
            public void onFailure(StormpathError error) {
                Stormpath.login(email, password, new StormpathCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle successful login
                        Stormpath.getAccessToken();
                        BaseActivity baseActivity = mView.getBaseActivity();
                        Intent launchMain = new Intent(baseActivity, NavigationActivity.class);
                        launchMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        mView.getBaseActivity().startActivity(launchMain);
                    }

                    @Override
                    public void onFailure(StormpathError error) {
                        // Handle login error
                        Toast loginFailedToast = Toast.makeText(mView.getBaseActivity(),
                                "UserName or Password is incomparable :" + email + " " + password, Toast.LENGTH_SHORT);
                        loginFailedToast.show();
                    }
                });
            }
        });
    }

    @Override
    public void onViewReady() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
