package com.fitnation.model;

import java.util.List;

/**
 * Created by Ryan on 4/15/2017.
 */

public interface ExerciseView {
    List<ExerciseSetView> getExerciseSetView();
    boolean isSelected();
    String getName();
    void setSelected(boolean checked);
    Long getId();
}
