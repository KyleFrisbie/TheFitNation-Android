package com.fitnation.model;

import java.util.Date;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A workout that has been performed by the User
 */
public class WorkoutInstance extends RealmObject {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private String name;
    private Date createdOn;
    private Date lastUpdated;
    private Float restBetweenInstances;
    private Integer orderNumber;
    private WorkoutTemplate workoutTemplate;
    private RealmList<UserWorkoutInstance> userWorkoutInstances;
    private RealmList<ExerciseInstance> exerciseInstances ;
    private String notes;

    public WorkoutInstance() {
        createdOn = new Date();
        lastUpdated = new Date();
    }

    public WorkoutInstance(String name, Float restBetweenInstances, Integer orderNumber, WorkoutTemplate workoutTemplate, String notes) {
        createdOn = new Date();
        lastUpdated = new Date();
        this.name = name;
        this.restBetweenInstances = restBetweenInstances;
        this.orderNumber = orderNumber;
        this.workoutTemplate = workoutTemplate;
        this.notes = notes;
    }

    public void setAndroidId(Long androidId) {
        this.androidId = androidId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setExercises(RealmList<ExerciseInstance> exercises) {
        this.exerciseInstances  = exercises;
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
            ", createdOn='" + createdOn.toString() + "'" +
            ", restBetweenInstances='" + restBetweenInstances + "'" +
            ", orderNumber='" + orderNumber + "'" +
            '}';
    }
}
