package com.fitnation.history.parent;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.fitnation.history.callbacks.OnWorkoutDeletePressedCallback;
import com.fitnation.history.callbacks.OnWorkoutDetailsPressedCallback;
import com.fitnation.history.callbacks.OnWorkoutLaunchPressedCallback;
import com.fitnation.history.list.WorkoutInstanceListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Erik on 4/24/2017.
 */

public class WorkoutInstanceParentFragment extends BaseFragment implements WorkoutInstanceParentContract.View, OnWorkoutDeletePressedCallback, OnWorkoutLaunchPressedCallback, OnWorkoutDetailsPressedCallback {
    public static final String WORKOUT_FRAGMENT_TYPE = "WorkoutType";
    public static final String WORKOUT_INSTANCE_FRAGMENT = "WORKOUT_INSTANCE";
    public static final String USER_WORKOUT_INSTANCE_FRAGMENT = "USER_WORKOUT_INSTANCE";
    private static final String TAG = WorkoutInstanceParentFragment.class.getSimpleName();
    private WorkoutInstanceParentContract.Presenter mPresenter;
    private WorkoutInstanceListFragment mWorkoutInstanceListFragment;
    private String mFragmentType;
    @BindView(R.id.build_workout_button)
    public FloatingActionButton mBuildWorkoutButton;

    public WorkoutInstanceParentFragment() {
        //required empty constructor
    }

    public static WorkoutInstanceParentFragment newInstance(Context context, String workoutInstanceType) {
        WorkoutInstanceParentFragment fragment = new WorkoutInstanceParentFragment();
        Bundle args = new Bundle();
        args.putString(WORKOUT_FRAGMENT_TYPE, workoutInstanceType);
        fragment.setArguments(args);
        fragment.setPresenter(new WorkoutInstanceParentPresenter(context, fragment, workoutInstanceType));

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mFragmentType = args.getString(WORKOUT_FRAGMENT_TYPE, null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.workout_instance_parent_fragment, container, false);
        ButterKnife.bind(this, v);

        if(mFragmentType.equals(USER_WORKOUT_INSTANCE_FRAGMENT)) {
            mBuildWorkoutButton.setVisibility(View.INVISIBLE);
        }

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
        if(mFragmentType.equals(WORKOUT_INSTANCE_FRAGMENT)) {
            ((NavigationActivity) getActivity()).updateToolbar(false, getString(R.string.workout_regimens));
            ((NavigationActivity) getActivity()).updateMenuItemSelected(R.id.nav_workout_regimens);
        } else if(mFragmentType.equals(USER_WORKOUT_INSTANCE_FRAGMENT)) {
            ((NavigationActivity) getActivity()).updateToolbar(false, getString(R.string.workout_history));
            ((NavigationActivity) getActivity()).updateMenuItemSelected(R.id.nav_my_workouts);
        }
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
        mWorkoutInstanceListFragment = WorkoutInstanceListFragment.newInstance(WORKOUT_INSTANCE_FRAGMENT, WorkoutInstance.convertWorkoutsToWorkoutViews(workouts), this, this, this);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.workout_instance_parent_container, mWorkoutInstanceListFragment).commit();
        mWorkoutInstanceListFragment.displayWorkouts(WORKOUT_INSTANCE_FRAGMENT, WorkoutInstance.convertWorkoutsToWorkoutViews(workouts));
        Log.i(TAG, "displayWorkouts()");
    }

    @Override
    public void displayUserWorkouts(List<UserWorkoutInstance> userWorkouts) {
        mWorkoutInstanceListFragment = WorkoutInstanceListFragment.newInstance(USER_WORKOUT_INSTANCE_FRAGMENT, UserWorkoutInstance.convertWorkoutsToWorkoutViews(userWorkouts), this, this, this);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.workout_instance_parent_container, mWorkoutInstanceListFragment).commit();
        mWorkoutInstanceListFragment.displayWorkouts(USER_WORKOUT_INSTANCE_FRAGMENT, UserWorkoutInstance.convertWorkoutsToWorkoutViews(userWorkouts));
        Log.i(TAG, "displayWorkouts()");
    }

    @Override
    public void displayUpdatedWorkouts(List<WorkoutInstance> workoutList) {
        getBaseActivity().getSupportFragmentManager().beginTransaction().remove(mWorkoutInstanceListFragment);
        mWorkoutInstanceListFragment = WorkoutInstanceListFragment.newInstance(WORKOUT_INSTANCE_FRAGMENT, WorkoutInstance.convertWorkoutsToWorkoutViews(workoutList), this, this, this);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.workout_instance_parent_container, mWorkoutInstanceListFragment).commit();
        mWorkoutInstanceListFragment.displayWorkouts(WORKOUT_INSTANCE_FRAGMENT, WorkoutInstance.convertWorkoutsToWorkoutViews(workoutList));
        Log.i(TAG, "displayUpdatedWorkouts()");
    }

    @Override
    public void displayUpdatedUserWorkouts(List<UserWorkoutInstance> userWorkoutList) {
        getBaseActivity().getSupportFragmentManager().beginTransaction().remove(mWorkoutInstanceListFragment);
        mWorkoutInstanceListFragment = WorkoutInstanceListFragment.newInstance(USER_WORKOUT_INSTANCE_FRAGMENT, UserWorkoutInstance.convertWorkoutsToWorkoutViews(userWorkoutList), this, this, this);
        getBaseActivity().getSupportFragmentManager().beginTransaction().replace(R.id.workout_instance_parent_container, mWorkoutInstanceListFragment).commit();
        mWorkoutInstanceListFragment.displayWorkouts(USER_WORKOUT_INSTANCE_FRAGMENT, UserWorkoutInstance.convertWorkoutsToWorkoutViews(userWorkoutList));
        Log.i(TAG, "displayWorkouts()");
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
        mPresenter.onDeletePressed(workout);
    }

    @Override
    public void onLaunchPressed(WorkoutView workout) {
        mPresenter.onLaunchPressed((WorkoutInstance) workout);
    }

    @Override
    public void onDetailsPressed(WorkoutView workout) {
        mPresenter.onDetailsPressed(workout);
    }

    @OnClick(R.id.build_workout_button)
    public void onBuildWorkoutPressed() {
        mPresenter.onBuildWorkoutPressed();
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
