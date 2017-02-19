package com.fitnation.domain;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WorkoutLog.
 */
public class WorkoutLog implements Serializable {
    private Long id;
    private Date created_on; //TODO Seems redundant
    private UserDemographic userDemographic; //TODO circular reference
    private Set<UserWorkoutTemplate> userWorkoutTemplates = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public WorkoutLog addUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        userWorkoutTemplates.add(userWorkoutTemplate);
        userWorkoutTemplate.setWorkoutLog(this);
        return this;
    }

    public WorkoutLog removeUserWorkoutTemplate(UserWorkoutTemplate userWorkoutTemplate) {
        userWorkoutTemplates.remove(userWorkoutTemplate);
        userWorkoutTemplate.setWorkoutLog(null);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutLog workoutLog = (WorkoutLog) o;
        if (workoutLog.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workoutLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkoutLog{" +
            "id=" + id +
            ", created_on='" + created_on + "'" +
            '}';
    }
}
