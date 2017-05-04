package com.fitnation.workout.edit.instance;

import android.support.annotation.Nullable;

import com.fitnation.R;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserExerciseInstance;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.workout.callbacks.OnEditExercisePressedCallback;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.navigation.Navigator;
import com.fitnation.workout.create.list.ExercisesListFragment;

import java.util.Collections;

import io.realm.RealmList;

/**
 * Created by Ryan on 4/16/2017.
 */

public class EditUserWorkoutPresenter implements EditUserWorkoutContract.Presenter, OnExerciseUpdatedCallback {
    private UserWorkoutInstance mUserWorkoutInstance;
    private UserWorkoutTemplate mUserWorkoutTemplate;
    private EditUserWorkoutContract.View mView;
    private ExercisesListFragment mListFragment;
    private UserExerciseInstance mUserExerciseBeingEdited;

    public EditUserWorkoutPresenter(EditUserWorkoutContract.View view) {
        mView = view;
    }


    @Override
    public void onViewReady() {
        mListFragment = ExercisesListFragment.newInstance(mUserWorkoutInstance.getExerciseViews(), false, null,
                new OnEditExercisePressedCallback() {
            @Override
            public void onEditPressed(ExerciseView exercise) {
                mUserExerciseBeingEdited = (UserExerciseInstance) exercise;
                Navigator.navigateToEditExercise(mView.getBaseActivity(), exercise, EditUserWorkoutPresenter.this, R.id.content_main_container);
            }
        });
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction().add(R.id.edit_user_workout_container, mListFragment).commit();
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
