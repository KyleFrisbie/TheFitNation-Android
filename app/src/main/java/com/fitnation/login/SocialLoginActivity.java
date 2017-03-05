package com.fitnation.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.navigation.NavigationActivity;
import com.stormpath.sdk.Provider;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathCallback;
import com.stormpath.sdk.StormpathConfiguration;
import com.stormpath.sdk.models.StormpathError;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Erik on 3/4/2017.
 */

public class SocialLoginActivity extends BaseActivity{
    private final static String TAG = "SocialLoginActivity";

    private StormpathCallback<Void> loginCallback = new StormpathCallback<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            navigateToHome();
        }

        @Override
        public void onFailure(StormpathError error) {
            new AlertDialog.Builder(SocialLoginActivity.this)
                    .setTitle("Error")
                    .setMessage(error.message())
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_login);

        initializeStormpath();
        ButterKnife.bind(this);
    }

    @OnClick(R.id.google_login_button)
    public void onGoogleLoginPressed() {
        Stormpath.loginWithProvider(Provider.GOOGLE, this, loginCallback);
    }

    @OnClick(R.id.facebook_login_button)
    public void onFacebookLoginPressed() {
        Stormpath.loginWithProvider(Provider.FACEBOOK, this, loginCallback);
    }

    public void initializeStormpath(){
        //initialize strompath for temp solution to logout test in navigation screen test
        if(!Stormpath.isInitialized()) {
            StormpathConfiguration stormpathConfiguration = new StormpathConfiguration.Builder()
                    .baseUrl("https://zippy-sword.apps.stormpath.io/")
                    .build();
            Stormpath.init(this, stormpathConfiguration);
        }
    }

    private void navigateToHome() {
        startActivity(new Intent(this, NavigationActivity.class));
        finish();
    }

}
