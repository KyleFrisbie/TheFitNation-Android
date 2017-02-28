package com.fitnation.login;

import android.content.Intent;
import android.widget.Toast;

import com.fitnation.base.BaseActivity;
import com.fitnation.navigation.NavigationActivity;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathCallback;
import com.stormpath.sdk.models.RegistrationForm;
import com.stormpath.sdk.models.StormpathError;

/**
 * Created by Erik on 2/18/2017.
 */

public class ForgotLoginPresenter implements ForgotLoginContract.Presenter {
    private ForgotLoginContract.View mView;

    public ForgotLoginPresenter(ForgotLoginContract.View view) { mView = view; }

    @Override
    public void onViewReady() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onResetPasswordButtonPressed(String email) {
        Stormpath.resetPassword(email, new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // success!
                Toast loginFailedToast = Toast.makeText(mView.getBaseActivity(),
                        "Email sent!", Toast.LENGTH_SHORT);
                loginFailedToast.show();
                Intent loginIntent = new Intent(mView.getBaseActivity(), LoginActivity.class);
                mView.getBaseActivity().startActivity(loginIntent);
            }

            @Override
            public void onFailure(StormpathError error) {
                // something went wrong
                Toast loginFailedToast = Toast.makeText(mView.getBaseActivity(),
                        "Email failed to send.", Toast.LENGTH_SHORT);
                loginFailedToast.show();
            }
        });
    }
}
