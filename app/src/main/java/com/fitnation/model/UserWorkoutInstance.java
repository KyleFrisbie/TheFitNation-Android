package com.fitnation.model;

import org.parceler.Parcel;

import java.util.Date;
import java.util.Objects;

import io.realm.RealmObject;
import io.realm.UserWorkoutInstanceRealmProxy;

/**
 * A workout that has/will been/be performed by a User
 */
@Parcel(implementations = {UserWorkoutInstanceRealmProxy.class}, value = Parcel.Serialization.BEAN, analyze = {UserWorkoutInstance.class})
public class UserWorkoutInstance extends RealmObject {
    private Long id;
    private Date createdOn;
    private Boolean wasCompleted;
    private Long userWorkoutTemplateId;
    private Long workoutInstanceId;

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserWorkoutInstance userWorkoutInstance = (UserWorkoutInstance) o;
        if (userWorkoutInstance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userWorkoutInstance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserWorkoutInstance{" +
            "id=" + id +
            '}';
    }
}
