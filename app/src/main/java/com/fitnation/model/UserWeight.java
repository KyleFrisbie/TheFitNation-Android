package com.fitnation.model;

import android.nfc.Tag;
import android.util.Log;

import com.fitnation.networking.UserLogins;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A single User mUserWeight instance
 */
public class UserWeight extends RealmObject implements Cloneable{
    @PrimaryKey
    private Long androidId;
    private Long id;
    private Long userDemographicId;
    private String weightDate;
    private Float weight;

    public UserWeight() {
        try {
            id = Long.parseLong(UserLogins.getUserDemographicId());
        } catch (Exception ex){
            Log.d("UserWeight", ex.toString());
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        weightDate = dateFormat.format(new Date());
    }

    public void setUserDemographicId(Long id) {
        this.userDemographicId = id;
    }

    public void setWeight(Float weight){
        this.weight = weight;
    }

    public String getWeightDate(){
        return weightDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserWeight userWeight = (UserWeight) o;
        if (userWeight.userDemographicId == null || userDemographicId == null) {
            return false;
        }
        return Objects.equals(userDemographicId, userWeight.userDemographicId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userDemographicId);
    }

    public Float getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "UserWeight{" +
            "id=" + userDemographicId +
            ", weightDate='" + weightDate + "'" +
            ", mUserWeight='" + weight + "'" +
            '}';
    }
    
}
