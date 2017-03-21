package com.fitnation.model;

import io.realm.RealmObject;

/**
 * Created by Ryan on 3/21/2017.
 */

public class ExerciseInstanceSet extends RealmObject{
    private Long id;
    private Long androidId;
    private Integer orderNumber;
    private Float reqQuantity;
    private Float effortQuantity;
    private Float restTime;

    /**
     * Constructor
     * @param orderNumber - the set's order
     * @param reqQuantity - the reps to do
     */
    public ExerciseInstanceSet(Integer orderNumber, Float reqQuantity) {
        this.orderNumber = orderNumber;
        this.reqQuantity = reqQuantity;
    }

    public Float getReqQuantity() {
        return reqQuantity;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }
}
