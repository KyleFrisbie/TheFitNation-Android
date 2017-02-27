package com.fitnation.profile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.model.UserDemographic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
 * View / Fragment Class for Profile
 */


public class ProfileFragment extends BaseFragment implements ProfileContract.View {

    private ProfileContract.Presenter mPresenter;

    @BindView(R.id.firstNameButton) public EditText mFirstNameTextBox;
    @BindView(R.id.lastNameButton) public EditText mLastNameTextBox;
    @BindView(R.id.weightButton) public EditText mWeightTextBox;
    @BindView(R.id.heightButton) public EditText mHeightTextBox;
    @BindView(R.id.ageButton) public EditText mAgeButton;
    @BindView(R.id.lifterTypeButton) public EditText mLifterButton;

    public ProfileFragment() {
        // Empty constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
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

    @OnClick(R.id.ageButton)
    public void ageButtonClicked() { mPresenter.onAgePressed(); }

    @OnClick(R.id.lifterTypeButton)
    public void lifterTypeClicked() { mPresenter.onLifterTypePressed(); }

    public void onSaveClicked() {
        UserDemographic userDemo = new UserDemographic();
        //userDemo.setFirstName(mUsernameTextBox.getText().toString());
        //userDemo.setLastName(m);


        mPresenter.onSave(userDemo);
    }



    @Override
    public void setPresenter(ProfileContract.Presenter presenter){ mPresenter = presenter;}

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
