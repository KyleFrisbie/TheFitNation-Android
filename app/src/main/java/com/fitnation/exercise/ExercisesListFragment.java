package com.fitnation.exercise;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnation.R;
import com.fitnation.base.BaseFragment;
import com.fitnation.model.Exercise;

import java.util.List;

/**
 * Displays a list of exercises
 */
public class ExercisesListFragment extends BaseFragment {
    private static final String EXERCISE_LIST = "EXERCISE_LIST";
    private List<Exercise> mExercises;


    public ExercisesListFragment() {
        // Required empty public constructor
    }

    public static ExercisesListFragment newInstance(List<Exercise> exercises) {
        Bundle bundle = new Bundle();

//        bundle.putSerializable(EXERCISE_LIST, exercises);
        //TODO list of serializible objects

        ExercisesListFragment  exercisesListFragment = new ExercisesListFragment();
        exercisesListFragment.setArguments(bundle);

        return exercisesListFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercises_list, container, false);
    }

}
