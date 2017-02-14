package com.fitnation.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ExerciseSet.
 */

public class ExerciseSet implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Integer order_number;
    private Integer reps;
    private Float weight;
    private Integer rest;
    private Exercise exercise;
    private Set<UserExerciseSet> userExerciseSets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrder_number() {
        return order_number;
    }

    public ExerciseSet order_number(Integer order_number) {
        this.order_number = order_number;
        return this;
    }

    public void setOrder_number(Integer order_number) {
        this.order_number = order_number;
    }

    public Integer getReps() {
        return reps;
    }

    public ExerciseSet reps(Integer reps) {
        this.reps = reps;
        return this;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Float getWeight() {
        return weight;
    }

    public ExerciseSet weight(Float weight) {
        this.weight = weight;
        return this;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Integer getRest() {
        return rest;
    }

    public ExerciseSet rest(Integer rest) {
        this.rest = rest;
        return this;
    }

    public void setRest(Integer rest) {
        this.rest = rest;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public ExerciseSet exercise(Exercise exercise) {
        this.exercise = exercise;
        return this;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Set<UserExerciseSet> getUserExerciseSets() {
        return userExerciseSets;
    }

    public ExerciseSet userExerciseSets(Set<UserExerciseSet> userExerciseSets) {
        this.userExerciseSets = userExerciseSets;
        return this;
    }

    public ExerciseSet addUserExerciseSet(UserExerciseSet userExerciseSet) {
        userExerciseSets.add(userExerciseSet);
//        userExerciseSet.setExerciseSet(this);
        return this;
    }

    public ExerciseSet removeUserExerciseSet(UserExerciseSet userExerciseSet) {
        userExerciseSets.remove(userExerciseSet);
//        userExerciseSet.setExerciseSet(null);
        return this;
    }

    public void setUserExerciseSets(Set<UserExerciseSet> userExerciseSets) {
        this.userExerciseSets = userExerciseSets;
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
            ", order_number='" + order_number + "'" +
            ", reps='" + reps + "'" +
            ", weight='" + weight + "'" +
            ", rest='" + rest + "'" +
            '}';
    }
}
