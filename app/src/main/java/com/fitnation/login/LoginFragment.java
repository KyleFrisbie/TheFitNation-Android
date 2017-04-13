package com.fitnation.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.navigation.NavigationActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Login Fragment with view properties only. Sends data to the presenter class for business logic.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View {
    private LoginContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;

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
        View v = inflater.inflate(R.layout.login_login_fragment, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.login_background_imageView);
        Picasso.with(getBaseActivity()).load(R.drawable.login_background_1).into(imageView);
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
    public void onGoogleLoginButtonClicked() { mPresenter.onGoogleLoginPressed();}

    /**
     * On login button will take text in edit texts and pass them for server token request.
     */
    @OnClick(R.id.login_button)
    public void onLoginButtonClicked() {
        if(!mUsernameEditText.getText().toString().isEmpty() && !mPasswordEditText.getText().toString().isEmpty()) {
            mPresenter.onLoginPressed(mUsernameEditText.getText().toString(), mPasswordEditText.getText().toString());
        }
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

    /*---------------------------------------LoginContract---------------------------------------*/

    @Override
    public void loginSuccess() {
        Intent homeScreenIntent = new Intent(getBaseActivity(), NavigationActivity.class);
        homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        homeScreenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getBaseActivity().finish();
        getBaseActivity().startActivity(homeScreenIntent);
    }

    @Override
    public void showSuccess(AlertDialog.Builder successDialog) {
        successDialog.show();
    }

    @Override
    public void showProgress(ProgressDialog progressDialog) {
        mProgressDialog = progressDialog;
        mProgressDialog.show();
    }

    @Override
    public void stopProgress() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showAuthError(AlertDialog.Builder errorDialog) {
        errorDialog.show();
    }

    /*----------------------------------------BaseFragment----------------------------------------*/

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
