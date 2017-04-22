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
 * A workout template that a user owns
 */
@Parcel(implementations = {UserWorkoutTemplateRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { UserWorkoutTemplate.class })
public class UserWorkoutTemplate extends RealmObject {
    @PrimaryKey
    @Expose(serialize = false)
    private Long androidId;
    private Long id;
    private String createdOn;
    private String lastUpdated;
    private boolean isPrivate;
    private String notes;
    private String skillLevelLevel;
    private Long skillLevelId;
    private String name;


    public void setId(Long id) {
        this.id = id;
    }

    public UserWorkoutTemplate() {

    }

    public UserWorkoutTemplate(WorkoutTemplate workoutTemplate) {
        skillLevelLevel = workoutTemplate.getSkillLevelLevel();
        skillLevelId = workoutTemplate.getSkillLevelId();
        name = workoutTemplate.getName();
    }

    public void initAndroidId() {
        androidId = PrimaryKeyFactory.getInstance().nextKey(UserWorkoutTemplate.class);
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
