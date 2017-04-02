package com.fitnation.model;

import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A single set of an Exercise that a User has/will perform
 */
public class UserExerciseInstanceSet extends RealmObject {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private UserExerciseInstance userExerciseInstance;
    private Integer orderNumber;
    private Float repQuantity;
    private Float effortQuantity;
    private Float restTime;
    private String notes;
    private Long exerciseInstanceSetId;

    /**
     * Constructor
     * @param androidId
     * @param id
     * @param userExerciseInstance
     * @param orderNumber
     * @param repQuantity
     * @param effortQuantity
     * @param restTime
     * @param notes
     * @param exerciseInstanceSet
     */
    public UserExerciseInstanceSet(Long androidId, Long id, UserExerciseInstance userExerciseInstance, Integer orderNumber, Float repQuantity, Float effortQuantity, Float restTime, String notes, ExerciseInstanceSet exerciseInstanceSet) {
        this.androidId = androidId;
        this.id = id;
        this.userExerciseInstance = userExerciseInstance;
        this.orderNumber = orderNumber;
        this.repQuantity = repQuantity;
        this.effortQuantity = effortQuantity;
        this.restTime = restTime;
        this.notes = notes;
        this.exerciseInstanceSetId = exerciseInstanceSet.getId();
    }

    public UserExerciseInstanceSet(ExerciseInstanceSet exerciseInstanceSet, UserExerciseInstance exerciseInstance) {
        this.exerciseInstanceSetId = exerciseInstanceSet.getId();
        this.userExerciseInstance = exerciseInstance;
        this.orderNumber = exerciseInstanceSet.getOrderNumber();
        this.repQuantity = exerciseInstanceSet.getRepQuantity();
        this.effortQuantity = exerciseInstanceSet.getEffortQuantity();
        this.restTime = exerciseInstanceSet.getRestTime();
        this.notes = ""; //TODO implement this field down the road for custom notes per set

    }

    public UserExerciseInstanceSet() {
        //default constructor
    }

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
        UserExerciseInstanceSet userExerciseInstanceSet = (UserExerciseInstanceSet) o;
        if (userExerciseInstanceSet.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userExerciseInstanceSet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "UserExerciseInstanceSet{" +
            "id=" + id +
            '}';
    }
}
