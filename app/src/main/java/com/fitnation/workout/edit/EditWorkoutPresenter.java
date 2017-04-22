package com.fitnation.workout.edit;

import android.support.annotation.Nullable;

import com.fitnation.R;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserExerciseInstance;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.workout.callbacks.ExerciseSelectedCallback;
import com.fitnation.workout.callbacks.OnEditExercisePressedCallback;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.navigation.Navigator;
import com.fitnation.workout.exercise.ExerciseType;
import com.fitnation.workout.exerciseList.ExercisesListFragment;

import java.util.Collections;

import io.realm.RealmList;

/**
 * Created by Ryan on 4/16/2017.
 */

public class EditWorkoutPresenter implements EditWorkoutContract.Presenter, OnExerciseUpdatedCallback {
    private UserWorkoutInstance mUserWorkoutInstance;
    private UserWorkoutTemplate mUserWorkoutTemplate;
    private EditWorkoutContract.View mView;
    private ExercisesListFragment mListFragment;
    private UserExerciseInstance mUserExerciseBeingEdited;

    public EditWorkoutPresenter (EditWorkoutContract.View view) {
        mView = view;
    }


    @Override
    public void onViewReady() {
        mListFragment = ExercisesListFragment.newInstance(mUserWorkoutInstance.getExerciseViews(), false, null,
                new OnEditExercisePressedCallback() {
            @Override
            public void onEditPressed(ExerciseView exercise) {
                mUserExerciseBeingEdited = (UserExerciseInstance) exercise;
                Navigator.navigateToEditExercise(mView.getBaseActivity(), exercise, ExerciseType.USER, EditWorkoutPresenter.this, R.id.content_main_container);
            }
        });
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction().add(R.id.edit_workout_container, mListFragment).commit();
    }

    @Override
    public void setUserWorkout(UserWorkoutInstance userWorkoutInstance, UserWorkoutTemplate userWorkoutTemplate) {
        mUserWorkoutInstance = userWorkoutInstance;
        mUserWorkoutTemplate = userWorkoutTemplate;
    }


    @Override
    public void onSavePressed() {
        Navigator.navigateToSaveUserWorkoutInstance(mView.getBaseActivity(), mUserWorkoutInstance, mUserWorkoutTemplate, R.id.content_main_container);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    //-----------------------OnExerciseUpdatedCallback---------------------------------//

    @Override
    public void exerciseUpdated(@Nullable ExerciseView updatedExerciseView) {
        UserExerciseInstance updatedExerciseInstance = (UserExerciseInstance) updatedExerciseView;
        RealmList<UserExerciseInstance> userExerciseInstances = mUserWorkoutInstance.getUserExerciseInstances();
        userExerciseInstances.remove(mUserExerciseBeingEdited);
        userExerciseInstances.add(updatedExerciseInstance);
        Collections.sort(userExerciseInstances);

        mUserWorkoutInstance.setExerciseInstanceSets(userExerciseInstances);

        mListFragment.displayExercises(mUserWorkoutInstance.getExerciseViews());
    }
}
