package com.fitnation.model;

import java.io.Serializable;


import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Ryan on 3/21/2017.
 */
public class ExerciseInstance extends RealmObject implements Serializable, Cloneable {
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

    public ExerciseInstance(Exercise exercise) {
        this.exercise = exercise;
        exerciseInstanceSets = new RealmList<>();
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 1, 8f, null));
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 2, 8f, null));
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 3, 8f, null));
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 4, 8f, null));
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
