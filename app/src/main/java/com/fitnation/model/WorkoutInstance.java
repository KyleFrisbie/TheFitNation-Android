package com.fitnation.model;

import com.google.gson.annotations.Expose;

import java.text.SimpleDateFormat;
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
    @Expose(serialize = false)
    private Long androidId;
    private Long id;
    private String name;
    private String createdOn;
    private String lastUpdated;
    @Expose(serialize = false)
    private Date createdOnObj;
    @Expose(serialize = false)
    private Date lastUpdatedObj;
    private Float restBetweenInstances;
    private Integer orderNumber;
    private Long workoutTemplateId;
    private String workoutTemplateName;
    private RealmList<ExerciseInstance> exerciseInstances;
    private String notes;

    public WorkoutInstance() {
        createdOnObj = new Date();
        lastUpdatedObj = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        if(createdOnObj != null) {
            createdOn = dateFormat.format(createdOnObj);
        }

        if(lastUpdatedObj != null) {
            lastUpdated = dateFormat.format(lastUpdatedObj);
        }
    }

    public WorkoutInstance(String name, Float restBetweenInstances, Integer orderNumber, WorkoutTemplate workoutTemplate, String notes) {
        createdOnObj = new Date();
        lastUpdatedObj = new Date();
        this.name = name;
        this.restBetweenInstances = restBetweenInstances;
        this.orderNumber = orderNumber;
        this.workoutTemplateId = workoutTemplate.getId();
        this.workoutTemplateName = workoutTemplate.getName();
        this.notes = notes;
        SimpleDateFormat dateFormat = new SimpleDateFormat ("yyyy-MM-dd");
        createdOn = dateFormat.format(createdOnObj);
        lastUpdated = dateFormat.format(lastUpdatedObj);
    }

    public Long getAndroidId() {
        return androidId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public Date getCreatedOnObj() {
        return createdOnObj;
    }

    public Date getLastUpdatedObj() {
        return lastUpdatedObj;
    }

    public Float getRestBetweenInstances() {
        return restBetweenInstances;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public Long getWorkoutTemplateId() {
        return workoutTemplateId;
    }

    public String getWorkoutTemplateName() {
        return workoutTemplateName;
    }

    public RealmList<ExerciseInstance> getExerciseInstances() {
        return exerciseInstances;
    }

    public String getNotes() {
        return notes;
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
