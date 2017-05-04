package com.fitnation.history.parent;

import android.content.Context;
import android.util.Log;

import com.android.volley.toolbox.Volley;
import com.fitnation.R;
import com.fitnation.model.UserExerciseInstance;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.UserWorkoutTemplate;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutView;
import com.fitnation.navigation.Navigator;
import com.fitnation.networking.AuthToken;
import com.fitnation.networking.tasks.ExerciseInstanceTask;
import com.fitnation.networking.tasks.callbacks.GetExerciseInstancesForListCallback;
import com.fitnation.workout.services.WorkoutTemplateManager;
import com.fitnation.history.callbacks.WorkoutManagerWorkoutsCallback;

import java.util.List;
import java.util.Objects;

/**
 * Calls classes, preforms business logic and returns data to view for display.
 */

public class WorkoutInstanceParentPresenter implements WorkoutInstanceParentContract.Presenter, WorkoutManagerWorkoutsCallback.instance, WorkoutManagerWorkoutsCallback.userInstance {
    private static final String TAG = WorkoutInstanceParentPresenter.class.getSimpleName();
    private WorkoutManager mWorkoutManager;
    private WorkoutInstanceParentContract.View mView;
    private String mWorkoutTypeToReturn;

    public WorkoutInstanceParentPresenter(Context mContext, WorkoutInstanceParentContract.View mView, String workoutType) {
        mWorkoutManager = new WorkoutManager(mContext);
        this.mView = mView;
        mWorkoutTypeToReturn = workoutType;
    }

    @Override
    public void onViewReady() {
        //get the workouts
        mView.showProgress();
        if (Objects.equals(mWorkoutTypeToReturn, "WORKOUT_INSTANCE")) {
            Log.i(TAG, "getting template");
            mWorkoutManager.getWorkoutTemplate();
            mWorkoutManager.getAllWorkoutInstances(new WorkoutManagerWorkoutsCallback.instance() {
                @Override
                public void onWorkoutInstancesRetrieved(List<WorkoutInstance> workoutList) {
                    mView.stopProgress();
                    mView.displayUpdatedWorkouts(workoutList);
                }

                @Override
                public void onError() {
                    Log.i(TAG, "Failed to update WorkoutInstances");
                }
            });
        } else if (Objects.equals(mWorkoutTypeToReturn, "USER_WORKOUT_INSTANCE")) {
            Log.i(TAG, "getting template");
            mWorkoutManager.getUserWorkoutTemplate();
            mWorkoutManager.getAllUserWorkoutInstances(new WorkoutManagerWorkoutsCallback.userInstance() {
                @Override
                public void onUserWorkoutInstancesRetrieved(List<UserWorkoutInstance> userWorkoutList) {
                    mView.stopProgress();
                    mView.displayUpdatedUserWorkouts(userWorkoutList);
                }

                @Override
                public void onUserError() {
                    Log.i(TAG, "Failed to update UserWorkoutInstances");
                }
            });
        } else {
            Log.i(TAG, "no workout type set error.");
        }
    }

    @Override
    public void start() {
        Log.i(TAG, "onStart()");
    }

    @Override
    public void stop() {
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onDeletePressed(WorkoutView workoutInstance) {
        // TODO: remove the workout instance
        Log.i(TAG, "onDeletePressed()");

        if (Objects.equals(mWorkoutTypeToReturn, "WORKOUT_INSTANCE")) {
            mWorkoutManager.deleteWorkoutInstance((WorkoutInstance) workoutInstance, new DeleteWorkoutCallback() {
                @Override
                public void onSuccess() {
                    mView.getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main_container, WorkoutInstanceParentFragment.newInstance(mView.getBaseActivity(), "WORKOUT_INSTANCE")).commit();
                }

                @Override
                public void onFailure() {
                    Log.e(TAG, "Failed to delete instance");
                }
            });
            mWorkoutManager.getAllWorkoutInstances(this);
        } else if (Objects.equals(mWorkoutTypeToReturn, "USER_WORKOUT_INSTANCE")) {
            mWorkoutManager.deleteUserWorkoutInstance((UserWorkoutInstance) workoutInstance, new DeleteWorkoutCallback() {
                @Override
                public void onSuccess() {
                    mView.getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main_container, WorkoutInstanceParentFragment.newInstance(mView.getBaseActivity(), "USER_WORKOUT_INSTANCE")).commit();
                }

                @Override
                public void onFailure() {
                    Log.e(TAG, "Failed to delete instance");
                }
            });
            mWorkoutManager.getAllUserWorkoutInstances(this);
        } else {
            Log.i(TAG, "no Workout type set error");
        }
    }

    @Override
    public void onLaunchPressed(WorkoutInstance workoutInstance) {
        UserWorkoutInstance userWorkoutInstance = new UserWorkoutInstance(workoutInstance, UserWorkoutInstance.getNextAndroidKey());
        UserWorkoutTemplate userWorkoutTemplate = WorkoutTemplateManager.getSingletonUserWorkoutTemplate(WorkoutTemplateManager.getSingletonWorkoutTemplate());
        Navigator.navigateToEditUserWorkout(mView.getBaseActivity(), userWorkoutInstance, userWorkoutTemplate, R.id.content_main_container);
    }

    @Override
    public void onDetailsPressed(WorkoutView workoutInstance) {
        Log.i(TAG, "onDetailsPressed()");
        if(Objects.equals(mWorkoutTypeToReturn, "WORKOUT_INSTANCE")) {
            Navigator.navigateToEditWorkout(mView.getBaseActivity(), (WorkoutInstance) workoutInstance, R.id.content_main_container);
        }else {
            ExerciseInstanceTask exerciseInstanceTask = new ExerciseInstanceTask(AuthToken.getInstance().getAccessToken(),
                    Volley.newRequestQueue(mView.getBaseActivity()));
            final UserWorkoutInstance userWorkoutInstance = (UserWorkoutInstance) workoutInstance;
            exerciseInstanceTask.getExerciseInstancesForList(userWorkoutInstance.getUserExerciseInstances(), new GetExerciseInstancesForListCallback() {
                @Override
                public void onSuccess(List<UserExerciseInstance> userExerciseInstancesUpdated) {
                    Navigator.navigateToEditUserWorkout(mView.getBaseActivity(), userWorkoutInstance, WorkoutTemplateManager.getSingletonUserWorkoutTemplate(WorkoutTemplateManager.getSingletonWorkoutTemplate()), R.id.content_main_container);
                }

                @Override
                public void onFailure(String error) {
                    Log.e(TAG, "Unable to populate exercise instance for user exercise instances:" + error);
                }
            });

        }
    }

    @Override
    public void onBuildWorkoutPressed() {
        Navigator.navigateToBuildWorkout(mView.getBaseActivity(), R.id.content_main_container);
    }

    //---------------------------WorkoutManagerWorkoutsCallback.instance--------------------------//

    @Override
    public void onWorkoutInstancesRetrieved(List<WorkoutInstance> workoutList) {
        mView.stopProgress();
        mView.displayWorkouts(workoutList);
    }

    @Override
    public void onError() {
        Log.e(TAG, "Error from workout instances");
    }

    //-------------------------WorkoutManagerRequestCallback.userInstance-------------------------//

    @Override
    public void onUserWorkoutInstancesRetrieved(List<UserWorkoutInstance> userWorkoutList) {
        mView.stopProgress();
        mView.displayUserWorkouts(userWorkoutList);
    }

    @Override
    public void onUserError() {
        Log.e(TAG, "Error from user workout instances");
    }
}
