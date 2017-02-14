package com.fitnation.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserExerciseSet.
 */
public class UserExerciseSet implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer order_number;
    private Integer reps;
    private Float weight;
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
}
