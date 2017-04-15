package com.fitnation.profile;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.model.User;
import com.fitnation.model.UserWeight;
import com.fitnation.model.UserDemographic;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.UserLogins;
import com.fitnation.networking.tasks.TaskCallback;
import com.fitnation.networking.tasks.UserDemographicTask;
import com.fitnation.networking.tasks.UserTask;
import com.fitnation.networking.tasks.UserWeightTask;
import com.fitnation.profile.callbacks.UserCallback;
import com.fitnation.profile.callbacks.UserDemographicsCallback;
import com.fitnation.profile.callbacks.GetUserWeightCallback;

import java.util.Calendar;
import java.util.List;

import io.realm.Realm;

import static com.fitnation.utils.UnitConversion.*;

public class ProfilePresenter implements ProfileContract.Presenter, TaskCallback.Presenter{

    private ProfileContract.View mView;
    private ProgressDialog mProgressDialog;
    public ProfileData mProfile;
    private ProfileDataManager mProfileDataManager;

    DatePickerFragment dateFragment;
    Resources res;

    final private String TAG = "PROFILE PRESENTER";

    public UserDemographic mUserdemo;
    public User mUser;
    public UserWeight mUserWeight;

    private boolean gotUserDemo = false;
    private boolean gotUser = false;
    private boolean gotWeight = false;

    private RequestQueue mQueue;
    private String mAuthToken;

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
        mQueue = Volley.newRequestQueue(getBaseActivity());
        mProfileDataManager = new ProfileDataManager(mQueue);
        mProfile = mProfileDataManager.getLocalProfileData();
        mAuthToken = AuthToken.getInstance().getAccessToken();
        mProfileDataManager.getUserLogins();

        UserDemographic userdemo = ProfileDataManager.getLocalUserDemographic();
        User user = ProfileDataManager.getLocalUser();
        UserWeight userWeight = ProfileDataManager.getLocalUserWeight();

        setDemographic(userdemo);
        setUser(user);
        setUserWeight(userWeight);

