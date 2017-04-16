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
    boolean isSelected();
    boolean isSelectable();
    String getName();
    String getSkillLevelLevel();
    String getNotes();
    void setSelected(boolean checked);
    Long getId();
    Object clone();
}
