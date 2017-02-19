package com.fitnation.model;

import java.io.Serializable;
import java.util.Objects;

/**
 * A single set of some Exercise
 */
public class ExerciseSet extends BaseModel {
    private Long id;
    private Integer orderNumber;
    private Integer reps;
    private Float weight;
    private Integer rest;
    private Exercise exercise;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ExerciseSet exerciseSet = (ExerciseSet) o;
        if (exerciseSet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, exerciseSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ExerciseSet{" +
            "id=" + id +
            ", order_number='" + orderNumber + "'" +
            ", reps='" + reps + "'" +
            ", weight='" + weight + "'" +
            ", rest='" + rest + "'" +
            '}';
    }
}
