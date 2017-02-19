package com.fitnation.model;

import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A single Exercise
 */

public class Exercise extends BaseModel {
    private Long id;
    private String name;
    private Set<Muscle> muscles = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Exercise exercise = (Exercise) o;
        if (exercise.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exercise.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Exercise{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
