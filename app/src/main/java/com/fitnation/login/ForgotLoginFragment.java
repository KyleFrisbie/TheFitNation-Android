package com.fitnation.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotLoginFragment extends BaseFragment implements ForgotLoginContract.View{
    private ForgotLoginContract.Presenter mPresenter;

    String email;
    @BindView(R.id.resetPassword_editText) public EditText mResetPasswordEditText;

    public ForgotLoginFragment() {
        // Required empty public constructor
    }

    public static ForgotLoginFragment newInstance() {
        return new ForgotLoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_forgot_login, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @OnClick(R.id.resetPassword_button)
    public void onResetPasswordButtonPressed() {
        email = mResetPasswordEditText.getText().toString();
        mPresenter.onResetPasswordButtonPressed(email);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showAuthError() {

    }

    @Override
    public void setPresenter(ForgotLoginContract.Presenter presenter) { mPresenter = presenter; }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
