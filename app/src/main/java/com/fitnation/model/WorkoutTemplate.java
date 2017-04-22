package com.fitnation.model;

import com.fitnation.utils.DateFormatter;
import com.google.gson.annotations.Expose;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * A WorkoutTemplate.
 */
public class WorkoutTemplate extends RealmObject implements Cloneable {
    @PrimaryKey
    @Expose(serialize = false)
    private Long androidId;
    private Long id;
    private String name;
    private String notes;
    private String createdOn;
    private String lastUpdated;
    @Expose(serialize = false)
    private Date createdOnObj;
    @Expose(serialize = false)
    private Date lastUpdatedObj;
    private boolean isPrivate;
    private String userDemographicId;
    private Long skillLevelId;
    private String skillLevelLevel;
    @Expose(serialize = false)
    private RealmList<WorkoutInstance> workoutInstances;

    public WorkoutTemplate() {
        createdOnObj = new Date();
        lastUpdatedObj = new Date();
        workoutInstances = new RealmList<>();

        if(createdOnObj != null) {
            createdOn = DateFormatter.getFormattedDate(createdOnObj);
        }

        if(lastUpdatedObj != null) {
            lastUpdated = DateFormatter.getFormattedDate(lastUpdatedObj);
        }
    }

    public void addWorkoutInstance(WorkoutInstance workoutInstance) {
        workoutInstances.add(workoutInstance);
    }

    public String getNotes() {
        return notes;
    }

    public RealmList<WorkoutInstance> getWorkoutInstances() {
        return workoutInstances;
    }

    public Long getSkillLevelId() {
        return skillLevelId;
    }

    public String getSkillLevelLevel() {
        return skillLevelLevel;
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
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "WorkoutTemplate{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", createdOnObj='" + createdOnObj.toString() + "'" +
            ", isPrivate='" + isPrivate + "'" +
            '}';
    }
}
