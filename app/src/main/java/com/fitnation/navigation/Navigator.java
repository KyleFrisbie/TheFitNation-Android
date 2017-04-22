package com.fitnation.navigation;

import android.app.Activity;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.model.Exercise;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.workout.edit.EditWorkoutFragment;
import com.fitnation.workout.edit.EditWorkoutPresenter;
import com.fitnation.workout.edit.SaveUserWorkoutInstanceFragment;
import com.fitnation.workout.edit.SaveWorkoutPresenter;
import com.fitnation.workout.exercise.ExerciseType;
import com.fitnation.workout.exercise.ViewExerciseFragment;
import com.fitnation.workout.exercise.ViewExercisePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 4/16/2017.
 */

public class Navigator {
    private Navigator(){}

    private static List<NavigationState> mNavigationStateList = new ArrayList<>();

    public static void addNavigationState(NavigationState navigationState) {
        mNavigationStateList.add(navigationState);
    }

    public static NavigationState popNavigationState() {
        int size = mNavigationStateList.size();
        if(size >= 2) {
            mNavigationStateList.remove(size-1);
            return mNavigationStateList.get(size-2);
        } else if(size >= 1) {
            mNavigationStateList.get(0);
        } {
            return null;
        }
    }

    public static void navigateToEditWorkout(BaseActivity activity, UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate, int containterId) {
        EditWorkoutFragment editWorkoutFragment = EditWorkoutFragment.newInstance(userWorkoutInstance, userWorkoutTemplate);

        editWorkoutFragment.setPresenter(new EditWorkoutPresenter(editWorkoutFragment));
        activity.getSupportFragmentManager().beginTransaction().replace(containterId, editWorkoutFragment ).commit();
    }

    public static void navigateToEditExercise(BaseActivity activity, ExerciseView exerciseView, ExerciseType exerciseType, OnExerciseUpdatedCallback callback, int containerId) {
        ViewExerciseFragment viewExerciseFragment = ViewExerciseFragment.newInstance(exerciseView);

        viewExerciseFragment.setPresenter(new ViewExercisePresenter(exerciseView, exerciseType, viewExerciseFragment, callback));
        activity.getSupportFragmentManager().beginTransaction().add(containerId, viewExerciseFragment).addToBackStack(null).commit();
    }

    public static void navigateToSaveUserWorkoutInstance(BaseActivity activity, UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate, int containerId) {
        SaveUserWorkoutInstanceFragment saveUserWorkoutInstanceFragment = SaveUserWorkoutInstanceFragment.newInstance(userWorkoutInstance, userWorkoutTemplate);

        saveUserWorkoutInstanceFragment.setPresenter(new SaveWorkoutPresenter(saveUserWorkoutInstanceFragment, activity.getBaseContext()));
        activity.getSupportFragmentManager().beginTransaction().replace(containerId, saveUserWorkoutInstanceFragment ).commit();
    }
}
