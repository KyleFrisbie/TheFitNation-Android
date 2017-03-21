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

    public Float getReqQuantity() {
        return reqQuantity;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }
}
