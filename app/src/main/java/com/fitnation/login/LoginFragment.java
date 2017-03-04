package com.fitnation.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.navigation.NavigationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
     * on facebook button pressed will launch the stormpath social login for facebook
     */
    @OnClick(R.id.facebook_login_button)
    public void onFacebookLoginButtonClicked() {
        mPresenter.onFacebookLoginPressed();
    }


    @OnClick(R.id.google_login_button)
    public void onGoogleLoginButtonClicked() { mPresenter.onGoogleLoginPressed();}

    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        mPresenter.onLoginPressed(mUsernameEditText.getText().toString(),
                                  mPasswordEditText.getText().toString());
    }

    @OnClick(R.id.signUp_button)
    public void onSignUpButtonClicked() { mPresenter.onSignUpButtonPressed(); }

    @OnClick(R.id.forgot_login_button)
    public void onForgotLoginButtonClicked() { mPresenter.onForgotLoginButtonPressed(); }

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
