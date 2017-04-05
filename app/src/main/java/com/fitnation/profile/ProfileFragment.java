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
import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.enums.Gender;
import com.fitnation.model.enums.SkillLevel;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/*
 * View / Fragment Class for Profile
 */


public class ProfileFragment extends BaseFragment implements ProfileContract.View,
        DatePickerDialog.OnDateSetListener {

    private ProfileContract.Presenter mPresenter;

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

    boolean unsavedChanges = true;
    final long MILLISECONDS_IN_YEAR = 31556952000L;
    final double INCH_PER_CM = 0.393701;
    final double CM_PER_INCH = 2.54;
    final double LBS_PER_KG = 2.20462;
    final double KG_PER_LB = 0.453592;
    Calendar birthday;
    UserDemographic userdemo;
    User user;
    DatePickerFragment dateFragment;
    ArrayAdapter<CharSequence> genderAdapter;
    ArrayAdapter<CharSequence> lifterTypeAdapter;

    public ProfileFragment() {
        // Empty constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (userdemo == null) {
            mPresenter.getUserDemographic(this);
        }
        loadDemographics();
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

        genderAdapter = ArrayAdapter.createFromResource(this.getBaseActivity(),
                R.array.genderArray, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenderSpinner.setAdapter(genderAdapter);

        lifterTypeAdapter = ArrayAdapter.createFromResource(this.getBaseActivity(),
                R.array.lifterTypeArray, android.R.layout.simple_spinner_item);
        lifterTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLifterTypeSpinner.setAdapter(lifterTypeAdapter);

    return v;
    }

    public void onStart() {
        super.onStart();
        dateFragment = new DatePickerFragment();
        dateFragment.setFragment(this);



        mPresenter.start();
    }

    @OnClick(R.id.switchMeasurement)
    public void onSwitchMeasurementClicked(){
        double weightFlt = 0;
        double heightFlt = 0;
        try {
            //Since weight/height may have in/cm text in their value.  We just want the num val
            weightFlt = getNumValue(mWeightTextBox);
            heightFlt = getNumValue(mHeightTextBox);
        } catch (NumberFormatException ex){
            Log.d("PROFILE",
                    "Failed to get numeric value from weight/height text box" + ex.toString());
        }
        // if mSwitchMeasurement says Switch to Metric
        if (mSwitchMeasurementButton.getText().toString().toLowerCase().contains("metric")){
            mSwitchMeasurementButton.setText(getString(R.string.switchMeasureImperial));
            weightFlt = (weightFlt * KG_PER_LB);  //lb to kg
            heightFlt = (heightFlt * CM_PER_INCH); //cm to inch
            mWeightTextBox.setText(String.format("%.1f", weightFlt)+ " kgs");
            mHeightTextBox.setText(String.format("%.1f", heightFlt)+ " cms");
        } else {
            mSwitchMeasurementButton.setText(getString(R.string.switchMeasureMetrics));
            weightFlt = (weightFlt * LBS_PER_KG);  //kgs to lbs
            heightFlt = (heightFlt * INCH_PER_CM); //cm to inch
            mWeightTextBox.setText(String.format("%.1f", weightFlt) + " lbs");
            mHeightTextBox.setText(String.format("%.1f", heightFlt) + " inches");
        }

        unsavedChanges = true;
    }


    private Float getNumValue(EditText textBox){
        String txt[] = textBox.getText().toString().trim().split("\\s+");
        Log.i("PROFILE", "text num value is "+txt[0]);
        if (txt[0] != null && !txt[0].isEmpty()){
            return Float.valueOf(txt[0]);
        } else {
            return 0.0f;
        }
    }

    @OnFocusChange(R.id.weightEditText)
    void weightFocusChanged(View v, boolean focus){
        if (focus) {
            String txt = removeUnits(mWeightTextBox.getText().toString());
            mWeightTextBox.setText(txt);
        } else {
            measurementsAddUnits();
        }
    }


    @OnFocusChange(R.id.heightEditText)
    void heightFocusChanged(View v, boolean focus){
        if (focus) {  //Get Focus, remove the alphabet characters
            String txt = removeUnits(mHeightTextBox.getText().toString());
            mHeightTextBox.setText(txt);
        } else { //Lose Focus
            measurementsAddUnits();
        }
    }

    private String removeUnits(String txt){
        txt.replaceAll("[^\\d.]", "");
        return txt;
    }


    private void measurementsAddUnits(){

        if (mSwitchMeasurementButton.getText().toString().toLowerCase().contains("imperial")){

            mWeightTextBox.setText(String.format("%.1f", getNumValue(mWeightTextBox))+ " kgs");
            mHeightTextBox.setText(String.format("%.1f", getNumValue(mHeightTextBox))+ " cms");
        } else {
            mWeightTextBox.setText(String.format("%.1f", getNumValue(mWeightTextBox))+ " lbs");
            mHeightTextBox.setText(String.format("%.1f", getNumValue(mHeightTextBox))+ " inches");
        }
        unsavedChanges = true;
    }

    @OnClick(R.id.birthdayEditText)
    public void onDobClicked(){
        openDatePicker();
    }

    @OnClick(R.id.ageText)
    public void onAgeClicked(){
        openDatePicker();
    }

    private void openDatePicker(){
        dateFragment.show(getFragmentManager(), "datePicker");
    }

    @OnClick(R.id.saveButton)
    public void onSaveClicked() {
        if (!unsavedChanges) return; //if nothing has been changed dont save again
        userdemo = new UserDemographic();
        try {
            userdemo.setFirstName(mNameTextBox.getText().toString().split("\\s+")[0]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            userdemo.setFirstName("");
            Log.d("PROFILE", "Name textbox empty or not enough elements" + ex.toString());
        }

        try {
            userdemo.setLastName(mNameTextBox.getText().toString().split("\\s+")[1]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            userdemo.setLastName("");
            Log.d("PROFILE", "Name textbox empty or not enough elements" + ex.toString());
        }



        userdemo.setDateOfBirth(birthday.getTime());
        //Get/Set gender
        userdemo.setGender((mGenderSpinner.getSelectedItem().toString()));
        userdemo.setHeight(getNumValue(mHeightTextBox).toString());
        userdemo.setUserWeights(getNumValue(mWeightTextBox).toString());
        //Get/Set skill level
        userdemo.setSkillLevelLevel(mLifterTypeSpinner.getSelectedItem().toString());

        if (mSwitchMeasurementButton.getText().toString().toLowerCase().contains("metric")){
            userdemo.setUnitOfMeasure("Metric");
        } else {
            userdemo.setUnitOfMeasure("Imperial");
        }

        unsavedChanges = false;
        mPresenter.saveData(userdemo);
    }

    public void loadDemographics(){
        if (userdemo==null) return;

        try {
            String first = userdemo.getFirstName();
            String last = userdemo.getLastName();
            if (first != null && last != null)
                mNameTextBox.setText(first + " " + last);
        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        } try {
            mWeightTextBox.setText(userdemo.getUserWeight().toString());
        } catch (Exception e){
            Log.d("PROFILE", e.toString());
            System.out.println(e.toString());
        } try {
            mHeightTextBox.setText(userdemo.getHeight().toString());
        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        } try {
            String dob = userdemo.getDateOfBirth();
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
                    genderAdapter.getPosition(userdemo.getGender()));
        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        }
        try {
            mLifterTypeSpinner
                    .setSelection(lifterTypeAdapter.getPosition(userdemo.getSkillLevelLevel()));
        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        }

        try {
            String unitType = userdemo.getUnitOfMeasure();
            //if weightType is pounds set check, else unchecked
            if (unitType.toLowerCase().contains("imperial")) {
                mSwitchMeasurementButton.setText(R.string.switchMeasureImperial);
            } else {
                mSwitchMeasurementButton.setText(R.string.switchMeasureMetrics);
            }

        } catch (Exception e){
            Log.d("PROFILE", e.toString());
        }

    }

    public void loadUser(){
        if (user==null) return;

        try{
            mEmailTextBox.setText(user.getEmail());
        } catch (Exception e) {
            Log.d("PROFILE", e.toString());
        }
    }

    public void setDemographic(UserDemographic userDemo){ this.userdemo = userDemo; }
    public void setUser(User user){ this.user = user;}

    @Override
    public void setPresenter(ProfileContract.Presenter presenter){ mPresenter = presenter;}

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        unsavedChanges = true;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        int yearsOld;
        if (calendar!=null) {
            Calendar now = Calendar.getInstance();

            yearsOld = getAgeInYears(calendar);
            mDobTextBox.setText(
                    String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(day));
            mAgeTextBox.setText("age:" + String.valueOf(yearsOld));
            return;
        }

        mAgeTextBox.setText("Age");
    }

    public int getAgeInYears(Calendar cal){
        Calendar now = Calendar.getInstance();
        long mili = now.getTimeInMillis() - cal.getTimeInMillis();
        if (mili > 0) {

            mili /= MILLISECONDS_IN_YEAR; //TO YEARS
            int yearsOld = (int) (mili);

            birthday = cal;
            return yearsOld;
        }
        return 0;
    }



}