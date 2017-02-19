package com.fitnation.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserExerciseSet.
 */
public class UserExerciseSet implements Serializable {
    private Long id;
    private Integer order_number;
    private Integer reps;
    private Float weight; //TODO these values are in exercise set, why are they being duplicated
    private Integer rest;
    private UserExercise userExercise;
    private ExerciseSet exerciseSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserExerciseSet userExerciseSet = (UserExerciseSet) o;
        if (userExerciseSet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userExerciseSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserExerciseSet{" +
            "id=" + id +
            ", order_number='" + order_number + "'" +
            ", reps='" + reps + "'" +
            ", weight='" + weight + "'" +
            ", rest='" + rest + "'" +
            '}';
    }

    public void setUserExercise(UserExercise userExercise) {
        this.userExercise = userExercise;
    }

    public void setExerciseSet(ExerciseSet exerciseSet) {
        this.exerciseSet = exerciseSet;
    }
}
