package com.fitnation.model;

import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A single set of some Exercise
 */
public class ExerciseSet extends RealmObject {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private Integer orderNumber;
    private Integer reps;
    private Float weight;
    private Integer rest;
    private Exercise exercise;

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
            ", orderNumber='" + orderNumber + "'" +
            ", reps='" + reps + "'" +
            ", weight='" + weight + "'" +
            ", rest='" + rest + "'" +
            '}';
    }
}
