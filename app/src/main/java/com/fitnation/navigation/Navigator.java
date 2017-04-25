package com.fitnation.navigation;

import com.fitnation.base.BaseActivity;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.workout.view.instance.EditUserWorkoutFragment;
import com.fitnation.workout.view.instance.EditUserWorkoutPresenter;
import com.fitnation.workout.view.instance.SaveUserWorkoutInstanceFragment;
import com.fitnation.workout.view.instance.SaveUserWorkoutPresenter;
import com.fitnation.workout.exercise.ExerciseType;
import com.fitnation.workout.exercise.ViewExerciseFragment;
import com.fitnation.workout.exercise.ViewExercisePresenter;
import com.fitnation.workout.view.template.EditWorkoutFragment;
import com.fitnation.workout.view.template.EditWorkoutPresenter;

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

    public static void navigateToEditWorkout(BaseActivity activity, WorkoutInstance workoutInstance, int containterId) {
        EditWorkoutFragment editWorkoutFragment = EditWorkoutFragment.newInstance(workoutInstance);

        editWorkoutFragment.setPresenter(new EditWorkoutPresenter(editWorkoutFragment, activity));
        activity.getSupportFragmentManager().beginTransaction().replace(containterId, editWorkoutFragment ).commit();
    }

    public static void navigateToEditUserWorkout(BaseActivity activity, UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate, int containterId) {
        EditUserWorkoutFragment editWorkoutFragment = EditUserWorkoutFragment.newInstance(userWorkoutInstance, userWorkoutTemplate);

        editWorkoutFragment.setPresenter(new EditUserWorkoutPresenter(editWorkoutFragment));
        activity.getSupportFragmentManager().beginTransaction().replace(containterId, editWorkoutFragment ).commit();
    }

    public static void navigateToEditExercise(BaseActivity activity, ExerciseView exerciseView, OnExerciseUpdatedCallback callback, int containerId) {
        ViewExerciseFragment viewExerciseFragment = ViewExerciseFragment.newInstance(exerciseView);

        viewExerciseFragment.setPresenter(new ViewExercisePresenter(exerciseView, viewExerciseFragment, callback));
        activity.getSupportFragmentManager().beginTransaction().add(containerId, viewExerciseFragment).addToBackStack(null).commit();
    }

    public static void navigateToSaveUserWorkoutInstance(BaseActivity activity, UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate, int containerId) {
        SaveUserWorkoutInstanceFragment saveUserWorkoutInstanceFragment = SaveUserWorkoutInstanceFragment.newInstance(userWorkoutInstance, userWorkoutTemplate);

        saveUserWorkoutInstanceFragment.setPresenter(new SaveUserWorkoutPresenter(saveUserWorkoutInstanceFragment, activity.getBaseContext()));
        activity.getSupportFragmentManager().beginTransaction().replace(containerId, saveUserWorkoutInstanceFragment ).commit();
    }
}
