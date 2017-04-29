package com.fitnation.model;


import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.Date;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.UserWorkoutTemplateRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * A workout template that a mUser owns
 */
@Parcel(implementations = {UserWorkoutTemplateRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {UserWorkoutTemplate.class})
public class UserWorkoutTemplate extends RealmObject implements Cloneable {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private Date createdOn;
    private WorkoutLog workoutLog;
    private WorkoutTemplate workoutTemplate;
    private RealmList<UserWorkoutInstance> userWorkoutInstances;
    private Date lastUpdated;

    public UserWorkoutTemplate() {
        createdOn = new Date();
    }

    public void setAndroidId(Long androidId) {
        this.androidId = androidId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RealmList<UserWorkoutInstance> userWorkoutInstances() {
        return userWorkoutInstances;
    }

    @ParcelPropertyConverter(UserWorkoutInstanceParcelConverter.class)
    public void setUserWorkoutInstances(RealmList<UserWorkoutInstance> userWorkoutInstances) {
        this.userWorkoutInstances = userWorkoutInstances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserWorkoutTemplate userWorkoutTemplate = (UserWorkoutTemplate) o;
        if (userWorkoutTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userWorkoutTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "UserWorkoutTemplate{" +
            "id=" + id +
            ", createdOn='" + createdOn.toString() + "'" +
            '}';
    }
}
