package com.fitnation.model;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.realm.RealmCollection;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A WorkoutLog.
 */
public class WorkoutLog extends RealmObject {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private RealmList<UserWorkoutTemplate> userWorkoutTemplates;

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
