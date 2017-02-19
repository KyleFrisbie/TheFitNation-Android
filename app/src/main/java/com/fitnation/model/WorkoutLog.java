package com.fitnation.model;

import java.io.Serializable;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WorkoutLog.
 */
public class WorkoutLog extends BaseModel {
    private Long id;
    private Set<UserWorkoutTemplate> userWorkoutTemplates = new HashSet<>();

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
            '}';
    }
}
