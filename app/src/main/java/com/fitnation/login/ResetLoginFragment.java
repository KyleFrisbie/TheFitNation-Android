package com.fitnation.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetLoginFragment extends BaseFragment implements ResetLoginContract.View{
    private ResetLoginContract.Presenter mPresenter;

    String email;
    @BindView(R.id.resetPassword_editText) public EditText mResetPasswordEditText;

    public ResetLoginFragment() {
        // Required empty public constructor
    }

    public static ResetLoginFragment newInstance() {
        return new ResetLoginFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reset_login, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @OnClick(R.id.resetPassword_button)
    public void onResetPasswordButtonPressed() {
        email = mResetPasswordEditText.getText().toString();
        mPresenter.onResetPasswordButtonPressed(email);
    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showProgress(String message) {
        InputMethodManager inputMethodManager = (InputMethodManager) getBaseActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        Snackbar progressSnackBar = Snackbar.make(getBaseActivity().findViewById(R.id.activity_login),
                message, BaseTransientBottomBar.LENGTH_SHORT);
        progressSnackBar.show();
    }

    @Override
    public void showAuthError(String errorMessage) {
        InputMethodManager inputMethodManager = (InputMethodManager) getBaseActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        Snackbar progressSnackBar = Snackbar.make(getBaseActivity().findViewById(R.id.activity_login),
                errorMessage, BaseTransientBottomBar.LENGTH_SHORT);
        progressSnackBar.show();
    }

    @Override
    public void setPresenter(ResetLoginContract.Presenter presenter) { mPresenter = presenter; }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
