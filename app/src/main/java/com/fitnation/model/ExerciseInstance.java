package com.fitnation.model;

import java.io.Serializable;
import java.util.List;


import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Ryan on 3/21/2017.
 */
public class ExerciseInstance extends RealmObject implements Serializable, Cloneable {
    public static final Float REPS_DEFAULT = 8f;
    public static final Float EFFORT_DEFAULT = 20f;
    public static final Float REST_TIME_DEFAULT = 30f;

    private Long id;
    private Long androidId;
    private String notes;
    private RealmList<ExerciseInstanceSet> exerciseInstanceSets;
    private Exercise exercise;
    private Unit repUnit;
    private Unit effortUnit;
    private boolean selected;

    public ExerciseInstance() {

    }

    /**
     * Constructor
     * @param exercise - exercise to have an instance created out of
     */
    public ExerciseInstance(Exercise exercise) {
        this.exercise = exercise;
        exerciseInstanceSets = new RealmList<>();
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 1, REPS_DEFAULT, EFFORT_DEFAULT, REST_TIME_DEFAULT));
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 2, REPS_DEFAULT, EFFORT_DEFAULT, REST_TIME_DEFAULT));
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 3, REPS_DEFAULT, EFFORT_DEFAULT, REST_TIME_DEFAULT));
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 4, REPS_DEFAULT, EFFORT_DEFAULT, REST_TIME_DEFAULT));
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public RealmList<ExerciseInstanceSet> getExerciseInstanceSets() {
        return exerciseInstanceSets;
    }

    public void setExerciseInstanceSets(List<ExerciseInstanceSet> exerciseInstanceSets) {
        this.exerciseInstanceSets = new RealmList<ExerciseInstanceSet>(exerciseInstanceSets.toArray(new ExerciseInstanceSet[exerciseInstanceSets.size()]));

    }

    public Exercise getExercise() {
        return exercise;
    }

    public String getNotes() {
        return notes;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Long getId() {
        return id;
    }
}
