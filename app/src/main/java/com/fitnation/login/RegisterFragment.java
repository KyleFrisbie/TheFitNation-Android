package com.fitnation.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

public class RegisterFragment extends BaseFragment implements RegisterContract.View{
    private RegisterContract.Presenter mPresenter;

    @BindView(R.id.registerEmail_editText) public EditText mEmail;
    @BindView(R.id.registerPassword_editText) public EditText mPassword;
    @BindView(R.id.userName_editText) public EditText userName;

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

    @OnClick(R.id.register_button)
    public void onRegisterButtonPressed() {
        mPresenter.onRegisterCreatePressed(mEmail.getText().toString().trim(),
                mPassword.getText().toString().trim(),
                userName.getText().toString().trim(),
                "en");
    }

    @Override
    public void showProgress(String message) {
        InputMethodManager inputMethodManager = (InputMethodManager) getBaseActivity()
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        final Snackbar thanksMessage = Snackbar.make(getBaseActivity().findViewById(R.id.activity_login),
                "Welcome: Before you log in please verify your email", BaseTransientBottomBar.LENGTH_INDEFINITE);
                thanksMessage.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        thanksMessage.dismiss();
                    }
                });
        thanksMessage.show();
    }

    @Override
    public void showAuthError(String errorMessage) {
        Snackbar progressSnackBar = Snackbar.make(getBaseActivity().findViewById(R.id.activity_login),
                errorMessage, BaseTransientBottomBar.LENGTH_SHORT);
        progressSnackBar.show();
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) { mPresenter = presenter; }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
