package com.fitnation.login;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResetLoginFragment extends BaseFragment implements ResetLoginContract.View{
    private ResetLoginContract.Presenter mPresenter;
    private ProgressDialog mProgressDialog;

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
        View v = inflater.inflate(R.layout.login_reset_password_fragment, container, false);
        ImageView imageView = (ImageView) v.findViewById(R.id.reset_password_background_imageView);
        Picasso.with(getBaseActivity()).load(R.drawable.login_background_3).into(imageView);
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
        if(!mResetPasswordEditText.getText().toString().isEmpty()) {
            mPresenter.onResetPasswordButtonPressed(mResetPasswordEditText.getText().toString());
        }
    }

    /*-------------------------------------ResetLoginContract-------------------------------------*/

    @Override
    public void showSuccess(android.support.v7.app.AlertDialog.Builder successDialog) {
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
    public void showAuthError(android.support.v7.app.AlertDialog.Builder errorDialog) {
        errorDialog.show();
    }

    /*----------------------------------------BaseFragment----------------------------------------*/

    @Override
    public void setPresenter(ResetLoginContract.Presenter presenter) { mPresenter = presenter; }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
