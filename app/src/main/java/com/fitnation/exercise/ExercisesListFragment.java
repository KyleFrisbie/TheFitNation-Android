package com.fitnation.exercise;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.model.enums.ExerciseAction;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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


    public ExercisesListFragment() {
        // Required empty public constructor
    }

    public static ExercisesListFragment newInstance(List<ExerciseInstance> exerciseInstances, ExerciseSelectedCallback callback) {
        ExercisesListFragment  exercisesListFragment = new ExercisesListFragment();

        if(exerciseInstances != null && !exerciseInstances.isEmpty()) {
            Bundle bundle = new Bundle();

            bundle.putSerializable(EXERCISE_LIST, new ArrayList<>(exerciseInstances));
            exercisesListFragment.setArguments(bundle);
        }

        exercisesListFragment.setExerciseSelectedCallback(callback);

        return exercisesListFragment;
    }

    public void setExerciseSelectedCallback(ExerciseSelectedCallback exerciseSelectedCallback) {
        mExerciseSelectedCallback = exerciseSelectedCallback;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();

        if(bundle != null) {
            mExercises = (List<ExerciseInstance>) bundle.get(EXERCISE_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_exercises_list, container, false);
        ButterKnife.bind(this, v);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ExerciseAdapter(mExercises, mExerciseSelectedCallback);
        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    public void displayExercises( final List<ExerciseInstance> exercises) {
        mExercises = exercises;

        if(getView() != null) {
            mAdapter = new ExerciseAdapter(mExercises, mExerciseSelectedCallback);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
