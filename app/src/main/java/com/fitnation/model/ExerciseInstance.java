package com.fitnation.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

import org.parceler.Parcel;
import org.parceler.ParcelPropertyConverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import io.realm.ExerciseInstanceRealmProxy;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Ryan on 3/21/2017.
 */
@Parcel(implementations = { ExerciseInstanceRealmProxy.class }, value = Parcel.Serialization.BEAN, analyze = { ExerciseInstance.class })
public class ExerciseInstance extends RealmObject implements Cloneable, Comparable, ExerciseView {
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
        notes = exercise.getNotes();
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

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public void setParentExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    @Override
    public List<ExerciseSetView> getExerciseSetView() {
        List<ExerciseSetView> exerciseSetView = new ArrayList<>();
        for (ExerciseInstanceSet set : exerciseInstanceSets) {
            exerciseSetView.add(set);
        }

        return exerciseSetView;
    }

    @Override
    public void setExerciseSetViews(List<ExerciseSetView> sets) {
        exerciseInstanceSets = new RealmList();

        for (ExerciseSetView setView : sets) {
            exerciseInstanceSets.add((ExerciseInstanceSet) setView);
        }
    }

    @Override
    public void addExerciseSetView(ExerciseView exercise, int orderNumber) {
        exerciseInstanceSets.add(new ExerciseInstanceSet((ExerciseInstance)exercise, orderNumber));
    }

    public static List<ExerciseView> convertExercisesToExerciseViews(List<ExerciseInstance> exerciseInstances) {
        List<ExerciseView> exerciseViews = null;

        if(exerciseInstances != null) {
            exerciseViews = new ArrayList<>();
            for (ExerciseInstance exerciseInstance :
                    exerciseInstances) {
                exerciseViews.add(exerciseInstance);
            }
        }

        return exerciseViews;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public boolean hasExerciseParent() {
        return exercise != null;
    }

    @Override
    public boolean isSelectable() {
        return true;
    }

    @Override
    public String getName() {
        return exerciseName;
    }

    @Override
    public String getSkillLevelLevel() {
        if(exercise != null) {
            return exercise.getSkillLevelLevel();
        }
        else {
            return null;
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExerciseInstance that = (ExerciseInstance) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (androidId != null ? !androidId.equals(that.androidId) : that.androidId != null)
            return false;
        if (exerciseId != null ? !exerciseId.equals(that.exerciseId) : that.exerciseId != null)
            return false;
        if (exerciseName != null ? !exerciseName.equals(that.exerciseName) : that.exerciseName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (androidId != null ? androidId.hashCode() : 0);
        result = 31 * result + (exerciseId != null ? exerciseId.hashCode() : 0);
        result = 31 * result + (exerciseName != null ? exerciseName.hashCode() : 0);
        return result;
    }

    public Long getId() {
        return id;
    }

    @Override
    public Long getParentExerciseId() {
        return exerciseId;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        ExerciseInstance exerciseInstance = (ExerciseInstance) o;
        String nameThis = this.exerciseName;
        String nameOther = exerciseInstance.getName();

        return nameThis.compareTo(nameOther);
    }
}
