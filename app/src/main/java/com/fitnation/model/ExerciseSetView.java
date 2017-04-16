package com.fitnation.model;

/**
 * Created by Ryan on 4/15/2017.
 */

public interface ExerciseSetView extends Comparable{
    Integer getOrderNumber();
    void setOrderNumber(Integer orderNumber);
    Integer getRepQuantityAsInt();
    Float getEffortQuantity();
    Float getRestTime();
    void setReqQuantity(Float reqQuantity);
    void setEffortQuantity(Float effortQuantity);
    void setRestTime(Float restTime);
}
