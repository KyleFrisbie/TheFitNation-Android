package com.fitnation.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Login Fragement with view properties only. Sends data to the presentor class for buisness logic.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {
    private LoginContract.Presenter mPresenter;

    @BindView(R.id.email_editText) public EditText mUsernameEditText;
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

    /**
     * on facebook button pressed will launch no nothing so far
     */
    @OnClick(R.id.facebook_login_button)
    public void onFacebookLoginButtonClicked() {
        //empty methods that do nothing yet
        mPresenter.onFacebookLoginPressed();
    }

    /**
     * On google button pressed will do nothing
     */
    @OnClick(R.id.google_login_button)
    public void onGoogleLoginButtonClicked() { mPresenter.onGoogleLoginPressed();}

    /**
     * ON login button will take text in edit texts and pass them for server token request.
     */
    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        mPresenter.onLoginPressed(mUsernameEditText.getText().toString(),
                                  mPasswordEditText.getText().toString());
    }

    /**
     * On Sign up Button pressed launches the register fragment.
     */
    @OnClick(R.id.signUp_button)
    public void onSignUpButtonClicked() { mPresenter.onSignUpButtonPressed(); }

    /**
     * on Forgot login button pressed will launch the reset password by email fragment flow.
     */
    @OnClick(R.id.forgot_login_button)
    public void onForgotLoginButtonClicked() { mPresenter.onForgotLoginButtonPressed(); }

    @Override
    public void showProgress(String message) {
    }

    @Override
    public void showAuthError(String errorMessage) {
        Snackbar progressSnackBar = Snackbar.make(getBaseActivity().findViewById(R.id.activity_login),
                errorMessage, BaseTransientBottomBar.LENGTH_SHORT);
        progressSnackBar.show();
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
