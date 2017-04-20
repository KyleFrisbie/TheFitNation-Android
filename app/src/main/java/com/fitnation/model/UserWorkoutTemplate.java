package com.fitnation.model;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.realm.RealmCollection;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A workout template that a mUser owns
 */
public class UserWorkoutTemplate extends RealmObject {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private Date createdOn;
    private WorkoutLog workoutLog;
    private WorkoutTemplate workoutTemplate;
    private RealmList<UserWorkoutInstance> userWorkoutInstances;
    private Date lastUpdated;


    public void setId(Long id) {
        this.id = id;
    }

    public UserWorkoutTemplate() {
        createdOn = new Date();
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
    public String toString() {
        return "UserWorkoutTemplate{" +
            "id=" + id +
            ", createdOn='" + createdOn.toString() + "'" +
            '}';
    }
}