        if (mProfile.isFullProfile()){
            mView.bindExerciseInstanceToView(mProfile);
        } else {
            getProfileData();
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
    public void getProfileData() {
        getUserDemographicFromWeb();

    }

    @Override
    public void showSuccess(AlertDialog.Builder successDialog) {

    }

    @Override
    public void showProgress(ProgressDialog progressDialog) {
        mView.showProgress(progressDialog);
    }

    @Override
    public void stopProgress() {
        mView.stopProgress();
    }

    @Override
    public void showAuthError(AlertDialog.Builder errorDialog) {

    }


    private void getUserDemographicFromWeb(){
        final UserDemographicTask getUserDemoTask =
                new UserDemographicTask(mAuthToken, mQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getUserDemoTask.getUserDemographicById(new UserDemographicsCallback() {
                    @Override
                    public void onSuccess(UserDemographic userDemographic) {
                        setDemographic(userDemographic);
                        gotUserDemo = true;
                        getUserFromWeb();
                        getUserWeightFromWeb();
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d(TAG, error.toString());
                        stopProgress();
                    }
                });
            }
        }).start();
    }

    private void getUserFromWeb(){
        final UserTask getUserTask =
                new UserTask(mAuthToken, mQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getUserTask.getUser(new UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        setUser(user);
                        gotUser = true;
                        sendProfileDataToView();
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d(TAG, error);
                        stopProgress();
                    }
                });
            }
        }).start();
    }

    private void getUserWeightFromWeb(){
        final UserWeightTask getWeightTask =
                new UserWeightTask(mAuthToken, mQueue);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getWeightTask.getUserWeights(new GetUserWeightCallback() {
                    @Override
                    public void onSuccess(List<UserWeight> userWeightList) {
                        List<UserWeight> weightList = userWeightList;
                        if (weightList.size() > 0) {
                            UserWeight weight = weightList.get(weightList.size() - 1);
                            setUserWeight(weight);
                            gotWeight = true;
                            sendProfileDataToView();
                        }
                    }

                    @Override
                    public void onFailure(String error) {
                        Log.d(TAG, error);
                        stopProgress();
                    }
                });
            }
        }).start();
    }

    private void sendProfileDataToView(){
        if (gotUser && gotUserDemo && gotWeight){
            ProfileData profileData = new ProfileData(mUserdemo, mUser, mUserWeight);
            Log.i(TAG, "Setting new profile data loaded from web");
            mView.bindExerciseInstanceToView(profileData);
            mView.stopProgress();
            gotUser = false;
            gotUserDemo = false;
            gotWeight = false;
        }

    }

    @Override
    public void onSwitchMeasurementClicked
            (EditText weightText, EditText heightText, TextView measurementText) {
        double weightFlt = 0;
        double heightFlt = 0;
        try {
            //Since mUserWeight/height may have in/cm text in their value.  We just want the num val
            weightFlt = getNumValue(weightText);
            heightFlt = getNumValue(heightText);
        } catch (NumberFormatException ex){
            Log.d(TAG,
                    "Failed to get numeric value from mUserWeight/height text box" + ex.toString());
        }
        //if measurement text contains "Switch to Metric" then we're in Imperial measurements
        isImperial = measurementText.getText().toString().toLowerCase().contains("metric");
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

        mUserdemo = new UserDemographic();
        mUserWeight = new UserWeight();
        mUser = new User();

        try {
            mUser.setFirstName(mNameTextBox.getText().toString().split("\\s+")[0]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            mUser.setFirstName("");
            Log.d(TAG, "Name textbox empty or not enough elements" + ex.toString());
        }

        try {
            mUser.setLastName(mNameTextBox.getText().toString().split("\\s+")[1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            mUser.setLastName("");
            Log.d(TAG, "Name textbox empty or not enough elements" + ex.toString());
        }


        try {
            mUserdemo.setDateOfBirth(mDobTextBox.getText().toString());
        } catch (NullPointerException e){
            mUserdemo.setDateOfBirth(Calendar.getInstance().getTime());
            Log.d(TAG, e.toString());
        }

        //Get/Set gender
        mUserdemo.setGender((mGenderSpinner.getSelectedItem().toString()));

        //Get/Set skill level
        String skill = mLifterTypeSpinner.getSelectedItem().toString();
        mUserdemo.setSkillLevelLevel(skill);
        mUserdemo.setSkillLevelId(mProfileDataManager.getSkillLevelId(skill));

        double height = getNumValue(mHeightTextBox);
        double weight = getNumValue(mWeightTextBox);
        //if measurement text contains "Switch to Metric" then we're in Imperial measurements
        isImperial = mSwitchMeasurementButton.
                getText().toString().
                toLowerCase().contains("metric");
        if (isImperial){
            mUserdemo.setUnitOfMeasure("Imperial");
            mUserdemo.setHeight(getNumValue(mHeightTextBox).toString());
            mUserdemo.setUserWeights(getNumValue(mWeightTextBox).toString());
            mUserWeight.setWeight(new Float(weight));
        } else { //METRIC MEASUREMENTS
            mUserdemo.setUnitOfMeasure("Metric");
            //WE ONLY WANT TO SAVE VALUES AS IMPERIAL UNITS SO CONVERT FROM METRIC
            height = cmToInch(height);
            weight = kgsToLbs(weight);
            mUserdemo.setHeight(Double.valueOf(height).toString());
            mUserWeight.setWeight(new Float(weight));
        }

        //USER
        try {
            mUser.setEmail(mEmailTextBox.getText().toString());
        } catch (Exception ex) {
            Log.d(TAG, ex.toString());
        }

        saveProfileData();
    }
    //------------------------SaveProfileCallback-----------------------------------//

    @Override
    public void saveProfileData() {
        //LOCAL DATE STORE

        mProfileDataManager.SaveUserDemographicData(mUserdemo);
        mProfileDataManager.SaveUserData(mUser);
        mProfileDataManager.SaveWeightData(mUserWeight);

        mProfileDataManager.saveUserDemographicToWeb(mUserdemo);
        mProfileDataManager.saveUserToWeb(mUser);
        mProfileDataManager.saveUserWeightToWeb(mUserWeight);
    }


    public BaseActivity getBaseActivity(){
        return mView.getBaseActivity();
    }

    public void setDemographic(UserDemographic userDemo){ mUserdemo = userDemo; }
    public void setUser(User user){ this.mUser = user; }
    public void setUserWeight(UserWeight weight){ this.mUserWeight = weight; }

    @Override
    public void stop() {}
}
