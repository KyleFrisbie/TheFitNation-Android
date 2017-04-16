package com.fitnation.workout.common;

import android.app.Activity;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.workout.edit.EditWorkoutFragment;
import com.fitnation.workout.edit.EditWorkoutPresenter;
import com.fitnation.workout.exercise.ViewExerciseFragment;
import com.fitnation.workout.exercise.ViewExercisePresenter;

/**
 * Created by Ryan on 4/16/2017.
 */

public class Navigator {
    private Navigator(){}

    public static void navigateToEditWorkout(BaseActivity activity, UserWorkoutInstance userWorkoutInstance, int containterId) {
        EditWorkoutFragment editWorkoutFragment = EditWorkoutFragment.newInstance(userWorkoutInstance);
        editWorkoutFragment.setPresenter(new EditWorkoutPresenter(editWorkoutFragment));
        activity.getSupportFragmentManager().beginTransaction().replace(containterId, editWorkoutFragment ).commit();
    }

    public static void navigateToEditExercise(BaseActivity activity, ExerciseView exerciseView, OnExerciseUpdatedCallback callback, int containerId) {
//        ViewExerciseFragment viewExerciseFragment = ViewExerciseFragment.newInstance(exerciseView);
//
//        viewExerciseFragment.setPresenter(new ViewExercisePresenter(exerciseView, viewExerciseFragment, callback));
//        activity.getSupportFragmentManager().beginTransaction().add(containerId, viewExerciseFragment).addToBackStack(null);
    }
}
