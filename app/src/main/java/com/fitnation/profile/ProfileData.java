package com.fitnation.profile;

import android.util.Log;

import com.fitnation.model.User;
import com.fitnation.model.UserDemographic;
import com.fitnation.model.UserWeight;
import com.fitnation.networking.UserLogins;

/**
 * Created by Jeremy on 4/11/2017.
 */

public class ProfileData {
    private static final String TAG = ProfileData.class.getSimpleName();
    private String firstName;
    private String lastName;
    private String gender;
    private Float height;
    private String skillLevelLevel;
    private String unitOfMeasure;
    private String dateOfBirth;
    private String email;
    private Float weight;
    boolean hasUserDemographic = false;
    boolean hasUser = false;
    boolean hasWeight = false;

    public ProfileData() {

    }

    public ProfileData(UserDemographic userdemo, User user, UserWeight weight) {
        addUserDemographicInfo(userdemo);
        addUserInfo(user);
        addWeight(weight);
    }

    public void addUserDemographicInfo(UserDemographic userdemo){
        if (userdemo==null) return;
        this.gender = userdemo.getGender();
        this.height = userdemo.getHeight();
        this.skillLevelLevel = userdemo.getSkillLevelLevel();
        this.unitOfMeasure = userdemo.getUnitOfMeasure();
        this.dateOfBirth = userdemo.getDateOfBirth();
        try {
            UserLogins.getInstance().setUserId(userdemo.getUserId().toString());
        } catch (NullPointerException ex) {
            Log.d(TAG, ex.toString());
        }
        try {
            UserLogins.getInstance().setUserDemographicId(userdemo.getId().toString());
        } catch (NullPointerException ex){
            Log.d(TAG, ex.toString());
        }
        try {
            UserLogins.getInstance().setUserLogin(userdemo.getUserLogin());
        } catch (NullPointerException ex){
            Log.d(TAG, ex.toString());
        }

        hasUserDemographic = true;
    }

    public void addUserInfo(User user){
        if (user==null) return;
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        hasUser = true;
    }

    public void addWeight(UserWeight weight) {
        if (weight==null) return;
        this.weight = weight.getWeight();
        hasWeight = true;
    }

    public boolean isFullProfile(){
        return (hasUser && hasUserDemographic && hasWeight);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getSkillLevelLevel() {
        return skillLevelLevel;
    }

    public void setSkillLevelLevel(String skillLevelLevel) {
        this.skillLevelLevel = skillLevelLevel;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }
}
