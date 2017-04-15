package com.fitnation.workout.edit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.model.ExerciseInstance;
import com.fitnation.workout.exerciseList.ExercisesListFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditWorkoutFragment extends Fragment {
    @BindView(R.id.first_entry_header)
    public TextView mFirstLabelHeader;
    private static final String EXERCISE_LIST = "EXERCISE_LIST";
    private List<ExerciseInstance> mExercises;
    @BindView(R.id.exercise_recycler_view)
    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public EditWorkoutFragment() {
        // Required empty public constructor
    }

    public static EditWorkoutFragment newInstance(List<UserE>)


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.workout_fragment_edit_workout, container, false);
        ButterKnife.bind(this, v);


        ExercisesListFragment exercisesListFragment = ExercisesListFragment.newInstance(mExercises, null, null);
        getFragmentManager().beginTransaction().add(R.id.edit_workout_container, exercisesListFragment).commit();

        mFirstLabelHeader.setText(R.string.completed);

        return v;
    }

}
