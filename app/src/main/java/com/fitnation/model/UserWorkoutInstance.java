package com.fitnation.model;

import java.util.Date;
import java.util.Objects;

import io.realm.RealmObject;

/**
 * A workout that has/will been/be performed by a User
 */
public class UserWorkoutInstance extends RealmObject {
    private Long id;
    private Date createdOn;
    private Boolean wasCompleted;
    private UserWorkoutTemplate userWorkoutTemplate;
    private WorkoutInstance workoutInstance;

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
