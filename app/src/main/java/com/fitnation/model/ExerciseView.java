package com.fitnation.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ryan on 4/15/2017.
 */

public interface ExerciseView extends Comparable{
    List<ExerciseSetView> getExerciseSetView();
    void setExerciseSetViews(List<ExerciseSetView> sets);
    void addExerciseSetView(ExerciseView exercise, int orderNumber);
    boolean isSelected();
    boolean hasExerciseParent();
    boolean isSelectable();
    String getName();
    String getSkillLevelLevel();
    String getNotes();
    void setSelected(boolean checked);
    Long getId();
    Long getParentExerciseId();
    Object clone();
}
