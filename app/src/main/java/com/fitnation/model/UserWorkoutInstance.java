package com.fitnation.model;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.UserWorkoutInstanceRealmProxy;

/**
 * A workout that has/will been/be performed by a User
 */
@Parcel(implementations = {UserWorkoutInstanceRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { UserWorkoutInstance.class })
public class UserWorkoutInstance extends RealmObject {
    private Long id;
    private String createdOn;
    private String lastUpdated;
    private Boolean wasCompleted;
    private Long userWorkoutTemplateId;
    private Long workoutInstanceId;
    private String workoutInstanceName;
    private String notes;
    private RealmList<UserExerciseInstance> userExerciseInstances;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @ParcelPropertyConverter(UserExerciseInstanceParcelConverter.class)
    public void setExerciseInstanceSets(RealmList<UserExerciseInstance> userExerciseInstances) {
        this.userExerciseInstances = userExerciseInstances;
    }

    public RealmList<UserExerciseInstance> getUserExerciseInstances() {
        return userExerciseInstances;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserWorkoutInstance userWorkoutInstance = (UserWorkoutInstance) o;
        if (userWorkoutInstance.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, userWorkoutInstance.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public List<ExerciseView> getExerciseViews() {
        List<ExerciseView> exerciseViews = new ArrayList<>(userExerciseInstances.size());
        for (UserExerciseInstance exercise : userExerciseInstances) {
            exerciseViews.add(exercise);
        }

        return exerciseViews;
    }

    @Override
    public String toString() {
        return "UserWorkoutInstance{" +
            "id=" + id +
            '}';
    }
}
