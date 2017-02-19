package com.fitnation.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Exercise a User has taken ownership of
 */
public class UserExercise extends BaseModel {
    private Long id;
    private Exercise exercise;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserExercise userExercise = (UserExercise) o;
        if (userExercise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userExercise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserExercise{" +
            "id=" + id +
            '}';
    }
}

