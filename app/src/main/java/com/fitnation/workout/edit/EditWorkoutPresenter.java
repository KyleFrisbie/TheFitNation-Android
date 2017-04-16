package com.fitnation.workout.edit;

import com.fitnation.R;
import com.fitnation.base.BasePresenter;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.workout.callbacks.ExerciseSelectedCallback;
import com.fitnation.workout.callbacks.OnEditExercisePressedCallback;
import com.fitnation.workout.exerciseList.ExercisesListFragment;

import java.util.List;

/**
 * Created by Ryan on 4/16/2017.
 */

public class EditWorkoutPresenter implements EditWorkoutContract.Presenter {
    private UserWorkoutInstance mUserWorkoutInstance;
    private EditWorkoutContract.View mView;

    public EditWorkoutPresenter (EditWorkoutContract.View view) {
        mView = view;
    }


    @Override
    public void onViewReady() {
        ExercisesListFragment exercisesListFragment = ExercisesListFragment.newInstance(mUserWorkoutInstance.getExerciseViews(), false, new ExerciseSelectedCallback() {
            @Override
            public void onExerciseSelected(ExerciseView exerciseInstance, boolean isSelected) {

            }
        }, new OnEditExercisePressedCallback() {
            @Override
            public void onEditPressed(ExerciseView exercise) {

            }
        });
        mView.getBaseActivity().getSupportFragmentManager().beginTransaction().add(R.id.edit_workout_container, exercisesListFragment).commit();
    }

    @Override
    public void setUserWorkoutInstance(UserWorkoutInstance userWorkoutInstance) {
        mUserWorkoutInstance = userWorkoutInstance;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
