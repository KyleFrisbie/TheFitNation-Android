package com.fitnation.profile;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fitnation.base.BasePresenter;
import com.fitnation.base.BaseView;
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserWeight;


public interface ProfileContract {
    interface View extends BaseView<Presenter> {
        void showProgress(ProgressDialog progressDialog);
        void stopProgress();
        FragmentManager getFragmentManager();
        void bindExerciseInstanceToView(ProfileData pInstance);
    }

    interface Presenter extends BasePresenter {
        void start();
        void saveProfileData();

        void getProfileData();

        void onSwitchMeasurementClicked
                (EditText weightText, EditText heightText, TextView measurementText);

        void scaleFocusChanged(EditText heightText, EditText weightText, boolean focus);

        void openDatePicker(DatePickerFragment datePickerFragment);

        void onSaveClicked(TextView mNameTextBox, EditText mWeightTextBox,
                           EditText mDobTextBox, EditText mHeightTextBox,
                           TextView mEmailTextBox, TextView mSwitchMeasurementButton,
                           Spinner mGenderSpinner, Spinner mLifterTypeSpinner);
        void measurementsAddUnits(EditText heightText, EditText weightText);

        void onDateSet(DatePicker view, int year, int month,
                       int day, EditText dobText, EditText ageText);

        void setDemographic(UserDemographic userDemo);
        void setUser(User user);
        void setUserWeight(UserWeight weight);
    }
}

