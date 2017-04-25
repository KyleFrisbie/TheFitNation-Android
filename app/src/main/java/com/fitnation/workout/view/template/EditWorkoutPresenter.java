package com.fitnation.workout.view.template;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fitnation.R;
import com.fitnation.datamanagers.WorkoutDataManager;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.navigation.Navigator;
import com.fitnation.workout.callbacks.OnEditExercisePressedCallback;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;
import com.fitnation.workout.exerciseList.ExercisesListFragment;
import com.fitnation.workout.parent.WorkoutTemplateManager;

import java.util.Collections;

import io.realm.RealmList;

/**
 * Created by Ryan on 4/23/2017.
 */

public class EditWorkoutPresenter implements EditWorkoutContract.Presenter, OnExerciseUpdatedCallback {
    private static final String TAG = EditWorkoutPresenter.class.getSimpleName();
    private ExercisesListFragment mListFragment;
    private EditWorkoutContract.View mView;
    private ExerciseInstance mExerciseBeingEdited;
    private WorkoutInstance mWorkoutInstance;
    private WorkoutTemplate mWorkoutTemplate;
    private WorkoutDataManager mWorkoutDataManager;

    public EditWorkoutPresenter(EditWorkoutContract.View view, Context context) {
        mView = view;
        mWorkoutDataManager = new WorkoutDataManager(context);
        mWorkoutTemplate = WorkoutTemplateManager.getSingletonWorkoutTemplate();
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
        //TODO save WorkoutInstance to DB
        //TODO save WorkoutInstance to WebServices
        mWorkoutDataManager.saveWorkout(mWorkoutTemplate, mWorkoutInstance, new SaveWorkoutCallback() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "Workout updated succesfully!");
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, "Workout update failed");
            }
        });

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
