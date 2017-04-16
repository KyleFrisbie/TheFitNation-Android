package com.fitnation.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ryan on 4/15/2017.
 */

public interface ExerciseView {
    List<ExerciseSetView> getExerciseSetView();
    boolean isSelected();
    boolean isSelectable();
    String getName();
    void setSelected(boolean checked);
    Long getId();
}
