package com.fitnation.profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.login.LoginContract;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * View / Fragment Class for Profile
 */


public class ProfileView extends BaseFragment implements ProfileContract.View {

    private ProfileContract.Presenter mPresenter;

    @BindView(R.id.usernameButton) public EditText mUsernameButton;
    @BindView(R.id.weightButton) public EditText mWeightButton;
    @BindView(R.id.heightButton) public EditText mHeighteButton;
    @BindView(R.id.ageButton) public EditText mAgeButton;
    @BindView(R.id.lifterTypeButton) public EditText mLifterButton;

    public ProfileView() {
        // Empty constructor
    }

    public static ProfileView newInstance() {
        return new ProfileView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_profile, container, false);
    ButterKnife.bind(this, v);

    return v;
    }

    public void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @OnClick(R.id.usernameButton)
    public void usernameButtonClicked() { mPresenter.onUserNamePressed(); }

    @OnClick(R.id.weightButton)
    public void weightButtonClicked() { mPresenter.onWeightPressed(); }

    @OnClick(R.id.heightButton)
    public void heighteButtonClicked() { mPresenter.onHeightPressed(); }

    @OnClick(R.id.ageButton)
    public void ageButtonClicked() { mPresenter.onAgePressed(); }

    @OnClick(R.id.lifterTypeButton)
    public void lifterTypeClicked() { mPresenter.onLifterTypePressed(); }





    @Override
    public void setPresenter(ProfileContract.Presenter presenter){ mPresenter = presenter;}

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
