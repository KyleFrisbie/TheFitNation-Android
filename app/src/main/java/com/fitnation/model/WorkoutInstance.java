package com.fitnation.model;

import android.support.annotation.NonNull;

import com.fitnation.utils.DateFormatter;
import com.google.gson.annotations.Expose;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.WorkoutInstanceRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * A workout that has been performed by the User
 */
@Parcel(implementations = {WorkoutInstanceRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { WorkoutInstance.class })
public class WorkoutInstance extends RealmObject implements Cloneable, Comparable, WorkoutView {
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

    @Override
    public String getCreatedOn() {
        return DateFormatter.getUIDate(createdOn);
    }

    public WorkoutInstance (List<ExerciseInstance> exerciseInstances, String name) {
        this.exerciseInstances = new RealmList<>();

        for (ExerciseInstance exerciseInstance : exerciseInstances) {
            this.exerciseInstances.add(exerciseInstance);
        }

        this.name = name;
        this.restBetweenInstances = 0f;
        this.orderNumber = 1;
        this.notes = "";
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

    public static List<WorkoutView> convertWorkoutsToWorkoutViews(List<WorkoutInstance> workoutInstances) {
        List<WorkoutView> workoutViews = null;

        if (workoutInstances != null) {
            workoutViews = new ArrayList<>();
            for (WorkoutInstance workoutInstance : workoutInstances) {
                workoutViews.add(workoutInstance);
            }
        }

        return workoutViews;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Long getWorkoutTemplateId() {
        return workoutTemplateId;
    }

    public String getName() {
        return name;
    }

    public String getNotes() {
        return notes;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    @ParcelPropertyConverter(ExerciseInstanceParcelConverter.class)
    public void setExercises(RealmList<ExerciseInstance> exercises) {
        this.exerciseInstances  = exercises;
    }

    public RealmList<ExerciseInstance> getExerciseInstances() {
        return exerciseInstances;
    }

    public List<ExerciseView> getExerciseViews() {
        List<ExerciseView> exerciseViews = new ArrayList<>();
        for (ExerciseInstance exerciseInstance : exerciseInstances) {
            exerciseViews.add(exerciseInstance);
        }

        return exerciseViews;
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

    public Long getAndroidId() {
        return androidId;
    }

    public void setAndroidId(Long androidId) {
        this.androidId = androidId;
    }

    public void setWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        this.workoutTemplateId = workoutTemplate.getId();
        this.workoutTemplateName = workoutTemplate.getName();
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}