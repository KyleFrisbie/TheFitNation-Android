package com.fitnation.model;

import java.io.Serializable;


import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Ryan on 3/21/2017.
 */
public class ExerciseInstance extends RealmObject implements Serializable {
    private Long id;
    private Long androidId;
    private String notes;
    private WorkoutInstance workoutInstance;
    private RealmList<ExerciseInstanceSet> exerciseInstanceSets;
    private Exercise exercise;
    private Unit repUnit;
    private Unit effortUnit;
    private boolean selected;

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

    public Long getId() {
        return id;
    }
}
