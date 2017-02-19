package com.fitnation.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.stormpath.sdk.Stormpath;
import com.stormpath.sdk.StormpathCallback;
import com.stormpath.sdk.models.RegistrationForm;
import com.stormpath.sdk.models.StormpathError;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment implements RegisterContract.View{
    private RegisterContract.Presenter mPresenter;

    @BindView(R.id.register_register_button) public Button mRegisterButton;
    @BindView(R.id.register_user_editText) public EditText mEmail;
    @BindView(R.id.register_password_editText) public EditText mPassword;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @OnClick(R.id.register_register_button)
    public void onRegisterButtonPressed() {
        String userEmail = mEmail.getText().toString();
        String userPassword = mPassword.getText().toString();
        mPresenter.onRegisterCreatePressed(userEmail, userPassword);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showAuthError() {

    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) { mPresenter = presenter; }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
