package com.fitnation.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A UserExercise.
 */
public class UserExercise implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private UserWorkoutInstance userWorkoutInstance;
    private Exercise exercise;
    private Set<UserExerciseSet> userExerciseSets = new HashSet<>();

    public UserExercise addUserExerciseSet(UserExerciseSet userExerciseSet) {
        userExerciseSets.add(userExerciseSet);
//        userExerciseSet.setUserExercise(this);
        return this;
    }

    public UserExercise removeUserExerciseSet(UserExerciseSet userExerciseSet) {
        userExerciseSets.remove(userExerciseSet);
//        userExerciseSet.setUserExercise(null);
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
