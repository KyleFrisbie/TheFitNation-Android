package com.fitnation.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.UserExerciseInstanceRealmProxy;

/**
 * Created by Ryan on 3/22/2017.
 */
@Parcel(implementations = {UserExerciseInstanceRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { UserExerciseInstance.class })
public class UserExerciseInstance extends RealmObject implements ExerciseView, Cloneable {
    private Long id;
    private Long userWorkoutInstanceId;
    private Long exerciseInstanceId;
    private String notes;
    @Expose(serialize = false)
    private ExerciseInstance exerciseInstance;
    private RealmList<UserExerciseInstanceSet> userExerciseInstanceSets;

    /**
     * Constructor
     * @param notes - the notes for the exercise
     * @param userWorkoutInstance - The workout instance this exercise belongs to
     * @param exerciseInstance - The exercise instance of whom this a child
     * @param userExerciseInstanceSets - Exercise instance sets
     */
    public UserExerciseInstance(String notes, UserWorkoutInstance userWorkoutInstance, ExerciseInstance exerciseInstance, RealmList<UserExerciseInstanceSet> userExerciseInstanceSets) {
        this.notes = notes;
        this.userWorkoutInstanceId = userWorkoutInstance.getId();
        this.exerciseInstanceId = exerciseInstance.getId();
        this.exerciseInstance = exerciseInstance;
        this.userExerciseInstanceSets = userExerciseInstanceSets;

        for (ExerciseInstanceSet set: exerciseInstance.getExerciseInstanceSets()) {
            userExerciseInstanceSets.add(new UserExerciseInstanceSet(set));
        }
    }

    public UserExerciseInstance(ExerciseInstance exerciseInstance, UserWorkoutInstance userWorkoutInstance) {
        this.notes = exerciseInstance.getNotes();
        this.userWorkoutInstanceId = userWorkoutInstance.getId();
        this.exerciseInstance = exerciseInstance;
        this.exerciseInstanceId = exerciseInstance.getId();
        userExerciseInstanceSets = new RealmList<>();

        for (ExerciseInstanceSet set: exerciseInstance.getExerciseInstanceSets()) {
            userExerciseInstanceSets.add(new UserExerciseInstanceSet(set));
        }
    }

    public UserExerciseInstance() {
        //Required default constructor
    }

    public RealmList<UserExerciseInstanceSet> getUserExerciseInstanceSets() {
        if(userExerciseInstanceSets == null) {
            userExerciseInstanceSets = new RealmList<>();
        }
        return userExerciseInstanceSets;
    }

    @Override
    public List<ExerciseSetView> getExerciseSetView() {
        List<ExerciseSetView> exerciseSetViews = new ArrayList<>();

        for (UserExerciseInstanceSet userExerciseInstanceSet :
                userExerciseInstanceSets) {
            exerciseSetViews.add(userExerciseInstanceSet);
        }

        return exerciseSetViews;
    }

    @Override
    public void setExerciseSetViews(List<ExerciseSetView> sets) {
        userExerciseInstanceSets = new RealmList<>();

        for (ExerciseSetView exerciseSetView :
                sets) {
            userExerciseInstanceSets.add((UserExerciseInstanceSet)exerciseSetView);
        }
    }

    @Override
    public void addExerciseSetView(ExerciseView exercise, int orderNumber) {
        userExerciseInstanceSets.add(new UserExerciseInstanceSet(orderNumber));
    }

    @Override
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean hasExerciseParent() {
        return exerciseInstance.hasExerciseParent();
    }

    @Override
    public String getName() {
        return exerciseInstance.getName();
    }

    @Override
    public String getSkillLevelLevel() {
        return null;
    }

    @Override
    public String getNotes() {
        return null;
    }

    @Override
    public void setSelected(boolean checked) {

    }

    @Override
    public void setParentExercise(Exercise exercise) {
        this.exerciseInstance.setParentExercise(exercise);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public Long getParentExerciseId() {
        return exerciseInstance.getParentExerciseId();
    }

    @Override
    public Object clone() {
        UserExerciseInstance cloned = null;
        try {
            cloned = (UserExerciseInstance) super.clone();
            RealmList<UserExerciseInstanceSet> setsClones = new RealmList<>();
            for (UserExerciseInstanceSet set : userExerciseInstanceSets) {
                UserExerciseInstanceSet clone = (UserExerciseInstanceSet) set.clone();
                setsClones.add(clone);
            }
            cloned.setUserExerciseInstanceSets(setsClones);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return cloned;
    }

    @ParcelPropertyConverter(UserExerciseInstanceSetParcelConverter.class)
    public void setUserExerciseInstanceSets(RealmList<UserExerciseInstanceSet> userExerciseInstanceSets) {
        this.userExerciseInstanceSets = userExerciseInstanceSets;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        UserExerciseInstance exerciseInstance = (UserExerciseInstance) o;
        String nameThis = this.getName();
        String nameOther = exerciseInstance.getName();

        return nameThis.compareTo(nameOther);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserExerciseInstance that = (UserExerciseInstance) o;

        return exerciseInstanceId != null ? exerciseInstanceId.equals(that.exerciseInstanceId) : that.exerciseInstanceId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (exerciseInstanceId != null ? exerciseInstanceId.hashCode() : 0);
        return result;
    }
}
