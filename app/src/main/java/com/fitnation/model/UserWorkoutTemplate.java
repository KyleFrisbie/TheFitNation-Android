package com.fitnation.model;


import com.fitnation.utils.PrimaryKeyFactory;
import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.realm.RealmCollection;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.UserWorkoutTemplateRealmProxy;
import io.realm.annotations.PrimaryKey;

/**
 * A workout template that a mUser owns
 */
@Parcel(implementations = {UserWorkoutTemplateRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { UserWorkoutTemplate.class })
public class UserWorkoutTemplate extends RealmObject {
    @PrimaryKey
    @Expose(serialize = false)
    private Long androidId;
    private Long id;
    private String createdOn;
    private String lastUpdated;
    private String notes;
    private String workoutTemplateName;
    private Long workoutTemplateId;

    public UserWorkoutTemplate() {

    }

    public UserWorkoutTemplate(WorkoutTemplate workoutTemplate, long androidId) {
        workoutTemplateId = workoutTemplate.getId();
        workoutTemplateName = workoutTemplate.getName();
        createdOn = workoutTemplate.getCreatedOn();
        lastUpdated = workoutTemplate.getLastUpdated();
        this.androidId = androidId;
    }

    public static long getNextAndroidIdForClass() {
        return PrimaryKeyFactory.getInstance().nextKey(UserWorkoutTemplate.class);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getAndroidId() {
        return androidId;
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
        UserWorkoutTemplate userWorkoutTemplate = (UserWorkoutTemplate) o;
        if (userWorkoutTemplate.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userWorkoutTemplate.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
