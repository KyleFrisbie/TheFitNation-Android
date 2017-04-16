package com.fitnation.model;

import android.support.annotation.NonNull;

import java.util.Objects;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A single set of an Exercise that a User has/will perform
 */
public class UserExerciseInstanceSet extends RealmObject implements ExerciseSetView {
    @PrimaryKey
    private Long androidId;
    private Long id;
    private Integer orderNumber;
    private Float reqQuantity;
    private Float effortQuantity;
    private Float restTime;
    private String notes;
    private Long exerciseInstanceSetId;

    /**
     * Constructor
     * @param androidId
     * @param id
     * @param orderNumber
     * @param reqQuantity
     * @param effortQuantity
     * @param restTime
     * @param notes
     * @param exerciseInstanceSet
     */
    public UserExerciseInstanceSet(Long androidId, Long id, Integer orderNumber, Float reqQuantity, Float effortQuantity, Float restTime, String notes, ExerciseInstanceSet exerciseInstanceSet) {
        this.androidId = androidId;
        this.id = id;
        this.orderNumber = orderNumber;
        this.reqQuantity = reqQuantity;
        this.effortQuantity = effortQuantity;
        this.restTime = restTime;
        this.notes = notes;
        this.exerciseInstanceSetId = exerciseInstanceSet.getId();
    }

    public UserExerciseInstanceSet(ExerciseInstanceSet exerciseInstanceSet) {
        this.exerciseInstanceSetId = exerciseInstanceSet.getId();
        this.orderNumber = exerciseInstanceSet.getOrderNumber();
        this.reqQuantity = exerciseInstanceSet.getReqQuantity();
        this.effortQuantity = exerciseInstanceSet.getEffortQuantity();
        this.restTime = exerciseInstanceSet.getRestTime();
        this.notes = exerciseInstanceSet.getNotes();

    }

    public UserExerciseInstanceSet() {
        //default constructor
    }

    public UserExerciseInstanceSet(int orderNumber) {
        this.orderNumber = orderNumber;
        reqQuantity = ExerciseInstance.REPS_DEFAULT;
        effortQuantity = ExerciseInstance.EFFORT_DEFAULT;
        restTime = ExerciseInstance.REST_TIME_DEFAULT;
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

    @Override
    public Integer getOrderNumber() {
        return orderNumber;
    }

    @Override
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public Integer getRepQuantityAsInt() {
        return Math.round(reqQuantity);
    }

    @Override
    public Float getEffortQuantity() {
        return effortQuantity;
    }

    @Override
    public Float getRestTime() {
        return restTime;
    }

    @Override
    public void setReqQuantity(Float reqQuantity) {
        this.reqQuantity = reqQuantity;
    }

    @Override
    public void setEffortQuantity(Float effortQuantity) {
        this.effortQuantity = effortQuantity;
    }

    @Override
    public void setRestTime(Float restTime) {
        this.restTime = restTime;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        UserExerciseInstanceSet otherInstanceSet = (UserExerciseInstanceSet) o;

        return this.orderNumber - otherInstanceSet.getOrderNumber();
    }
}
