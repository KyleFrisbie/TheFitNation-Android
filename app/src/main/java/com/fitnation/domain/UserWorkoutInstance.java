package com.fitnation.domain;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserWorkoutInstance.
 */
public class UserWorkoutInstance implements Serializable {
    private Long id;
    private Date created_on;
    private Boolean was_completed;
    private UserWorkoutTemplate userWorkoutTemplate;
    private WorkoutInstance workoutInstance;
    private Set<UserExercise> userExercises = new HashSet<>();

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
            ", created_on='" + created_on + "'" +
            ", was_completed='" + was_completed + "'" +
            '}';
    }
}
