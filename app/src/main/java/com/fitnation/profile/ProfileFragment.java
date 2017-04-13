package com.fitnation.profile;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.navigation.NavigationActivity;


import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

import static com.fitnation.utils.UnitConversion.*;

/*
 * View / Fragment Class for Profile
 */


public class ProfileFragment extends BaseFragment implements ProfileContract.View,
        DatePickerDialog.OnDateSetListener {


    @BindView(R.id.nameText)          public EditText mNameTextBox;
    @BindView(R.id.weightEditText)    public EditText mWeightTextBox;
    @BindView(R.id.heightEditText)    public EditText mHeightTextBox;
    @BindView(R.id.ageText)           public EditText mAgeTextBox;
    @BindView(R.id.birthdayEditText)  public EditText mDobTextBox;
    @BindView(R.id.emailEditText)     public EditText mEmailTextBox;
    @BindView(R.id.saveButton)        public Button mSaveButton;
    @BindView(R.id.genderEditText)    public Spinner mGenderSpinner;
    @BindView(R.id.lifterTypeSpinner) public Spinner mLifterTypeSpinner;
    @BindView(R.id.switchMeasurement) public TextView mSwitchMeasurementButton;

    ArrayAdapter<CharSequence> genderAdapter;
    ArrayAdapter<CharSequence> lifterTypeAdapter;
    DatePickerFragment dateFragment;

    private ProfileContract.Presenter mPresenter;

    ProfileData mProfile;


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
        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        ButterKnife.bind(this, v);
        mPresenter = new ProfilePresenter(this);

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



        genderAdapter = ArrayAdapter.createFromResource(getBaseActivity(),
                R.array.genderArray, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(genderAdapter);

        lifterTypeAdapter = ArrayAdapter.createFromResource(getBaseActivity(),
                R.array.lifterTypeArray, android.R.layout.simple_spinner_item);
        lifterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLifterTypeSpinner.setAdapter(lifterTypeAdapter);


    return v;
    }

    public void onStart() {
        super.onStart();
        mPresenter.start();

        NavigationActivity navigationActivity = (NavigationActivity) getBaseActivity();
        navigationActivity.displayBackArrow(true, "Edit");
    }

    @OnClick(R.id.switchMeasurement)
    public void onSwitchMeasurementClicked(){
        mPresenter.onSwitchMeasurementClicked
                (mWeightTextBox, mHeightTextBox, mSwitchMeasurementButton);
    }

    @OnFocusChange({R.id.weightEditText, R.id.heightEditText})
    void scaleFocusChanged(boolean focus){
        mPresenter.scaleFocusChanged(mHeightTextBox, mWeightTextBox, focus);
    }


    @OnClick({R.id.birthdayEditText, R.id.ageText})
    public void onDobClicked(){
        dateFragment = new DatePickerFragment();
        dateFragment.setFragment(this);
        mPresenter.openDatePicker(dateFragment);
    }


    @OnClick(R.id.saveButton)
    public void onSaveClicked() {
        mPresenter.onSaveClicked(
                mNameTextBox, mWeightTextBox, mDobTextBox, mHeightTextBox,
                mEmailTextBox, mSwitchMeasurementButton, mGenderSpinner, mLifterTypeSpinner
        );

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        mPresenter.onDateSet(view, year, month, day, mDobTextBox, mAgeTextBox);
    }


    @Override
    public void setPresenter(ProfileContract.Presenter presenter){ mPresenter = presenter;}

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void bindExerciseInstanceToView(ProfileData pInstance){
        mProfile = pInstance;

        if (pInstance==null) {
            Log.d("PROFILE", "Failed to load demographic, user demographic is null.");
            return;
        }

        try {
            if (pInstance.getSkillLevelLevel().toLowerCase().contains("imperial")){
                mSwitchMeasurementButton.setText(R.string.switchMeasureToMetric);
            } else {
                mSwitchMeasurementButton.setText(R.string.switchMeasureToImperial);
            }

        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        }

        try {
            if (isImperial())
            {
                mHeightTextBox.setText(pInstance.getHeight().toString());
            } else { //CONVERT SAVED INCHES TO CM
                mHeightTextBox.setText(String.valueOf(inchToCM(pInstance.getHeight())));
            }
        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        } try {
            String dob = pInstance.getDateOfBirth();
            Calendar c = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dob);
            c.setTime(date);
            int age = getAgeInYears(c);
            mDobTextBox.setText(dob);
            mAgeTextBox.setText("age: " + String.valueOf(age));
        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        }
        try {
            mGenderSpinner.setSelection(
                    genderAdapter.getPosition(pInstance.getGender()));
        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        }
        try {
            mLifterTypeSpinner
                    .setSelection(lifterTypeAdapter.getPosition(pInstance.getSkillLevelLevel()));
        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        }

        try {
            mEmailTextBox.setText(pInstance.getEmail());
        } catch (Exception ex) {
            Log.d("PROFILE", ex.toString());
        }

        try {
            String first = pInstance.getFirstName();
            String last = pInstance.getLastName();
            if (first != null && last != null)
                mNameTextBox.setText(first + " " + last);
        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        }

        try{
            mEmailTextBox.setText(pInstance.getEmail());
        } catch (Exception e) {
            Log.d("PROFILE", e.toString());
        }

        if (pInstance==null) {
            Log.d("PROFILE", "Attempted to load Null UserWeight to profile");
            return;
        }
        try {
            if (isImperial()) {
                mWeightTextBox.setText(pInstance.getWeight().toString());
            } else {
                mWeightTextBox.setText(String.valueOf(lbsToKgs(pInstance.getWeight())));
            }

        } catch (Exception e) {
            Log.d("PROFILE", e.getMessage());
        }
    }

    private boolean isImperial(){
        return (mProfile.getUnitOfMeasure().contains("imperial"));
    }

}