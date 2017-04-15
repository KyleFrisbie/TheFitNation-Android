package com.fitnation.workout.edit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.workout.exerciseList.ExercisesListFragment;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditWorkoutFragment extends Fragment {
    @BindView(R.id.first_entry_header)
    public TextView mFirstLabelHeader;
    private static final String USER_WORKOUT_INSTANCE = "USER_WORKOUT_INSTANCE";
    private UserWorkoutInstance mUserWorkoutInstance;
    @BindView(R.id.exercise_recycler_view)
    public RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public EditWorkoutFragment() {
        // Required empty public constructor
    }

    public static EditWorkoutFragment newInstance(UserWorkoutInstance userWorkoutInstance) {
        Bundle args = new Bundle();
        args.putParcelable(USER_WORKOUT_INSTANCE, Parcels.wrap(userWorkoutInstance));
        EditWorkoutFragment editWorkoutFragment = new EditWorkoutFragment();
        editWorkoutFragment.setArguments(args);

        return editWorkoutFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.workout_fragment_edit_workout, container, false);
        ButterKnife.bind(this, v);


        ExercisesListFragment exercisesListFragment = ExercisesListFragment.newInstance(mUserWorkoutInstance.getExerciseViews(), null, null);
        getFragmentManager().beginTransaction().add(R.id.edit_workout_container, exercisesListFragment).commit();

        mFirstLabelHeader.setText(R.string.completed);

        return v;
    }

}
