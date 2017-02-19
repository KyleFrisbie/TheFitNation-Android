package com.fitnation.login;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.appcompat.BuildConfig;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathConfiguration;
import com.stormpath.sdk.StormpathLogger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fitnation.login.LoginActivity.VIEW_CONTAINER;

/**
 * View for login
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {
    private LoginContract.Presenter mPresenter;

    @BindView(R.id.login_button) public Button mLoginButton;
    @BindView(R.id.google_login_button) public Button mGoogleLoginButton;
    @BindView(R.id.facebook_login_button) public Button mFacebookLoginButton;
    @BindView(R.id.username_editText) public EditText mUsernameEditText;
    @BindView(R.id.password_editText) public EditText mPasswordEditText;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @OnClick(R.id.facebook_login_button)
    public void onFacebookLoginButtonClicked() {
        mPresenter.onFacebookLoginPressed();
    }

    @OnClick(R.id.google_login_button)
    public void onGoogleLoginButtonClicked() { mPresenter.onGoogleSignInPressed();}

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        String userName = mUsernameEditText.getText().toString();
        String password = mPasswordEditText.getText().toString();
        mPresenter.onLoginPressed(userName, password);
    }

    @OnClick(R.id.register_button)
    public void onRegisterButtonClicked() {
        mPresenter.onRegisterButtonPressed();
        }

    @Override
    public void showProgress() {

    }

    @Override
    public void showAuthError() {

    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
