package com.fitnation.workout.view.template;

import android.support.annotation.Nullable;

import com.fitnation.R;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserExerciseInstance;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.navigation.Navigator;
import com.fitnation.workout.callbacks.OnEditExercisePressedCallback;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.workout.exercise.ExerciseType;
import com.fitnation.workout.exerciseList.ExercisesListFragment;
import com.fitnation.workout.view.instance.EditUserWorkoutPresenter;

import java.util.Collections;

import io.realm.RealmList;

/**
 * Created by Ryan on 4/23/2017.
 */

public class EditWorkoutPresenter implements EditWorkoutContract.Presenter, OnExerciseUpdatedCallback {
    private ExercisesListFragment mListFragment;
    private EditWorkoutContract.View mView;
    private ExerciseInstance mExerciseBeingEdited;
    private WorkoutInstance mWorkoutInstance;

    public EditWorkoutPresenter(EditWorkoutContract.View view) {
        mView = view;
    }


    @Override
    public void onViewReady() {
        mListFragment = ExercisesListFragment.newInstance(mWorkoutInstance.getExerciseViews(), false, null,
                new OnEditExercisePressedCallback() {
                    @Override
                    public void onEditPressed(ExerciseView exercise) {
                        mExerciseBeingEdited = (ExerciseInstance) exercise;
                        Navigator.navigateToEditExercise(mView.getBaseActivity(), exercise, EditWorkoutPresenter.this, R.id.content_main_container);
                    }
                });
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction().add(R.id.edit_workout_instance_container, mListFragment).commit();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void setWorkout(WorkoutInstance workoutInstance) {
        mWorkoutInstance = workoutInstance;
    }

    @Override
    public void onSavePressed() {

    }

    @Override
    public void exerciseUpdated(@Nullable ExerciseView updatedExerciseView) {
        ExerciseInstance updatedExerciseInstance = (ExerciseInstance) updatedExerciseView;
        RealmList<ExerciseInstance> exerciseInstances = mWorkoutInstance.getExerciseInstances();
        exerciseInstances.remove(mExerciseBeingEdited);
        exerciseInstances.add(updatedExerciseInstance);
        Collections.sort(exerciseInstances);

        mWorkoutInstance.setExercises(exerciseInstances);

        mListFragment.displayExercises(mWorkoutInstance.getExerciseViews());
    }
}
