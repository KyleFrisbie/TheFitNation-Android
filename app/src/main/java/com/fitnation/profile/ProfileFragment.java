package com.fitnation.profile;


import java.util.Map;
import java.util.Calendar;

import java.util.Date;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.enums.Gender;
import com.fitnation.model.enums.SkillLevel;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

/*
 * View / Fragment Class for Profile
 */


public class ProfileFragment extends BaseFragment implements ProfileContract.View,
        DatePickerDialog.OnDateSetListener
        {

    private ProfileContract.Presenter mPresenter;

    @BindView(R.id.firstNameTextBox)     public EditText mFirstNameTextBox;
    @BindView(R.id.lastNameTextBox)      public EditText mLastNameTextBox;
    @BindView(R.id.weightTextBox)        public EditText mWeightTextBox;
    @BindView(R.id.heightTextBox)        public EditText mHeightTextBox;
    @BindView(R.id.ageTextBox)           public Button mAgePicker;
    @BindView(R.id.gendersRadioGroup)    public RadioGroup mGenderButton;
    @BindView(R.id.lifterTypeRadioGroup) public RadioGroup mLifterButton;
    @BindView(R.id.saveButton)           public Button mSaveButton;


    Calendar birthday;
    Gender gender;
    SkillLevel skillLevel;
    UserDemographic userdemo;
    DatePickerFragment dateFragment;

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
        gender = new Gender();
        dateFragment = new DatePickerFragment();
        dateFragment.setFragment(this);
        skillLevel = new SkillLevel();
        if (userdemo!=null) loadDemographics();
        mPresenter.start();
    }

    @OnClick(R.id.ageTextBox)
    public void onAgeClicked(){
        dateFragment.show(getFragmentManager(), "datePicker");


    }

    @OnClick(R.id.saveButton)
    public void onSaveClicked() {
        userdemo = new UserDemographic();

        System.out.println(mHeightTextBox.getText().toString());
        System.out.println(mWeightTextBox.getText().toString());

        userdemo.setFirstName(mFirstNameTextBox.getText().toString());
        userdemo.setLastName(mLastNameTextBox.getText().toString());
        userdemo.setDob(birthday.getTime());
        //Get/Set gender
        userdemo.setGender(gender.getGenderFromId(mGenderButton.getCheckedRadioButtonId()));
        userdemo.setHeight(mHeightTextBox.getText().toString());
        userdemo.setUserWeights(mWeightTextBox.getText().toString());
        //Get/Set skill level
        userdemo.setSkillLevel(skillLevel.getSkillLevelFromId
                (mLifterButton.getCheckedRadioButtonId()));

        mPresenter.onSaveClicked(userdemo);
    }

    public void loadDemographics(){
        Calendar cal = Calendar.getInstance();
        cal.setTime(userdemo.getDob());


        try {
            mFirstNameTextBox.setText(userdemo.getFirstName());
        } catch (Exception e){}
        try {
            mLastNameTextBox.setText(userdemo.getLastName());
        } catch (Exception e){}
        try {
            mWeightTextBox.setText(userdemo.getUserWeight().toString());
        } catch (Exception e){ System.out.println(e.toString());}
        try {
            mHeightTextBox.setText(userdemo.getHeight().toString());
        } catch (Exception e){ System.out.println(e.toString());}
        try {
            //mAgePicker.set(userdemo.getDob());
        } catch (Exception e){}
        try {
            mGenderButton.check(gender.getIdFromGender(
                    userdemo.getGender()));
        } catch (Exception e){}
        try {
            mLifterButton.check(skillLevel.getIdFromSkillLevel(
                    userdemo.getSkillLevel()));
        } catch (Exception e){}

    }

    public void setDemographic(UserDemographic userDemo){ userdemo = userDemo; }

    @Override
    public void setPresenter(ProfileContract.Presenter presenter){ mPresenter = presenter;}

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        int yearsOld;
        if (calendar!=null) {
            Calendar now = Calendar.getInstance();

            long dateDiff = now.getTimeInMillis() - calendar.getTimeInMillis();
            if (dateDiff > 0) {
                dateDiff /= 1000; //TO SECONDS
                dateDiff /= 3600; //TO HOURS
                dateDiff /= 24;  //TO DAYS
                dateDiff /= 365; //TO YEARS
                yearsOld = (int)(dateDiff);
                mAgePicker.setText(String.valueOf(yearsOld));
                birthday = calendar;
                return;
            }
        }

        mAgePicker.setText("Age");
    }
}