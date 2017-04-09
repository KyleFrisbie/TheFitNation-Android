package com.fitnation.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.io.Serializable;
import java.util.List;


import io.realm.ExerciseInstanceRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Ryan on 3/21/2017.
 */
@Parcel(implementations = { ExerciseInstanceRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { ExerciseInstance.class })
public class ExerciseInstance extends RealmObject implements Cloneable {
    public static final Float REPS_DEFAULT = 8f;
    public static final Float EFFORT_DEFAULT = 20f;
    public static final Float REST_TIME_DEFAULT = 30f;

    private Long id;
    private Long androidId;
    private Long exerciseId;
    private Long repUnitId;
    private Long effortUnitId;
    private String exerciseName;
    private String notes;
    private String repUnitName;
    private String effortUnitName;
    @Expose(serialize = false)
    private Exercise exercise;
    @Expose(serialize = false)
    private Unit repUnit;
    @Expose(serialize = false)
    private Unit effortUnit;
    @Expose(serialize = false)
    private boolean selected;
    private RealmList<ExerciseInstanceSet> exerciseInstanceSets;



    public ExerciseInstance() {

    }

    /**
     * Constructor
     * @param exercise - exercise to have an instance created out of
     */
    public ExerciseInstance(Exercise exercise) {
        this.exercise = exercise;
        exerciseId = exercise.getId();
        exerciseName = exercise.getName();
        exerciseInstanceSets = new RealmList<>();
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 1, REPS_DEFAULT, EFFORT_DEFAULT, REST_TIME_DEFAULT));
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 2, REPS_DEFAULT, EFFORT_DEFAULT, REST_TIME_DEFAULT));
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 3, REPS_DEFAULT, EFFORT_DEFAULT, REST_TIME_DEFAULT));
        exerciseInstanceSets.add(new ExerciseInstanceSet(this, 4, REPS_DEFAULT, EFFORT_DEFAULT, REST_TIME_DEFAULT));
    }

    public void setEffortUnit(Unit effortUnit) {
        this.effortUnit = effortUnit;
        effortUnitId = effortUnit.getId();
        effortUnitName = effortUnit.getName();
    }

    public void setRepUnit(Unit repUnit) {
        this.repUnit = repUnit;
        repUnitId = repUnit.getId();
        repUnitName = repUnit.getName();
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

    @ParcelPropertyConverter(ExerciseInstanceSetParcelConverter.class)
    public void setExerciseInstanceSets(RealmList<ExerciseInstanceSet> exerciseInstanceSets) {
        this.exerciseInstanceSets = exerciseInstanceSets;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public String getNotes() {
        return notes;
    }

    public Object clone() {
        ExerciseInstance cloned = null;
        try {
            cloned = (ExerciseInstance) super.clone();
            RealmList<ExerciseInstanceSet> setsClones = new RealmList<>();
            for (ExerciseInstanceSet set : exerciseInstanceSets) {
                ExerciseInstanceSet clone = (ExerciseInstanceSet) set.clone();
                setsClones.add(clone);
            }
            cloned.setExerciseInstanceSets(setsClones);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return cloned;
    }

    public Long getId() {
        return id;
    }
}
