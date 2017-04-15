package com.fitnation.workout.launch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fitnation.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditWorkoutFragment extends Fragment {


    public EditWorkoutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.workout_fragment_edit_workout, container, false);
    }

}
