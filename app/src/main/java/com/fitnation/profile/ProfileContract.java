package com.fitnation.profile;

import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;


public interface ProfileContract {
    interface View extends BaseView<Presenter> {

        FragmentManager getFragmentManager();

        void bindExerciseInstanceToView(ProfileData pInstance);
    }

    interface Presenter extends BasePresenter {
        void saveProfileData();

        void getProfileData(ProfilePresenter presenter);

        void onSwitchMeasurementClicked
                (EditText weightText, EditText heightText, TextView measurementText);

        void scaleFocusChanged(EditText heightText, EditText weightText, boolean focus);

        void openDatePicker(DatePickerFragment datePickerFragment);

        void onSaveClicked(EditText mNameTextBox, EditText mWeightTextBox, EditText mDobTextBox, EditText mHeightTextBox, EditText mEmailTextBox, TextView mSwitchMeasurementButton, Spinner mGenderSpinner, Spinner mLifterTypeSpinner);

        void start();

        void onDateSet(DatePicker view, int year, int month,
                       int day, EditText dobText, EditText ageText);
    }
}

