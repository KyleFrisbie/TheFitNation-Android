package com.fitnation.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Muscle.
 */
public class Muscle implements Serializable {
    private Long id;
    private String name;

    private Set<Exercise> exercises = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Muscle name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }

    public Muscle exercises(Set<Exercise> exercises) {
        this.exercises = exercises;
        return this;
    }

    public Muscle addExercise(Exercise exercise) {
        exercises.add(exercise);
        exercise.getMuscles().add(this);
        return this;
    }

    public Muscle removeExercise(Exercise exercise) {
        exercises.remove(exercise);
        exercise.getMuscles().remove(this);
        return this;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Muscle muscle = (Muscle) o;
        if (muscle.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, muscle.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Muscle{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}
