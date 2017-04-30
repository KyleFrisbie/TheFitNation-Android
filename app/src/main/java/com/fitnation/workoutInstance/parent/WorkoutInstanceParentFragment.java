package com.fitnation.workoutInstance.parent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.model.WorkoutInstance;
import com.fitnation.model.WorkoutView;
import com.fitnation.navigation.NavigationActivity;
import com.fitnation.workoutInstance.callbacks.OnWorkoutDeletePressedCallback;
import com.fitnation.workoutInstance.callbacks.OnWorkoutDetailsPressedCallback;
import com.fitnation.workoutInstance.callbacks.OnWorkoutLaunchPressedCallback;
import com.fitnation.workoutInstance.instanceList.WorkoutInstanceListFragment;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Erik on 4/24/2017.
 */

public class WorkoutInstanceParentFragment extends BaseFragment implements WorkoutInstanceParentContract.View, OnWorkoutDeletePressedCallback, OnWorkoutLaunchPressedCallback, OnWorkoutDetailsPressedCallback {
    private static final String TAG = WorkoutInstanceParentFragment.class.getSimpleName();
    private WorkoutInstanceParentContract.Presenter mPresenter;
    private WorkoutInstanceListFragment mWorkoutInstanceListFragment;

    public WorkoutInstanceParentFragment() {
        //required empty constructor
    }

    public static WorkoutInstanceParentFragment newInstance(Context context){
        WorkoutInstanceParentFragment fragment = new WorkoutInstanceParentFragment();
        fragment.setPresenter(new WorkoutInstanceParentPresenter(context, fragment));

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.workout_instance_parent_fragment, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated()");
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
        mPresenter.onViewReady();
        ((NavigationActivity) getActivity()).updateToolbar(false, "My Workouts");
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume()");
        super.onResume();
    }



    //------------------------------WorkoutInstanceParentContract.View----------------------------//

    @Override
    public void showProgress() {
        BaseActivity baseActivity = getBaseActivity();
        if(baseActivity != null) {
            baseActivity.showProgress(null);
        }
    }

    @Override
    public void stopProgress() {
        final BaseActivity baseActivity = getBaseActivity();
        if(baseActivity != null) {
            baseActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    baseActivity.stopProgress();
                }
            });
        }
    }

    @Override
    public void displayWorkouts(List<WorkoutInstance> workouts) {
        mWorkoutInstanceListFragment = WorkoutInstanceListFragment.newInstance(WorkoutInstance.convertWorkoutsToWorkoutViews(workouts), this, this, this);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.workout_instance_parent_layout, mWorkoutInstanceListFragment).commit();
        mWorkoutInstanceListFragment.displayWorkouts(WorkoutInstance.convertWorkoutsToWorkoutViews(workouts));
        Log.i(TAG, "displayWorkouts()");
    }

    @Override
    public void displayUserWorkouts(List<UserWorkoutInstance> userWorkouts) {
        mWorkoutInstanceListFragment = WorkoutInstanceListFragment.newInstance(UserWorkoutInstance.convertWorkoutsToWorkoutViews(userWorkouts), this, this, this);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.workout_instance_parent_layout, mWorkoutInstanceListFragment).commit();
        mWorkoutInstanceListFragment.displayWorkouts(UserWorkoutInstance.convertWorkoutsToWorkoutViews(userWorkouts));
        Log.i(TAG, "displayWorkouts()");
    }

    @Override
    public void displayUpdatedWorkouts(List<WorkoutInstance> workoutList) {
        mWorkoutInstanceListFragment = WorkoutInstanceListFragment.newInstance(WorkoutInstance.convertWorkoutsToWorkoutViews(workoutList), this, this, this);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.workout_instance_parent_layout, mWorkoutInstanceListFragment).commit();
        mWorkoutInstanceListFragment.displayWorkouts(WorkoutInstance.convertWorkoutsToWorkoutViews(workoutList));
    }

    @Override
    public void showSuccess(final AlertDialog alertDialog) {
        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
    }

    @Override
    public void showFailure(final AlertDialog alertDialog) {
        getBaseActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialog.show();
            }
        });
    }

    //----------------------------------OnButtonsPressedCallbacks---------------------------------//

    @Override
    public void onDeletePressed(WorkoutView workout) {
        mPresenter.onDeletePressed((WorkoutInstance) workout);
    }

    @Override
    public void onLaunchPressed(WorkoutView workout) {
        mPresenter.onLaunchPressed((WorkoutInstance) workout);
    }

    @Override
    public void onDetailsPressed(WorkoutView workout) {
        mPresenter.onDetailsPressed((WorkoutInstance) workout);
    }


    //-----------------------------------------BaseActivity---------------------------------------//

    @Override
    public void setPresenter(WorkoutInstanceParentContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}
