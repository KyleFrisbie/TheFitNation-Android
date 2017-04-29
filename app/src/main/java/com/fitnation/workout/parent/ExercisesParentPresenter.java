package com.fitnation.workout.parent;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fitnation.R;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutTemplate;
import com.fitnation.networking.tasks.callbacks.ExercisesRequestCallback;
import com.fitnation.workout.callbacks.OnExerciseUpdatedCallback;
import com.fitnation.workout.callbacks.SaveDialogCallback;
import com.fitnation.workout.callbacks.SaveWorkoutCallback;
import com.fitnation.workout.common.WorkoutAlertDialogFactory;
import com.fitnation.navigation.Navigator;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseAction;
import com.fitnation.workout.services.WorkoutDataManager;

import java.util.List;

/**
 * Presenter for the ExercisesParent Fragment
 */
public class ExercisesParentPresenter implements ExercisesParentContract.Presenter, ExercisesRequestCallback, SaveDialogCallback, OnExerciseUpdatedCallback {
    private static final String TAG = ExercisesParentPresenter.class.getSimpleName();
    private ExercisesManager mExerciseManager;
    private WorkoutDataManager mWorkoutDataManager;
    private ExercisesParentContract.View mView;
    private ExerciseInstance mExerciseInstanceBeingEdited;

    public ExercisesParentPresenter(Context context, ExercisesParentContract.View view) {
        mView = view;
        mExerciseManager = new ExercisesManager(context);
        mWorkoutDataManager = new WorkoutDataManager(context);
    }

    @Override
    public void onViewReady() {
        //get the exercises
        mView.showProgress();
        mExerciseManager.getExercises(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void onEditPressed(ExerciseInstance exercise) {
        mExerciseInstanceBeingEdited = exercise;
        Navigator.navigateToEditExercise(mView.getBaseActivity(), exercise, this, R.id.content_main_container);
    }

    //----------------------------------ExercisesRequestCallback----------------------------------//

    @Override
    public void onExercisesRetrieved(List<ExerciseInstance> exerciseList1, List<ExerciseInstance> exerciseList2, List<ExerciseInstance> exerciseList3) {
        mView.stopProgress();
        mView.displayExercises(exerciseList1, exerciseList2, exerciseList3);
    }

    @Override
    public void onError() {
        //TODO re-authenticate user if 401
        mView.stopProgress();
    }

    @Override
    public void onActionButtonClicked(ExerciseAction action) {
        Log.i(TAG, "ExerciseActionButtonClicked: " + action);
        if(action == ExerciseAction.SAVE) {
            mView.promptUserToSave(SaveWorkoutDialogFragment.newInstance(this));

        } else if(action == ExerciseAction.LAUNCH) {
            //TODO Launch a new instance of this workout
        }
    }

    @Override
    public void onExerciseSelected(ExerciseInstance exerciseInstance, boolean isSelected) {
        mExerciseManager.exerciseInstanceSelected(exerciseInstance, isSelected);
        if(mExerciseManager.atLeastOneExerciseSelected()) {
            mView.setSaveButtonEnabled(true);
        } else {
            mView.setSaveButtonEnabled(false);
        }
    }

    //----------------------------------SaveDialogCallback----------------------------------//

    @Override
    public void onSaveRequested(String name) {
        Log.i(TAG, "User requested to save workout with name: " + name);
        List<ExerciseInstance> selectedExercises = mExerciseManager.getSelectedExercises();
        final WorkoutTemplate workoutTemplate = WorkoutTemplateManager.getSingletonWorkoutTemplate();
        final WorkoutInstance workoutInstance = new WorkoutInstance(selectedExercises, name);

        if(name != null && !name.isEmpty()) {
            mView.showProgress();
            mWorkoutDataManager.saveWorkout(workoutTemplate, workoutInstance, new SaveWorkoutCallback() {
                @Override
                public void onSuccess() {
                    mView.stopProgress();
                    mView.showSuccess(WorkoutAlertDialogFactory.getBuildWorkoutSuccess(mView.getBaseActivity(),
                            mView.getBaseActivity().getString(R.string.created), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            //TODO move to LAUNCH button for UserWorkoutInstance list view
                            UserWorkoutInstance userWorkoutInstance = new UserWorkoutInstance(mWorkoutDataManager.getWorkoutInstance(), UserWorkoutInstance.getNextAndroidKey());
                            UserWorkoutTemplate userWorkoutTemplate = WorkoutTemplateManager.getSingletonUserWorkoutTemplate(WorkoutTemplateManager.getSingletonWorkoutTemplate());
                            Navigator.navigateToEditUserWorkout(mView.getBaseActivity(), userWorkoutInstance, userWorkoutTemplate, R.id.content_main_container);

                            //TODO move to LAUNCH button for WorkoutInstance list view
//                            Navigator.navigateToEditWorkout(mView.getBaseActivity(), mWorkoutDataManager.getWorkoutInstance(), R.id.content_main_container);

                        }
                    }));
                }

                @Override
                public void onFailure(String error) {
                    mView.stopProgress();
                    mView.showFailure(WorkoutAlertDialogFactory.getBuildWorkoutError(error, mView.getBaseActivity()));
                }
            });
        } else {
            mView.showFailure(WorkoutAlertDialogFactory.getBuildWorkoutError("A Workout name must be provided", mView.getBaseActivity()));
        }
    }

    //----------------------------------OnExerciseUpdatedCallback----------------------------------//

    @Override
    public void exerciseUpdated(@Nullable ExerciseView updatedExerciseView) {
        ExerciseInstance updatedExerciseInstance = (ExerciseInstance) updatedExerciseView;

        if(updatedExerciseInstance != null) {
            mExerciseInstanceBeingEdited = (ExerciseInstance) updatedExerciseInstance.clone();
            mExerciseManager.updateExerciseList(mExerciseInstanceBeingEdited, updatedExerciseInstance, mView.getSelectedTab());
            mView.displayUpdatedExercises(mExerciseManager.getExercisesTab1(), mExerciseManager.getExercisesTab2(), mExerciseManager.getExercisesTab3());
        }
    }
}
