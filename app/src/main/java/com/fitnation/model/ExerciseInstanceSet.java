package com.fitnation.model;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * An Exercise Instance Termplate's set
 */
public class ExerciseInstanceSet extends RealmObject implements Cloneable{
    private Long id;
    private Long androidId;
    private Integer orderNumber;
    private Float repQuantity;
    private Float effortQuantity;
    private Float restTime;
    private Long exerciseInstanceId;
    private String notes;

    /**
     * @param exerciseInstance - the exercise this set belongs to
     * @param orderNumber - the set's order
     * @param reqQuantity - the reps to do
     * @param effortQuantity - Weight, Time, etc.
     * @param restTime - The rest time the user should take
     */
    public ExerciseInstanceSet(ExerciseInstance exerciseInstance, Integer orderNumber, Float reqQuantity, Float effortQuantity, Float restTime) {
        this.exerciseInstanceId = exerciseInstance.getId();
        this.orderNumber = orderNumber;
        this.repQuantity = reqQuantity;
        this.effortQuantity = effortQuantity;
        this.restTime = restTime;
    }

    /**
     * Constructor, will set default rep, effort, and rest time
     * @param exerciseInstance
     * @param orderNumber - order of the set compared to the others
     */
    public ExerciseInstanceSet(ExerciseInstance exerciseInstance, Integer orderNumber) {
        this.exerciseInstanceId = exerciseInstance.getId();
        this.orderNumber = orderNumber;
        repQuantity = ExerciseInstance.REPS_DEFAULT;
        effortQuantity = ExerciseInstance.EFFORT_DEFAULT;
        restTime = ExerciseInstance.REST_TIME_DEFAULT;
    }

    public ExerciseInstanceSet() {
        //required default constructor
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
