package com.fitnation.workout.create.list;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.base.BaseFragment;
import com.fitnation.model.ExerciseSetView;
import com.fitnation.model.ExerciseView;
import com.fitnation.workout.callbacks.ExerciseSelectedCallback;
import com.fitnation.workout.callbacks.OnEditExercisePressedCallback;

import org.parceler.Parcels;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Displays a list of exercises
 */
public class ExercisesListFragment extends BaseFragment {
    private static final String TAG = ExercisesListFragment.class.getSimpleName();
    private static final String EXERCISE_LIST = "EXERCISE_LIST";
    private static final String DISPLAY_SELECTABLE = "DISPLAY_SELECTABLE";
    private List<ExerciseView> mExercises;
    @BindView(R.id.exercise_recycler_view)
    public RecyclerView mRecyclerView;
    @BindView(R.id.first_entry_header)
    public TextView mFirstLabelHeader;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExerciseSelectedCallback mExerciseSelectedCallback;
    private OnEditExercisePressedCallback mOnEditExercisePressedCallback;
    private boolean mHasUpdatedData;
    private boolean mElementsSelectable;


    public ExercisesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "onHiddenChanged()");
        if(mHasUpdatedData) {
            mHasUpdatedData = false;
            mAdapter = new ExerciseAdapter(mExercises, mExerciseSelectedCallback, mOnEditExercisePressedCallback, mElementsSelectable);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public static ExercisesListFragment newInstance(List<ExerciseView> exerciseInstances, boolean elementsSelectable, ExerciseSelectedCallback callback, OnEditExercisePressedCallback onEditExercisePressedCallback) {
        ExercisesListFragment  exercisesListFragment = new ExercisesListFragment();
        Bundle bundle = new Bundle();

        if(exerciseInstances != null && !exerciseInstances.isEmpty()) {
            bundle.putParcelable(EXERCISE_LIST, Parcels.wrap(exerciseInstances));
        }

        bundle.putBoolean(DISPLAY_SELECTABLE, elementsSelectable);
        exercisesListFragment.setArguments(bundle);

        exercisesListFragment.setExerciseSelectedCallback(callback);
        exercisesListFragment.setOnEditExercisePressed(onEditExercisePressedCallback);

        return exercisesListFragment;
    }

    public void setExerciseSelectedCallback(ExerciseSelectedCallback exerciseSelectedCallback) {
        mExerciseSelectedCallback = exerciseSelectedCallback;
    }

    public void setOnEditExercisePressed(OnEditExercisePressedCallback onEditExercisePressedCallback) {
        this.mOnEditExercisePressedCallback = onEditExercisePressedCallback;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null) {
            mExercises = Parcels.unwrap(bundle.getParcelable(EXERCISE_LIST));
            mElementsSelectable = bundle.getBoolean(DISPLAY_SELECTABLE);
        }

        if(savedInstanceState != null) {
            mExercises = Parcels.unwrap(savedInstanceState.getParcelable(EXERCISE_LIST));
        }


        if(mExercises != null) {
            Collections.sort(mExercises);
            for (ExerciseView exerciseView : mExercises) {
                List<ExerciseSetView> exerciseSetViews = exerciseView.getExerciseSetView();
                Collections.sort(exerciseSetViews);
                exerciseView.setExerciseSetViews(exerciseSetViews);
            }
        }
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
        View v = inflater.inflate(R.layout.exercise_fragment_exercises_list, container, false);
        ButterKnife.bind(this, v);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setFocusable(false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new ExerciseAdapter(mExercises, mExerciseSelectedCallback, mOnEditExercisePressedCallback, mElementsSelectable);
        mRecyclerView.setAdapter(mAdapter);

        if(mElementsSelectable) {
            mFirstLabelHeader.setVisibility(View.VISIBLE);
        } else {
            mFirstLabelHeader.setVisibility(View.INVISIBLE);
        }

        return v;
    }

    public void displayExercises(final List<ExerciseView> exercises) {
        mExercises = exercises;
        if(getView() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mAdapter = new ExerciseAdapter(mExercises, mExerciseSelectedCallback, mOnEditExercisePressedCallback, mElementsSelectable);
                    mRecyclerView.setAdapter(mAdapter);
                }
            });

        } else {
            mHasUpdatedData = true;
        }

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
        outState.putParcelable(EXERCISE_LIST, Parcels.wrap(mExercises));
        super.onSaveInstanceState(outState);
    }
}
