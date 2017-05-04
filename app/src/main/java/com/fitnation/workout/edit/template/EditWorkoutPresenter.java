package com.fitnation.workout.edit.template;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fitnation.R;
import com.fitnation.workout.services.WorkoutDataManager;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.navigation.Navigator;
import com.fitnation.workout.callbacks.OnEditExercisePressedCallback;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;
import com.fitnation.workout.common.WorkoutAlertDialogFactory;
import com.fitnation.workout.create.list.ExercisesListFragment;
import com.fitnation.workout.services.WorkoutTemplateManager;

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
        mView.showProgress();
        mWorkoutDataManager.saveWorkout(mWorkoutTemplate, mWorkoutInstance, new SaveWorkoutCallback() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "Workout updated successfully!");
                mView.hideProgress();
                mView.showSuccess(WorkoutAlertDialogFactory.getBuildWorkoutSuccess(mView.getBaseActivity(),
                        mView.getBaseActivity().getString(R.string.updated), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Navigator.navigateToWorkouts(mView.getBaseActivity(), R.id.content_main_container);
                    }
                }));
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, "Workout update failed");
                mView.hideProgress();
                mView.showFailure(WorkoutAlertDialogFactory.getBuildWorkoutError(error, mView.getBaseActivity()));
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
