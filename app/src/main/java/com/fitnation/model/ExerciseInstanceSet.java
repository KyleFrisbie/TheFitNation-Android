package com.fitnation.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * An Exercise Instance Termplate's set
 */
public class ExerciseInstanceSet extends RealmObject{
    private Long id;
    private Long androidId;
    private Integer orderNumber;
    private Float repQuantity;
    private Float effortQuantity;
    private Float restTime;
    private ExerciseInstance exerciseInstance;
    private String notes;

    /**
     * @param exerciseInstance - the exercise this set belongs to
     * @param orderNumber - the set's order
     * @param reqQuantity - the reps to do
     * @param effortQuantity - Weight, Time, etc.
     * @param restTime - The rest time the user should take
     */
    public ExerciseInstanceSet(ExerciseInstance exerciseInstance, Integer orderNumber, Float reqQuantity, Float effortQuantity, Float restTime) {
        this.exerciseInstance = exerciseInstance;
        this.orderNumber = orderNumber;
        this.repQuantity = reqQuantity;
        this.effortQuantity = effortQuantity;
        this.restTime = restTime;
    }

    public ExerciseInstanceSet() {
        //required default constructor
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExerciseInstance getExerciseInstance() {
        return exerciseInstance;
    }

    public Float getEffortQuantity() {
        return effortQuantity;
    }

    public Float getRestTime() {
        return restTime;
    }

    public Float getRepQuantity() {
        return repQuantity;
    }

    public int getReqQuantityAsInt() {
        return Math.round(repQuantity);
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }
}
