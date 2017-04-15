package com.fitnation.workout.callbacks;

import com.fitnation.model.enums.SkillLevel;

import java.util.List;

/**
 * Created by Ryan Newsom on 4/9/17. *
 */

public interface GetSkillLevelsCallback {
    void onSuccess(List<SkillLevel> skillLevels);
    void onFailure(String error);
}