package com.fitnation.model;

import org.parceler.Parcel;

import java.util.List;

import io.realm.ExerciseInstanceSetRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * An Exercise Instance Termplate's set
 */
@Parcel(implementations = { ExerciseInstanceSetRealmProxy.class },
        value = Parcel.Serialization.BEAN,
        analyze = { ExerciseInstanceSet.class })
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

    public void setRepQuantity(Float repQuantity) {
        this.repQuantity = repQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
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

    public int getRepQuantityAsInt() {
        return Math.round(repQuantity);
    }

    public void setRestTime(Float restTime) {
        this.restTime = restTime;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setEffortQuantity(Float effortQuantity) {
        this.effortQuantity = effortQuantity;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseInstanceSet that = (ExerciseInstanceSet) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (androidId != null ? !androidId.equals(that.androidId) : that.androidId != null)
            return false;
        if (orderNumber != null ? !orderNumber.equals(that.orderNumber) : that.orderNumber != null)
            return false;
        if (repQuantity != null ? !repQuantity.equals(that.repQuantity) : that.repQuantity != null)
            return false;
        if (effortQuantity != null ? !effortQuantity.equals(that.effortQuantity) : that.effortQuantity != null)
            return false;
        if (restTime != null ? !restTime.equals(that.restTime) : that.restTime != null)
            return false;
        if (exerciseInstanceId != null ? !exerciseInstanceId.equals(that.exerciseInstanceId) : that.exerciseInstanceId != null)
            return false;
        return notes != null ? notes.equals(that.notes) : that.notes == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (androidId != null ? androidId.hashCode() : 0);
        result = 31 * result + (orderNumber != null ? orderNumber.hashCode() : 0);
        result = 31 * result + (repQuantity != null ? repQuantity.hashCode() : 0);
        result = 31 * result + (effortQuantity != null ? effortQuantity.hashCode() : 0);
        result = 31 * result + (restTime != null ? restTime.hashCode() : 0);
        result = 31 * result + (exerciseInstanceId != null ? exerciseInstanceId.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        return result;
    }
}
