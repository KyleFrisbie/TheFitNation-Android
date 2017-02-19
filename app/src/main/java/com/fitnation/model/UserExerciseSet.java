package com.fitnation.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A single set of an Exercise that a User has/will perform
 */
public class UserExerciseSet extends BaseModel {
    private Long id;
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
            '}';
    }

    public void setUserExercise(UserExercise userExercise) {
        this.userExercise = userExercise;
    }

    public void setExerciseSet(ExerciseSet exerciseSet) {
        this.exerciseSet = exerciseSet;
    }
}
