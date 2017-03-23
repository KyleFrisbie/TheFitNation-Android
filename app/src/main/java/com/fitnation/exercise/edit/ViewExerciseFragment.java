package com.fitnation.exercise.edit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnation.R;

import butterknife.ButterKnife;

/**
 * Allows the user to Edit an
 */
public class ViewExerciseFragment extends Fragment {

    public ViewExerciseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.exercise_fragment_view_exercise, container, false);
        ButterKnife.bind(this, v);

        return v;
    }

}
