package com.fitnation.exercise.exerciseList;


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
import com.fitnation.exercise.callbacks.ExerciseSelectedCallback;
import com.fitnation.exercise.callbacks.ExercisesRequestCallback;
import com.fitnation.exercise.edit.ViewExerciseFragment;
import com.fitnation.model.ExerciseInstance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Displays a list of exercises
 */
public class ExercisesListFragment extends BaseFragment {
    private static final String TAG = ExercisesListFragment.class.getSimpleName();
    private static final String EXERCISE_LIST = "EXERCISE_LIST";
    private List<ExerciseInstance> mExercises;
    @BindView(R.id.exercise_recycler_view)
    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ExerciseSelectedCallback mExerciseSelectedCallback;
    private OnEditExercisePressed mOnEditExercisePressed;
    private boolean mHasUpdatedData;


    public ExercisesListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.i(TAG, "onHiddenChanged()");
        if(mHasUpdatedData) {
            mHasUpdatedData = false;
            mAdapter = new ExerciseAdapter(mExercises, mExerciseSelectedCallback, mOnEditExercisePressed);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    public static ExercisesListFragment newInstance(List<ExerciseInstance> exerciseInstances, ExerciseSelectedCallback callback, OnEditExercisePressed onEditExercisePressed) {
        ExercisesListFragment  exercisesListFragment = new ExercisesListFragment();

        if(exerciseInstances != null && !exerciseInstances.isEmpty()) {
            Bundle bundle = new Bundle();

            bundle.putSerializable(EXERCISE_LIST, new ArrayList<>(exerciseInstances));
            exercisesListFragment.setArguments(bundle);
        }

        exercisesListFragment.setExerciseSelectedCallback(callback);
        exercisesListFragment.setOnEditExercisePressed(onEditExercisePressed);

        return exercisesListFragment;
    }

    public void setExerciseSelectedCallback(ExerciseSelectedCallback exerciseSelectedCallback) {
        mExerciseSelectedCallback = exerciseSelectedCallback;
    }

    public void setOnEditExercisePressed(OnEditExercisePressed onEditExercisePressed) {
        this.mOnEditExercisePressed = onEditExercisePressed;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if(bundle != null) {
            mExercises = (List<ExerciseInstance>) bundle.get(EXERCISE_LIST);
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
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ExerciseAdapter(mExercises, mExerciseSelectedCallback, mOnEditExercisePressed);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    public void displayExercises(final List<ExerciseInstance> exercises) {
        mExercises = exercises;
        if(getView() != null) {
            mAdapter = new ExerciseAdapter(mExercises, mExerciseSelectedCallback, mOnEditExercisePressed);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mHasUpdatedData = true;
        }

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
