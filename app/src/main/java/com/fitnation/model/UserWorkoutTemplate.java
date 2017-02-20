package com.fitnation.model;


import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A workout template that a user owns
 */
public class UserWorkoutTemplate extends BaseModel {
    private Long id;
    private Date createdOn;
    private WorkoutLog workoutLog;
    private WorkoutTemplate workoutTemplate;
    private Set<UserWorkoutInstance> userWorkoutInstances = new HashSet<>();

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
