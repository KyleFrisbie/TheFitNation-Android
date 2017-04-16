package com.fitnation.workout.edit;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fitnation.R;
import com.fitnation.base.BaseActivity;
import com.fitnation.base.BaseFragment;
import com.fitnation.base.BaseView;
import com.fitnation.base.Navigationable;
import com.fitnation.model.ExerciseView;
import com.fitnation.model.UserWorkoutInstance;
import com.fitnation.navigation.NavigationActivity;
import com.fitnation.workout.callbacks.ExerciseSelectedCallback;
import com.fitnation.workout.callbacks.OnEditExercisePressedCallback;
import com.fitnation.workout.exerciseList.ExerciseAdapter;
import com.fitnation.workout.exerciseList.ExercisesListFragment;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

/**
 */
public class EditWorkoutFragment extends BaseFragment implements EditWorkoutContract.View {
    private static final String USER_WORKOUT_INSTANCE = "USER_WORKOUT_INSTANCE";
    private UserWorkoutInstance mUserWorkoutInstance;
    private EditWorkoutContract.Presenter mPresenter;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mUserWorkoutInstance = Parcels.unwrap(args.getParcelable(USER_WORKOUT_INSTANCE));
        mPresenter.setUserWorkoutInstance(mUserWorkoutInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.workout_fragment_edit_workout, container, false);
        ButterKnife.bind(this, v);
        Navigationable navigationable =  (Navigationable) getBaseActivity();
        navigationable.displayBackArrow(true, getString(R.string.edit_workout_title) + " " + mUserWorkoutInstance.getWorkoutInstanceName());

        mPresenter.onViewReady();

        return v;
    }

    @Override
    public void setPresenter(EditWorkoutContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }
}