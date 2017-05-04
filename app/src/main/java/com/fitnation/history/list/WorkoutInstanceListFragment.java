package com.fitnation.history.list;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnation.R;
import com.fitnation.base.BaseFragment;
import com.fitnation.model.WorkoutView;
import com.fitnation.history.callbacks.OnWorkoutDeletePressedCallback;
import com.fitnation.history.callbacks.OnWorkoutDetailsPressedCallback;
import com.fitnation.history.callbacks.OnWorkoutLaunchPressedCallback;
import com.fitnation.history.parent.WorkoutInstanceParentFragment;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Erik on 4/24/2017.
 */

public class WorkoutInstanceListFragment extends BaseFragment{
    private static final String TAG = WorkoutInstanceListFragment.class.getSimpleName();
    private static final String WORKOUT_LIST = "WORKOUT_LIST";
    @BindView(R.id.workout_recycler_view)
    public RecyclerView mRecyclerView;
    List<WorkoutView> mWorkouts;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnWorkoutDetailsPressedCallback mOnWorkoutDetailsPressedCallback;
    private OnWorkoutDeletePressedCallback mOnWorkoutDeletePressedCallback;
    private OnWorkoutLaunchPressedCallback mOnWorkoutLaunchPressedCallback;
    private boolean mHasUpdatedData;
    private String mWorkoutType;

    public WorkoutInstanceListFragment() {
        Log.i(TAG, "in constructor");
        //Required empty public constructor
    }

    public static WorkoutInstanceListFragment newInstance(String workoutType, List<WorkoutView> workoutInstances,
                                                          OnWorkoutDeletePressedCallback onWorkoutDeletePressedCallback,
                                                          OnWorkoutLaunchPressedCallback onWorkoutLaunchPressedCallback,
                                                          OnWorkoutDetailsPressedCallback onWorkoutDetailsPressedCallback){
        WorkoutInstanceListFragment workoutInstanceListFragment = new WorkoutInstanceListFragment();
            Bundle bundle = new Bundle();

//        if(workoutInstances != null && !workoutInstances.isEmpty()) {
//
//            bundle.putParcelable(WORKOUT_LIST, Parcels.wrap(workoutInstances));
//            workoutInstanceListFragment.setArguments(bundle);
//        }

        bundle.putString(WorkoutInstanceParentFragment.WORKOUT_FRAGMENT_TYPE, workoutType);


        workoutInstanceListFragment.setOnWorkoutDeletePressed(onWorkoutDeletePressedCallback);
        workoutInstanceListFragment.setOnWorkoutLaunchPressed(onWorkoutLaunchPressedCallback);
        workoutInstanceListFragment.setOnWorkoutDetailsPressed(onWorkoutDetailsPressedCallback);

        return workoutInstanceListFragment;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "onHiddenChanged()");
        if (mHasUpdatedData) {
            mHasUpdatedData = false;
            mAdapter = new WorkoutInstanceAdapter(mWorkoutType, mWorkouts, mOnWorkoutDeletePressedCallback, mOnWorkoutLaunchPressedCallback, mOnWorkoutDetailsPressedCallback);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public void setOnWorkoutDeletePressed(OnWorkoutDeletePressedCallback onWorkoutDeletePressed) {
        this.mOnWorkoutDeletePressedCallback = onWorkoutDeletePressed;
    }

    public void setOnWorkoutLaunchPressed(OnWorkoutLaunchPressedCallback onWorkoutLaunchPressed) {
        this.mOnWorkoutLaunchPressedCallback = onWorkoutLaunchPressed;
    }

    public void setOnWorkoutDetailsPressed(OnWorkoutDetailsPressedCallback onWorkoutDetailsPressed) {
        this.mOnWorkoutDetailsPressedCallback = onWorkoutDetailsPressed;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if(bundle != null) {
            mWorkouts = (List<WorkoutView>) bundle.get(WORKOUT_LIST);
            mWorkoutType = bundle.getString(WorkoutInstanceParentFragment.WORKOUT_FRAGMENT_TYPE);
        }

        if(savedInstanceState != null) {
            mWorkouts = Parcels.unwrap(savedInstanceState.getParcelable(WORKOUT_LIST));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        Log.i(TAG, "onResume()");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart()");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.i(TAG, "onStop()");
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.workout_instance_fragment, container, false);
        ButterKnife.bind(this, v);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setFocusable(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new WorkoutInstanceAdapter(mWorkoutType, mWorkouts, mOnWorkoutDeletePressedCallback, mOnWorkoutLaunchPressedCallback, mOnWorkoutDetailsPressedCallback);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated()");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onViewStateRestored()");
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState()");
        outState.putParcelable(WORKOUT_LIST, Parcels.wrap(mWorkouts));
        super.onSaveInstanceState(outState);
    }

    public void displayWorkouts(String workoutType, final List<WorkoutView> workouts) {
        mWorkouts = workouts;
        mWorkoutType = workoutType;
        if(getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter = new WorkoutInstanceAdapter(mWorkoutType, mWorkouts, mOnWorkoutDeletePressedCallback, mOnWorkoutLaunchPressedCallback, mOnWorkoutDetailsPressedCallback);
                    mRecyclerView.setAdapter(mAdapter);
                }
            });

        } else {
            mHasUpdatedData = true;
        }

    }
}
