package com.fitnation.model;

import com.fitnation.model.enums.Gender;
import com.fitnation.model.enums.SkillLevel;
import com.fitnation.model.enums.UnitOfMeasure;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Information about a user and their data
 */
public class UserDemographic extends RealmObject {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;
    private Date dob;
    private Integer height;
    private String skillLevel;
    private String unitOfMeasure;
    private Boolean isActive;
    private RealmList<Gym> gyms;
    private RealmList<UserWeight> userWeights;
    private WorkoutLog workoutLog;
    private RealmList<WorkoutTemplate> workoutTemplates;

    public UserDemographic() {
        dob = new Date();
        skillLevel = SkillLevel.BEGINNER;
        unitOfMeasure = UnitOfMeasure.IMPERIAL;
    }

    public void setId(Long id) {
        androidId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDemographic that = (UserDemographic) o;

        if (androidId != null ? !androidId.equals(that.androidId) : that.androidId != null)
            return false;
        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        int result = androidId != null ? androidId.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    public void setFirstName(String pName){
        firstName = pName;
    }

    public String getFirstName(){
        return firstName;
    }

    public void setLastName(String pName){
        lastName = pName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setGender(String pGender){
        gender = pGender;
    }

    public String getGender(){
        return gender;
    }

    public void setDob(Date pDob){
        dob = pDob;
    }

    public Date getDob(){
        return dob;
    }

    public void setHeight(Integer pHeight){
        height = pHeight;
    }

    public Integer getHeight(){
        return height;
    }

    public void setUserWeights(RealmList<UserWeight> pWeights){
        userWeights = pWeights;
    }

    public RealmList<UserWeight> getUserWeight(){
        return userWeights;
    }

    public void setSkillLevel(String pSkill){
        skillLevel = pSkill;
    }

    public String getSkillLevel(){
        return skillLevel;
    }

    public void setUnitOfMeasure(String pUnit){
        unitOfMeasure = pUnit;
    }

    public String getUnitOfMeasure(){
        return unitOfMeasure;
    }



    @Override
    public String toString() {
        return "UserDemographic{" +
            "id=" + id +
            ", first_name='" + firstName + "'" +
            ", last_name='" + lastName + "'" +
            ", gender='" + gender + "'" +
            ", dob='" + dob + "'" +
            ", height='" + height + "'" +
            ", skill_level='" + skillLevel + "'" +
            ", unit_of_measure='" +  unitOfMeasure + "'" +
            ", is_active='" + isActive + "'" +
            '}';
    }
}
