package com.fitnation.profile;

import android.content.res.Resources;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.model.User;
import com.fitnation.model.UserWeight;
import com.fitnation.model.UserDemographic;
import com.fitnation.networking.UserLogins;
import com.fitnation.networking.tasks.ProfileDataTask;

import java.util.Calendar;

import static com.fitnation.utils.UnitConversion.*;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View mView;
    public ProfileData mProfile;

    DatePickerFragment dateFragment;
    Resources res;

    final private String TAG = "PROFILE PRESENTER";

    public UserDemographic userdemo;
    public User user;
    public UserWeight userWeight;

    private boolean isImperial;

    public ProfilePresenter(ProfileContract.View view) {
        mView = view;
    }

    @Override
    public void onViewReady() {

    }

    @Override
    public void start() {
        res = mView.getBaseActivity().getResources();

        mProfile = ProfileDataManager.getLocalProfileData();

        if (!mProfile.isFullProfile()) {
            getProfileData(this);
        }

    }


    @Override
    public void onDateSet
            (DatePicker view, int year, int month,
             int day, EditText dobText, EditText ageText) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        int yearsOld;
        if (calendar!=null) {
            Calendar now = Calendar.getInstance();

            yearsOld = getAgeInYears(calendar);
            dobText.setText(
                    String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
            ageText.setText("age:" + String.valueOf(yearsOld));
            return;
        }

        ageText.setText("Age");
    }

    @Override
    public void stop() {}


    @Override
    public void getProfileData(ProfilePresenter presenter) {
        ProfileDataTask pdt = new ProfileDataTask(getBaseActivity());
        pdt.getProfileData(this);
    }

    @Override
    public void onSwitchMeasurementClicked
            (EditText weightText, EditText heightText, TextView measurementText) {
        double weightFlt = 0;
        double heightFlt = 0;
        try {
            //Since userWeight/height may have in/cm text in their value.  We just want the num val
            weightFlt = getNumValue(weightText);
            heightFlt = getNumValue(heightText);
        } catch (NumberFormatException ex){
            Log.d(TAG,
                    "Failed to get numeric value from userWeight/height text box" + ex.toString());
        }

        if (isImperial){
            measurementText.setText(res.getString(R.string.switchMeasureToImperial));
            isImperial = false;
            weightFlt = lbsToKgs(weightFlt);  //lb to kg
            heightFlt = inchToCM(heightFlt); //inch to cm
            weightText.setText(String.format("%.1f", weightFlt)+ " kgs");
            heightText.setText(String.format("%.1f", heightFlt)+ " cms");
        } else {
            isImperial = true;
            measurementText.setText(res.getString(R.string.switchMeasureToMetric));
            weightFlt = kgsToLbs(weightFlt);  //kgs to lbs
            heightFlt = cmToInch(heightFlt); //cm to inch
            weightText.setText(String.format("%.1f", weightFlt) + " lbs");
            heightText.setText(String.format("%.1f", heightFlt) + " inches");
        }

    }

    @Override
    public void scaleFocusChanged
            (EditText heightText, EditText weightText, boolean focus) {
        if (focus) {
            heightText.setText(removeUnits(heightText.getText().toString()));
            weightText.setText(removeUnits(weightText.getText().toString()));
        } else {
            measurementsAddUnits(heightText, weightText);
        }
    }

    private Float getNumValue(EditText textBox){
        String txt[] = textBox.getText().toString().trim().split("\\s+");
        if (txt[0] != null && !txt[0].isEmpty()){
            return Float.valueOf(txt[0]);
        } else {
            return 0.0f;
        }
    }

    private String removeUnits(String txt){
        return txt.replaceAll("[^\\d.]", "");
    }

    private void measurementsAddUnits(EditText heightText, EditText weightText){

        if (isImperial){
            weightText.setText(String.format("%.1f", getNumValue(weightText))+ " lbs");
            heightText.setText(String.format("%.1f", getNumValue(heightText))+ " inches");
        } else {
            weightText.setText(String.format("%.1f", getNumValue(weightText))+ " kgs");
            heightText.setText(String.format("%.1f", getNumValue(heightText))+ " cms");
        }
    }

    public void openDatePicker(DatePickerFragment dateFragment){
        dateFragment.show(mView.getFragmentManager(), "datePicker");
    }

    @Override
    public void onSaveClicked
            (EditText mNameTextBox, EditText mWeightTextBox,
             EditText mDobTextBox, EditText mHeightTextBox,
             EditText mEmailTextBox, TextView mSwitchMeasurementButton,
             Spinner mGenderSpinner, Spinner mLifterTypeSpinner) {


        userdemo = new UserDemographic();
        userWeight = new UserWeight();
        user = new User();


        try {
            user.setFirstName(mNameTextBox.getText().toString().split("\\s+")[0]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            user.setFirstName("");
            Log.d(TAG, "Name textbox empty or not enough elements" + ex.toString());
        }

        try {
            user.setLastName(mNameTextBox.getText().toString().split("\\s+")[1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            user.setLastName("");
            Log.d(TAG, "Name textbox empty or not enough elements" + ex.toString());
        }


        try {
            userdemo.setDateOfBirth(mDobTextBox.getText().toString());
        } catch (NullPointerException e){
            userdemo.setDateOfBirth(Calendar.getInstance().getTime());
            Log.d(TAG, e.toString());
        }

        //Get/Set gender
        userdemo.setGender((mGenderSpinner.getSelectedItem().toString()));

        //Get/Set skill level
        userdemo.setSkillLevelLevel(mLifterTypeSpinner.getSelectedItem().toString());

        double height = getNumValue(mHeightTextBox);
        double weight = getNumValue(mWeightTextBox);
        if (isImperial){
            userdemo.setUnitOfMeasure("Imperial");
            userdemo.setHeight(getNumValue(mHeightTextBox).toString());
            userdemo.setUserWeights(getNumValue(mWeightTextBox).toString());
            userWeight.setWeight(new Float(weight));
        } else { //METRIC MEASUREMENTS
            userdemo.setUnitOfMeasure("Metric");
            //WE ONLY WANT TO SAVE VALUES AS IMPERIAL UNITS SO CONVERT FROM METRIC
            height = cmToInch(height);
            weight = kgsToLbs(weight);
            userdemo.setHeight(Double.valueOf(height).toString());
            userWeight.setWeight(new Float(weight));
        }

        //USER
        try {
            user.setEmail(mEmailTextBox.getText().toString());
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }

        saveProfileData();
    }

    @Override
    public void saveProfileData() {
        //LOCAL DATE STORE
        ProfileDataManager profileDataManager = new ProfileDataManager();

        profileDataManager.SaveUserDemographicData(userdemo);
        profileDataManager.SaveUserData(user);
        profileDataManager.SaveWeightData(userWeight);

        if (UserLogins.getInstance().getUserDemographicId() != null){
            ProfileDataTask pdt = new ProfileDataTask(getBaseActivity());
            pdt.saveProfileData(this);
        }
    }

    public void bindExerciseInstanceToView(){
        mView.bindExerciseInstanceToView(mProfile);
    }

    public BaseActivity getBaseActivity(){
        return mView.getBaseActivity();
    }

    public void setDemographic(UserDemographic userDemo){ userdemo = userDemo; }
    public void setUser(User user){ this.user = user; }
    public void setUserWeight(UserWeight weight){ this.userWeight = weight; }

}
