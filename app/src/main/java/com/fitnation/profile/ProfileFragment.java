package com.fitnation.profile;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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

    @BindView(R.id.nameText)          public EditText mNameTextBox;
    @BindView(R.id.weightEditText)    public EditText mWeightTextBox;
    @BindView(R.id.heightEditText)    public EditText mHeightTextBox;
    @BindView(R.id.ageText)           public EditText mAgeTextBox;
    @BindView(R.id.birthdayEditText)  public EditText mDobTextBox;
    @BindView(R.id.saveButton)        public Button mSaveButton;
    @BindView(R.id.switchMeasurement) public TextView mUnitTypeButton;
    @BindView(R.id.genderEditText)    public EditText mGenderTextBox;

    final long MILLISECONDS_IN_YEAR = 31556952000L;
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

        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyPressed, KeyEvent keyEvent) {
                if( keyPressed == KeyEvent.KEYCODE_BACK){
                    onSaveClicked();
                    return true;
                }
                return false;
            }
        });

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

        userdemo.setFirstName(mNameTextBox.getText().toString());
        userdemo.setDateOfBirth(birthday.getTime());
        //Get/Set gender
        userdemo.setGender((mGenderTextBox.getText().toString()));
        userdemo.setHeight(mHeightTextBox.getText().toString());
        userdemo.setUserWeights(mWeightTextBox.getText().toString());
        //Get/Set skill level
        userdemo.setSkillLevelLevel(skillLevel.getSkillLevelFromId
                (mLifterButton.getCheckedRadioButtonId()));

        userdemo.setUnitOfMeasure(mUnitTypeButton.getText().toString());

        mPresenter.saveData(userdemo);
    }

    public void loadDemographics(){

        try {
            mNameTextBox.setText(userdemo.getFirstName());
        } catch (Exception e){}
        try {
            mWeightTextBox.setText(userdemo.getUserWeight().toString());
        } catch (Exception e){ System.out.println(e.toString());}
        try {
            mHeightTextBox.setText(userdemo.getHeight().toString());
        } catch (Exception e){ System.out.println(e.toString());}
        try {
            String dob = userdemo.getDateOfBirth();
            Calendar c = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dob);
            c.setTime(date);
            int age = getAgeInYears(c);
            mAgeTextBox.setText("age: " + String.valueOf(age));
        } catch (Exception e){}
        try {
            mGenderTextBox.check(gender.getIdFromGender(
                    userdemo.getGender()));
        } catch (Exception e){}
        try {
            mLifterButton.check(skillLevel.getIdFromSkillLevel(
                    userdemo.getSkillLevelLevel()));
        } catch (Exception e){}

        try {
            String unitType = userdemo.getUnitOfMeasure();
            //if weightType is pounds set check, else unchecked
            mUnitTypeButton.setChecked(
                    unitType.toLowerCase().contains("imperial"));

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

            yearsOld = getAgeInYears(calendar);

            mAgePicker.setText(String.valueOf(yearsOld));
            return;
        }

        mAgePicker.setText("Age");
    }

    public int getAgeInYears(Calendar cal){
        Calendar now = Calendar.getInstance();
        long mili = now.getTimeInMillis() - cal.getTimeInMillis();
        if (mili > 0) {

            mili /= MILLISECONDS_IN_YEAR; //TO YEARS
            int yearsOld = (int) (mili);
            mAgePicker.setText(String.valueOf(yearsOld));
            birthday = cal;
            return yearsOld;
        }
        return 0;
    }



}