package com.fitnation.domain;

import java.io.Serializable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A WorkoutInstance.
 */
public class WorkoutInstance implements Serializable {
    private Long id;
    private String name;
    private Date created_on;
    private Integer rest_between_instances;
    private Integer order_number;
    private WorkoutTemplate workoutTemplate;
    private Set<UserWorkoutInstance> userWorkoutInstances = new HashSet<>();
    private Set<Exercise> exercises = new HashSet<>();

    public WorkoutInstance addUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        userWorkoutInstances.add(userWorkoutInstance);
        userWorkoutInstance.setWorkoutInstance(this);
        return this;
    }

    public WorkoutInstance removeUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        userWorkoutInstances.remove(userWorkoutInstance);
        userWorkoutInstance.setWorkoutInstance(null);
        return this;
    }

    public WorkoutInstance addExercise(Exercise exercise) {
        exercises.add(exercise);
        exercise.getWorkoutInstances().add(this);
        return this;
    }

    public WorkoutInstance removeExercise(Exercise exercise) {
        exercises.remove(exercise);
        exercise.getWorkoutInstances().remove(this);
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
        WorkoutInstance workoutInstance = (WorkoutInstance) o;
        if (workoutInstance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workoutInstance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkoutInstance{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", created_on='" + created_on + "'" +
            ", rest_between_instances='" + rest_between_instances + "'" +
            ", order_number='" + order_number + "'" +
            '}';
    }

    public void setWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        this.workoutTemplate = workoutTemplate;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }
}
