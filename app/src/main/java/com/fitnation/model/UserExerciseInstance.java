package com.fitnation.model;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.UserExerciseInstanceRealmProxy;

/**
 * Created by Ryan on 3/22/2017.
 */
@Parcel(implementations = {UserExerciseInstanceRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { UserExerciseInstance.class })
public class UserExerciseInstance extends RealmObject implements ExerciseView {
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
    public boolean isSelected() {
        return false;
    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    @Override
    public String getName() {
        return exerciseInstance.getName();
    }

    @Override
    public void setSelected(boolean checked) {

    }

    @Override
    public Long getId() {
        return id;
    }
}
