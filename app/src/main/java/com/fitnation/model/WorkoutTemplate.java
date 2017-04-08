package com.fitnation.model;

import com.fitnation.model.enums.SkillLevel;
import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A WorkoutTemplate.
 */
public class WorkoutTemplate extends RealmObject {
    @PrimaryKey
    @Expose(serialize = false)
    private Long androidId;
    private Long id;
    private String name;
    private String notes;
    private Date createdOn;
    private Date lastUpdated;
    private Boolean isPrivate;
    private String userDemographicId;
    private Long skillLevelId;
    private String skillLevelLevel;
    @Expose(serialize = false)
    private RealmList<WorkoutInstance> workoutInstances;

    public WorkoutTemplate() {
        createdOn = new Date();
        lastUpdated = new Date();
        workoutInstances = new RealmList<>();
    }

    public void addWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.add(workoutInstance);
    }

    public RealmList<WorkoutInstance> getWorkoutInstances() {
        return workoutInstances;
    }

    public void setUserDemographicId(String userDemographicId) {
        this.userDemographicId = userDemographicId;
    }

    public void setSkillLevelId(Long skillLevelId) {
        this.skillLevelId = skillLevelId;
    }

    public void setSkillLevelLevel(String skillLevelLevel) {
        this.skillLevelLevel = skillLevelLevel;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAndroidId(Long androidId) {
        this.androidId = androidId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkoutTemplate workoutTemplate = (WorkoutTemplate) o;
        if (workoutTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workoutTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkoutTemplate{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", createdOn='" + createdOn.toString() + "'" +
            ", isPrivate='" + isPrivate + "'" +
            '}';
    }
}
